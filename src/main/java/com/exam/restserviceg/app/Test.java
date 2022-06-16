package com.exam.restserviceg.app;

import com.exam.restserviceg.logic.LookupOnAirportsFile;
import com.exam.restserviceg.logic.exceptions.NoSuchRecordException;
import com.exam.restserviceg.models.Data;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Data qql;
        try {
            qql = LookupOnAirportsFile.getDataById(1l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(2l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(3l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(7l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(10l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(15l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(17l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(19l);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(240000l);
            System.out.println(qql);
        } catch (IOException | NoSuchRecordException e) {
            e.printStackTrace();
        }


//        System.out.println("HELLP");
    }
}
