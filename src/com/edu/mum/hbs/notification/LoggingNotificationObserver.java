package com.edu.mum.hbs.notification;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.RoomDate;
import com.edu.mum.hbs.util.Constants;

import java.util.List;

/**
 * Created by hieuho on 4/18/17.
 */
public class LoggingNotificationObserver implements NotificationObserver {
    @Override
    public void update(Customer customer, List<RoomDate> rooms) {
        String roomInfo = "";
        for (RoomDate room : rooms) {
            roomInfo += "\nRoom: " + room.getRoom_number();
            roomInfo += "\n   Price: " + room.getPrice();
        }
        String body = "Booking Notification - Logging\n" +
                "There is a customer " +
                "that have the booking over " + Constants.MAX_ROOM_PRICE +
                " Customer Information as below: \n" +
                "\nCustomer Name: " + customer.getFullname() +
                "\nPassport/ID: " + customer.getPassport_id() +
                "\nAddress: " + customer.getAddress() +
                "\nBooking Information: \n" + roomInfo;
        System.out.println(body);
    }
}
