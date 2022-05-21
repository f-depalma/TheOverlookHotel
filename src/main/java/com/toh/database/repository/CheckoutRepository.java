package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Checkout;


public class CheckoutRepository {
    private static Repository<Checkout> instance;

    private CheckoutRepository() {};

    public static Repository<Checkout> get() {
        if (instance == null) {
            instance = new Repository<>(Checkout.class, "checkout.json");
        }
        return instance;
    }
}

