package com.exam.restserviceg.app;

import com.exam.restserviceg.logic.LookupOnAirportsFile;
import com.exam.restserviceg.logic.exceptions.RecordNotFoundException;
import com.exam.restserviceg.models.Data;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Data qql;
        try {
            qql = LookupOnAirportsFile.getDataById(1L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(2L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(3L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(7L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(10L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(15L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(17L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(19L);
            System.out.println(qql);
            qql = LookupOnAirportsFile.getDataById(240000L);
            System.out.println(qql);
        } catch (IOException | RecordNotFoundException e) {
            e.printStackTrace();
        }


//        System.out.println("HELLP");
    }
}
