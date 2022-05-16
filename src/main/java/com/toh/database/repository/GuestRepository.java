package com.toh.database.repository;

import com.toh.database.entity.BaseEntity;
import com.toh.database.entity.Guest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;


public class GuestRepository extends Repository<Guest>{
    public GuestRepository() {
        super(Guest.class, "guest.json");
    }
}

