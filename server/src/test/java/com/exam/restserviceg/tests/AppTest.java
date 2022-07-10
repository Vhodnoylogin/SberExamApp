package com.exam.restserviceg.tests;


import com.exam.restservice.server.logic.LookupOnAirportsFile;
import com.exam.restservice.server.logic.exceptions.RecordNotFoundException;
import common.models.Airport;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class AppTest {
    @org.junit.jupiter.api.Test
    public void test_get_by_id_1() {
        Long id = 1L;
        try {
            Airport qql = LookupOnAirportsFile.getDataById(id);
            Assertions.assertEquals(qql.getId(), id);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_get_by_id_19() {
        Long id = 19L;
        try {
            Airport qql = LookupOnAirportsFile.getDataById(id);
            Assertions.assertEquals(qql.getId(), id);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_get_by_id_190() {
        Long id = 190L;
        try {
            Airport qql = LookupOnAirportsFile.getDataById(id);
//            Assertions.assertEquals(qql.getId(),id);
            Assertions.fail();
        } catch (RecordNotFoundException e) {
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_get_all() {
        try {
            List<Airport> qql = LookupOnAirportsFile.getAllData();
            Assertions.assertTrue(qql.size() > 0);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

}
