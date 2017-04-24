package com.edu.mum.hbs.dao;

import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Revenue;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRecordDao extends DaoAbstract<InvoiceRecord,String> {
	private static final String TABLE_NAME = InvoiceRecord.TABLE_NAME;

	InvoiceRecordDao() {
		super(InvoiceRecord.class);
	}

	public void addInvoice(InvoiceRecord invoiceRecord) {
		add(invoiceRecord);
	}

	public List<InvoiceRecord> getAllInvoiceRecords() {
		return getAll();
	}

	public List<InvoiceRecord> getAllInvoiceRecordsFromToDate(String fromDate, String toDate) {
		List<InvoiceRecord> invoiceRecords = new ArrayList<InvoiceRecord>();
		FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		condition.addCondition(InvoiceRecord.CHECK_IN_DATE, SqliteUtil.GREATER_EQUALS, fromDate);
		condition.addCondition(InvoiceRecord.CHECK_OUT_DATE, SqliteUtil.LESS_EQUALS, toDate);
		List<Map<String, Object>> objects = db.get(TABLE_NAME, null, condition);

		if (objects.size() > 0) {
			for (Map<String, Object> ob : objects) {
				InvoiceRecord invoiceRecord = new InvoiceRecord();
				invoiceRecord.setPassport_id((String) ob.get(InvoiceRecord.PASSPORT_OR_ID));
				invoiceRecord.setRoom_number((String) ob.get(InvoiceRecord.ROOM_NUMBER));
				invoiceRecord.setCheckInDateByString((String) ob.get(InvoiceRecord.CHECK_IN_DATE));
				invoiceRecord.setCheckOutDateByString((String) ob.get(InvoiceRecord.CHECK_OUT_DATE));
				invoiceRecord.setRoom_amount((Double) ob.get(InvoiceRecord.ROOM_AMOUNT));
				invoiceRecord.setService_amount((Double) ob.get(InvoiceRecord.SERVICE_AMOUNT));
				invoiceRecord.setTotal_amount((Double) ob.get(InvoiceRecord.TOTAL_AMOUNT));

				invoiceRecords.add(invoiceRecord);
			}
		}
		return invoiceRecords;
	}

	public List<Revenue> getAllRevenueRecords() {
		List<InvoiceRecord> invoiceRecords = getAllInvoiceRecords();
		List<Revenue> revenueRecords = new ArrayList<Revenue>();

		if (invoiceRecords.size() > 0) {
			for (InvoiceRecord invoice : invoiceRecords) {
				revenueRecords.add(new Revenue(invoice));
			}
		}

		return revenueRecords;
	}

	public List<Revenue> getAllRevenueRecordsFromToDate(String fromDate, String toDate) {
		List<InvoiceRecord> invoiceRecords = getAllInvoiceRecordsFromToDate(fromDate, toDate);
		List<Revenue> revenueRecords = new ArrayList<Revenue>();

		if (invoiceRecords.size() > 0) {
			for (InvoiceRecord invoice : invoiceRecords) {
				revenueRecords.add(new Revenue(invoice));
			}
		}

		return revenueRecords;
	}

	public static void main(String[] argv) {
		LocalDate startDate = LocalDate.now().minusDays(4);
		LocalDate endDate = LocalDate.now();

		long days = Period.between(startDate, endDate).getDays();
		System.out.println(days);
	}

}
