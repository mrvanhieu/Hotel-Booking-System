package com.edu.mum.hbs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqliteUtil {
	private Connection conn;
	public static final int EQUALS = 0;
	public static final int LESS = 1;
	public static final int GREATER = 2;
	public static final int NOT_EQUALS = 3;
	public static final int LIKE = 4;
	public static final int IN = 5;
	public static final int NOT_IN = 6;
	public static final int GREATER_EQUALS = 7;
	public static final int LESS_EQUALS = 8;

	public SqliteUtil() {
		this.conn = this.connector();
	}

	public Connection connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:Hotel.sqlite");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isClosed() {
		try {
			return conn.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertRow(String table, Object... colValues) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table);
		StringBuilder values = new StringBuilder();

		values.append("(");
		for (Object value : colValues) {
			values.append(getDbStringForValue(value) + ",");
		}
		values.deleteCharAt(values.length() - 1);
		values.append(")");
		sql.append(" VALUES " + values);

		executeUpdate(sql.toString());
	}

	public int insertRow(String table, LinkedHashMap<String, Object> data, boolean autoKeyGenerated) {
		removeNullKeys(data);

		// Prepared Statement is more expensive then Normal Statement
		// but Normal Statement does not skip string with single quote, so
		// values like "'ticker" will be troublesome for Normal Statement
		String query = createInsertPSQuery(table, data);
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query,
					autoKeyGenerated ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);

			setValues(ps, data.values());

			ps.executeUpdate();

			if (autoKeyGenerated) {
				ResultSet resultSet = ps.getGeneratedKeys();

				if (resultSet.next()) {
					int key = resultSet.getInt(1);
					return key;
				}
			}

			return -1;

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private void removeNullKeys(LinkedHashMap<String, Object> data) {
		// remove map entries with null value - this also means update can't set
		// column to null

		for (Iterator<String> iter = data.keySet().iterator(); iter.hasNext();) {
			Object value = data.get(iter.next());
			if (value == null) {
				iter.remove();
			}
		}

	}

	// return list of columns to insert in a prepared statement query
	private String createInsertPSQuery(String tableName, LinkedHashMap<String, Object> row) {
		// prepare sql
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("INSERT INTO " + tableName);
		StringBuilder keys = new StringBuilder();
		StringBuilder values = new StringBuilder();

		keys.append("(");
		values.append("(");

		// generate keys
		for (String key : row.keySet()) {
			keys.append(key + ",");
			values.append("?,");

		}

		keys.deleteCharAt(keys.length() - 1);
		values.deleteCharAt(values.length() - 1);

		keys.append(")");
		values.append(")");
		queryBuilder.append(" " + keys + " VALUES " + values);

		String query = queryBuilder.toString();
		return query;

	}

	private void setValues(PreparedStatement ps, Collection<Object> columnValues) throws SQLException {

		int count = 1;

		StringBuilder valueLogs = new StringBuilder();
		for (Object value : columnValues) {
			if (value instanceof Double) {
				valueLogs.append(GeneralUtil.roundTwoPlaces((Double) value) + ", ");
			} else {
				valueLogs.append(value + ", ");
			}

			if (value instanceof String)
				ps.setString(count, value.toString());
			else if (value instanceof Boolean)
				ps.setBoolean(count, (Boolean) value);
			else if (value instanceof Integer)
				ps.setInt(count, (Integer) value);
			else if (value instanceof Long)
				ps.setLong(count, (Long) value);
			else if (value instanceof Double)
				ps.setDouble(count, (Double) value);
			else if (value instanceof Date)
				ps.setDate(count, new java.sql.Date(((Date) value).getTime()));
			else
				ps.setObject(count, value);

			count++;
		}
	}

	public Map<String, Object> findOne(String tableName, String[] columns, FilterCondition conditions) {
		// the conditions should return only one record
		List<Map<String, Object>> results = get(tableName, columns, conditions, null, null, null);
		if (results.size() == 0) {
			return null;
		}
		if (results.size() > 1) {
			throw new RuntimeException("More than one record found for getOne query");
		}
		// results.size() == 1
		return results.get(0);
	}

	public List<Map<String, Object>> get(String tableName, String[] columns, FilterCondition conditionsobj) {
		return get(tableName, columns, conditionsobj, null, null, null);
	}

	public List<Map<String, Object>> get(String tableName, String[] columns, FilterCondition conditionsobj,
			String orderByCol, Integer limit, Integer offset) {

		if (limit == null && offset != null) {
			throw new RuntimeException("You cannot specify offset without specifying limit");
		}

		StringBuilder sqlbuffer = new StringBuilder();
		sqlbuffer.append("SELECT ");
		if (columns == null || columns.length == 0) {
			sqlbuffer.append("*");
		} else {
			for (String col : columns) {
				sqlbuffer.append(col + ", ");
			}
			sqlbuffer.delete(sqlbuffer.length() - 2, sqlbuffer.length());
		}

		sqlbuffer.append(" FROM ");
		sqlbuffer.append(tableName);

		if (conditionsobj != null && conditionsobj.getConditions().size() > 0) {
			LogicalOperator logicalOpeartor = conditionsobj.getOp();
			if (logicalOpeartor == null)
				logicalOpeartor = LogicalOperator.AND; // can be for single
														// condition
			List<ColumnFilter> conditions = conditionsobj.getConditions();

			sqlbuffer.append(" WHERE ");
			for (ColumnFilter condition : conditions) {

				sqlbuffer.append(condition.getPSFilterString());
				sqlbuffer.append(" " + logicalOpeartor.toString() + " ");

			}

			int trimLength = logicalOpeartor.toString().length() + 2;
			sqlbuffer.delete(sqlbuffer.length() - trimLength, sqlbuffer.length());
		}

		if (orderByCol != null)
			sqlbuffer.append(" ORDER BY " + orderByCol);

		if (limit != null) {
			sqlbuffer.append(" LIMIT " + limit.intValue());
		}

		if (offset != null) {
			sqlbuffer.append(" OFFSET " + offset.intValue());
		}

		String sql = sqlbuffer.toString();

		PreparedStatement ps = null;

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		try {
			ps = conn.prepareStatement(sql);
			setConditions(ps, conditionsobj, 1);
			ResultSet resultSet = ps.executeQuery();
			results = parseResultSet(resultSet);
		} catch (Exception e) {
			System.out.println(sql);
			if (ps != null)
				System.out.println("Error occurred while executing sql: " + ps.toString());

			throw new RuntimeException(e);
		} finally {
			close(ps);
		}

		return results;
	}

	public List<Map<String, Object>> join(String tableName1, String tableName2, String[] columns,
			FilterCondition conditionsobj, String orderByCol, String joinType, Map<String, String> joinOn) {
		return join(tableName1, tableName2, columns, conditionsobj, orderByCol, joinType, joinOn, null, null);
	}

	public List<Map<String, Object>> join(String tableName1, String tableName2, String[] columns,
			FilterCondition conditionsobj, String orderByCol, String joinType, Map<String, String> joinOn,
			Integer limit, Integer offset) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		if (columns == null || columns.length == 0) {
			sql.append("*");
		} else {
			for (String col : columns) {
				sql.append(col + ", ");
			}
			sql.delete(sql.length() - 2, sql.length());
		}

		sql.append(" FROM " + tableName1 + " ");

		// Join statement
		sql.append(joinType + " JOIN " + tableName2 + " ON ");
		if (joinOn != null && joinOn.size() > 0) {
			// here key contains table1 join column and value contains table2
			// join column
			for (String table1Col : joinOn.keySet()) {
				String table2Col = (String) joinOn.get(table1Col);
				sql.append(tableName1 + "." + table1Col + "=" + tableName2 + "." + table2Col + " AND ");
			}
			sql.delete(sql.length() - 5, sql.length());
		} else {
			// TODO - Can join clause work without JOIN join condition, what to
			// do if joinOn is null?
		}

		if (conditionsobj != null && conditionsobj.getConditions().size() > 0) {
			List<ColumnFilter> conditions = conditionsobj.getConditions();
			sql.append(" WHERE ");
			for (ColumnFilter condition : conditions) {
				sql.append(condition.getPSFilterString());
				sql.append(" AND ");

			}
			sql.delete(sql.length() - 5, sql.length());
		}

		if (orderByCol != null)
			sql.append(" ORDER BY " + orderByCol);

		if (limit != null) {
			sql.append(" LIMIT " + limit.intValue());
		}

		if (offset != null) {
			sql.append(" OFFSET " + offset.intValue());
		}

		PreparedStatement ps = null;

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		try {
			ps = conn.prepareStatement(sql.toString());

			setConditions(ps, conditionsobj, 1);

			ResultSet resultSet = ps.executeQuery();
			results = parseResultSet(resultSet);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(ps);
		}

		return results;
	}

	private static void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void setConditions(PreparedStatement ps, FilterCondition conditions, int startIndex)
			throws SQLException {
		// startIndex parameter is required because for update prepared
		// statement, there could be update placeholders before
		// condition placeholders

		if (conditions == null)
			return;

		for (ColumnFilter condition : conditions.getConditions()) {
			Object value = condition.getValue();

			if (value instanceof String)
				ps.setString(startIndex, value.toString());
			else if (value instanceof Boolean)
				ps.setBoolean(startIndex, (Boolean) value);
			else if (value instanceof Integer)
				ps.setInt(startIndex, (Integer) value);
			else if (value instanceof Long)
				ps.setLong(startIndex, (Long) value);
			else if (value instanceof Double)
				ps.setDouble(startIndex, (Double) value);
			else if (value instanceof Date)
				ps.setDate(startIndex, new java.sql.Date(((Date) value).getTime()));
			else
				ps.setObject(startIndex, value);

			startIndex++;
		}
	}

	private static List<Map<String, Object>> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		ResultSetMetaData metadata = resultSet.getMetaData();
		int colCount = metadata.getColumnCount();

		String[] cols = new String[colCount];
		for (int i = 1; i <= colCount; i++) {
			cols[i - 1] = metadata.getColumnName(i);
		}

		while (resultSet.next()) {
			Map<String, Object> row = new HashMap<String, Object>();
			for (int i = 1; i <= colCount; i++) {
				String key = cols[i - 1];
				Object value = resultSet.getObject(i);

				if (value == null)
					continue;

				row.put(key, value);
			}
			results.add(row);
		}

		return results;
	}

	public int executeUpdate(String sql) {
		Statement stmt = null;
		try {
			System.out.println(sql);
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			stmt.close();
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getDbStringForValue(Object value) {

		if (value == null)
			return "null";

		if (value instanceof Number)
			return value.toString();
		else if (value instanceof String)
			return "'" + value.toString() + "'";
		else if (value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = "'" + formatter.format(date) + "'";
			return s;
		} else if (value instanceof Boolean)
			return value.toString();

		return null; // this should not happen
	}
	public boolean delete(String tableName, FilterCondition conditions) {
        boolean success = false;

        String query = createDeletePSQuery(tableName, conditions);
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            setConditions(ps, conditions, 1);
            ps.executeUpdate();
            success = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return success;
    }

    public boolean delete(String tableName, List<FilterCondition> conditions) {
        boolean success = false;

        if (conditions.isEmpty())
            return false;

        String query = createDeletePSQuery(tableName, conditions.get(0));

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);

            for (FilterCondition condition : conditions) {
                setConditions(ps, condition, 1);
                ps.addBatch();
            }

            ps.executeBatch();
            success = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return success;
    }

    private String createDeletePSQuery(String tableName, FilterCondition conditionsobj) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("DELETE from " + tableName);

        if (conditionsobj != null && conditionsobj.getConditions().size() > 0) {

            queryBuilder.append(" WHERE ");
            LogicalOperator logicalOperator = conditionsobj.getOp();
            List<ColumnFilter> conditions = conditionsobj.getConditions();

            if (logicalOperator == null)
                logicalOperator = LogicalOperator.AND;

            for (ColumnFilter condition : conditions) {
                queryBuilder.append(condition.getPSFilterString());
                queryBuilder.append(" " + logicalOperator.toString() + " ");

            }

            queryBuilder.delete(queryBuilder.length() - (logicalOperator.toString().length() + 2),
                    queryBuilder.length());
        }

        String query = queryBuilder.toString();
        return query;
    }
    
    //Update
    public int update(String tableName, LinkedHashMap<String, Object> data, FilterCondition conditions) {

        removeNullKeys(data);

        String query = createUpdatePSQuery(tableName, data, conditions);

        PreparedStatement ps = null;

        int result = -1;
        try {

            ps = conn.prepareStatement(query);
            setValues(ps, data.values());
            setConditions(ps, conditions, data.values().size() + 1);

            result = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(ps);
        }

        return result;
    }
	public static class FilterCondition {
		private List<ColumnFilter> conditions;
		private LogicalOperator op;

		public LogicalOperator getOp() {
			return op;
		}

		public FilterCondition() {
			this(null);
		}

		public FilterCondition(LogicalOperator op) {
			conditions = new ArrayList<ColumnFilter>();
			this.op = op;
		}

		public FilterCondition addCondition(String column, int op, Object value) {
			if (this.op == null && conditions.size() > 0) {
				throw new RuntimeException("Adding multiple conditions not allowed without logical operator");
			}
			ColumnFilter filter = new ColumnFilter(column, op, value);
			conditions.add(filter);
			return this;
		}

		public FilterCondition addEqualsCondition(String column, Object value) {
			return addCondition(column, SqliteUtil.EQUALS, value);
		}

		public List<ColumnFilter> getConditions() {
			return conditions;
		}

		public List<String> getConditionColumns() {
			List<String> columns = new ArrayList<String>();
			for (ColumnFilter colFilter : conditions) {
				columns.add(colFilter.getColumn());
			}
			return columns;
		}
	}
	 private String createUpdatePSQuery(String tableName, LinkedHashMap<String, Object> data, FilterCondition conditions) {
	        StringBuilder queryBuilder = new StringBuilder();
	        queryBuilder.append("UPDATE " + tableName + " SET ");

	        for (String key : data.keySet()) {
	            queryBuilder.append(key + "=?, ");

	        }

	        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

	        if (conditions != null && conditions.getConditions().size() > 0) {
	            queryBuilder.append(" WHERE ");
	            LogicalOperator logicalOperator = conditions.getOp();

	            if (logicalOperator == null)
	                logicalOperator = LogicalOperator.AND;

	            for (ColumnFilter condition : conditions.getConditions()) {
	                queryBuilder.append(condition.getPSFilterString());
	                queryBuilder.append(" " + logicalOperator.toString() + " ");

	            }
	            queryBuilder.delete(queryBuilder.length() - (logicalOperator.toString().length() + 2),
	                    queryBuilder.length());
	        }
	        String query = queryBuilder.toString();
	        return query;
	    }
	public static class ColumnFilter {
		public String column;
		public int op;
		public Object value;

		public ColumnFilter(String col, int op, Object val) {
			this.column = col;
			this.op = op;
			this.value = val;
		}

		public String getFilterString() {
			String opStr = getOpString();
			String valStr = getDbStringForValue(value);
			return column + opStr + valStr;
		}

		public String getPSFilterString() {
			String opStr = getOpString();
			return column + opStr + "?";
		}

		public String getInFilterString() {
			String opStr = getOpString();
			return column + opStr + "(?)";
		}

		public String getColumn() {
			return column;
		}

		public Object getValue() {
			return value;
		}

		private String getOpString() {
			String opStr = null;
			switch (op) {
			case EQUALS:
				opStr = (value == null) ? " is " : " = ";
				break;
			case NOT_EQUALS:
				opStr = (value == null) ? " is not " : " != ";
				break;
			case LESS:
				opStr = " < ";
				break;
			case GREATER:
				opStr = " > ";
				break;
			case LIKE:
				opStr = " LIKE ";
				break;
			case IN:
				opStr = " IN ";
				break;
			case NOT_IN:
				opStr = " NOT IN";
				break;
			case GREATER_EQUALS:
				opStr = " >= ";
				break;
			case LESS_EQUALS:
				opStr = " <= ";
				break;
			}
			return opStr;
		}
	}

	public static enum LogicalOperator {
		OR, AND;
	}
}
