package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.MappedField;

public class Checkout extends BaseEntity {
    private MappedField<Booking> booking;
    private Double price;
    private Integer discount;

    public Booking getBooking() {
        return booking.getValue();
    }

    public void setBooking(Booking booking) {
        this.booking.setValue(booking);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
