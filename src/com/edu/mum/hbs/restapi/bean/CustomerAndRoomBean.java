package com.edu.mum.hbs.restapi.bean;

import com.edu.mum.hbs.entity.RoomDate;

import java.util.List;

/**
 * Created by hieuho on 4/20/17.
 */
public class CustomerAndRoomBean {
    String status;
    String passport;
    String roomNumber;
    List<RoomDate> roomDates;

    public List<RoomDate> getRoomDates() {
        return roomDates;
    }

    public void setRoomDates(List<RoomDate> roomDates) {
        this.roomDates = roomDates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
