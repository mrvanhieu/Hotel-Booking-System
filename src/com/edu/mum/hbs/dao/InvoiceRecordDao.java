package com.edu.mum.hbs.dao;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Revenue;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;

public class InvoiceRecordDao extends DaoAbstract<InvoiceRecord,String> {
//	private static final String TABLE_NAME = InvoiceRecord.TABLE_NAME;

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
		FilterCondition condition = new SqliteUtil.FilterCondition(SqliteUtil.LogicalOperator.AND);
		condition.addCondition(InvoiceRecord.CHECK_IN_DATE, SqliteUtil.GREATER_EQUALS, fromDate);
		condition.addCondition(InvoiceRecord.CHECK_OUT_DATE, SqliteUtil.LESS_EQUALS, toDate);
		List<InvoiceRecord> objects = getAll(condition);
		return objects;

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
