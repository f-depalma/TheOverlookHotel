package com.toh.database.entity;

import com.toh.database.core.BaseEntity;
import com.toh.database.core.field.MappedEntity;
import com.toh.database.core.Exceptions.UnsavedEntityException;

public class Checkout extends BaseEntity {
    private final MappedEntity<Booking> booking = new MappedEntity<>(Booking.class);
    private Double price;
    private Double discount;

    static {
        notNullFields(Checkout.class, "booking", "price");
    }

    public Checkout() {}

    public Checkout(Booking booking, Double price) {
        setBooking(booking);
        setPrice(price);
    }

    public Booking getBooking() {
        return booking.getValue();
    }

    public void setBooking(Booking booking) {
        try {
            this.booking.setValue(booking);
        } catch (UnsavedEntityException e) {
            e.printStackTrace();
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
