package com.edu.mum.hbs.notification;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.RoomDate;
import com.edu.mum.hbs.util.Constants;
import com.edu.mum.hbs.util.EmailUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hieuho on 4/18/17.
 */
public class EmailNotificationObserver implements NotificationObserver {
    @Override
    public void update(Customer customer, List<RoomDate> rooms) {
        System.out.println("======= Observer call - Sending emails");

        //Send email in different thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String subject = "Booking Notification";
            String roomInfo = "";
            for (RoomDate room : rooms) {
            roomInfo += "<br>Room: " + room.getRoom_number();
            roomInfo += "<br>   Price: " + room.getPrice();
        }
//            rooms.stream().forEach(room ->
//                    roomInfo +=  room.getRoomNumber());
        String body = "Hi Management,<br>" +
                "This email is to let you know that we have a customer " +
                "that have the booking over " + Constants.MAX_ROOM_PRICE +
                "<br>Customer Information as below: <br>" +
                "<br><br>Customer Name: " + customer.getFullName() +
                "<br>Passport/ID: " + customer.getPassport_id() +
                "<br>Address: " + customer.getAddress() +
                "<br><br>Booking Information: <br>" + roomInfo;

        EmailUtils.getInstance().sendEmail(Constants.ADMIN_EMAIL, subject, body);
    });
    }
}
