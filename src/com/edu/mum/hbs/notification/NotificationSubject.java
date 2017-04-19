package com.edu.mum.hbs.notification;

import com.edu.mum.hbs.entity.Customer;
import com.edu.mum.hbs.entity.RoomDate;

import java.util.List;

/**
 * Created by hieuho on 4/18/17.
 */
public interface NotificationSubject {
    public void register(NotificationObserver observer);
    public void unregister(NotificationObserver observer);
    public void notifyObservers(Customer customer, List<RoomDate> rooms);
}
