package com.toh.database.repository;

import com.toh.database.core.Repository;
import com.toh.database.entity.Checkout;


public class CheckoutRepository extends Repository<Checkout> {
    private static CheckoutRepository instance;

    private CheckoutRepository() {
        super(Checkout.class, "checkout.json");
    }

    public static CheckoutRepository execute() {
        if (instance == null) {
            instance = new CheckoutRepository();
        }
        return instance;
    }
}

