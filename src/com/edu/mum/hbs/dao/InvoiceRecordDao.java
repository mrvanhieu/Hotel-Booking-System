package com.edu.mum.hbs.dao;

import com.edu.mum.hbs.entity.InvoiceRecord;
import com.edu.mum.hbs.entity.Profit;
import com.edu.mum.hbs.util.SqliteUtil;
import com.edu.mum.hbs.util.SqliteUtil.FilterCondition;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRecordDao extends DaoAbstract {
    private static final String TABLE_NAME = InvoiceRecord.TABLE_NAME;

    InvoiceRecordDao() {
    }

    public void addInvoice(InvoiceRecord invoiceRecord) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put(InvoiceRecord.PASSPORT_OR_ID, invoiceRecord.getPassportOrId());
        map.put(InvoiceRecord.ROOM_NUMBER, invoiceRecord.getRoomNumber());
        map.put(InvoiceRecord.CHECK_IN_DATE, invoiceRecord.getCheckInDate());
        map.put(InvoiceRecord.CHECK_OUT_DATE, invoiceRecord.getCheckOutDate());
        map.put(InvoiceRecord.ROOM_AMOUNT, invoiceRecord.getRoomAmount());
        map.put(InvoiceRecord.SERVICE_AMOUNT, invoiceRecord.getServiceAmount());
        map.put(InvoiceRecord.TOTAL_AMOUNT, invoiceRecord.getTotalAmount());

        db.insertRow(TABLE_NAME, map, false);
    }

    public List<InvoiceRecord> getAllInvoiceRecords() {
        List<InvoiceRecord> invoiceRecords = new ArrayList<InvoiceRecord>();
        List<Map<String, Object>> objects = db.get(TABLE_NAME, null, null);

        if (objects.size() > 0) {
            for (Map<String, Object> ob : objects) {
                InvoiceRecord invoiceRecord = new InvoiceRecord();
                invoiceRecord.setPassportOrId((String) ob.get(InvoiceRecord.PASSPORT_OR_ID));
                invoiceRecord.setRoomNumber((String) ob.get(InvoiceRecord.ROOM_NUMBER));
                invoiceRecord.setCheckInDateByString((String) ob.get(InvoiceRecord.CHECK_IN_DATE));
                invoiceRecord.setCheckOutDate((String) ob.get(InvoiceRecord.CHECK_OUT_DATE));
                invoiceRecord.setRoomAmount((Double) ob.get(InvoiceRecord.ROOM_AMOUNT));
                invoiceRecord.setServiceAmount((Double) ob.get(InvoiceRecord.SERVICE_AMOUNT));
                invoiceRecord.setTotalAmount((Double) ob.get(InvoiceRecord.TOTAL_AMOUNT));

                invoiceRecords.add(invoiceRecord);
            }
        }
        return invoiceRecords;
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
                invoiceRecord.setPassportOrId((String) ob.get(InvoiceRecord.PASSPORT_OR_ID));
                invoiceRecord.setRoomNumber((String) ob.get(InvoiceRecord.ROOM_NUMBER));
                invoiceRecord.setCheckInDateByString((String) ob.get(InvoiceRecord.CHECK_IN_DATE));
                invoiceRecord.setCheckOutDate((String) ob.get(InvoiceRecord.CHECK_OUT_DATE));
                invoiceRecord.setRoomAmount((Double) ob.get(InvoiceRecord.ROOM_AMOUNT));
                invoiceRecord.setServiceAmount((Double) ob.get(InvoiceRecord.SERVICE_AMOUNT));
                invoiceRecord.setTotalAmount((Double) ob.get(InvoiceRecord.TOTAL_AMOUNT));

                invoiceRecords.add(invoiceRecord);
            }
        }
        return invoiceRecords;
    }

    public List<Profit> getAllProfitRecords() {
        List<InvoiceRecord> invoiceRecords = getAllInvoiceRecords();
        List<Profit> profitRecords = new ArrayList<Profit>();

        if (invoiceRecords.size() > 0) {
            for (InvoiceRecord invoice : invoiceRecords) {
                profitRecords.add(new Profit(invoice));
            }
        }

        return profitRecords;
    }

    public List<Profit> getAllProfitRecordsFromToDate(String fromDate, String toDate) {
        List<InvoiceRecord> invoiceRecords = getAllInvoiceRecordsFromToDate(fromDate, toDate);
        List<Profit> profitRecords = new ArrayList<Profit>();

        if (invoiceRecords.size() > 0) {
            for (InvoiceRecord invoice : invoiceRecords) {
                profitRecords.add(new Profit(invoice));
            }
        }

        return profitRecords;
    }

    public static void main(String[] argv) {
        LocalDate startDate = LocalDate.now().minusDays(4);
        LocalDate endDate = LocalDate.now();

        long days = Period.between(startDate, endDate).getDays();
        System.out.println(days);
    }


}
