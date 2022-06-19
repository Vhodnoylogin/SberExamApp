package com.exam.restserviceg.tests;


import models.Data;
import org.junit.jupiter.api.Assertions;
import restserviceg.logic.LookupOnAirportsFile;
import restserviceg.logic.exceptions.RecordNotFoundException;

import java.util.List;

public class AppTest {
    @org.junit.jupiter.api.Test
    public void test_get_by_id_1() {
        Long id = 1L;
        try {
            Data qql = LookupOnAirportsFile.getDataById(id);
            Assertions.assertEquals(qql.getId(), id);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_get_by_id_19() {
        Long id = 19L;
        try {
            Data qql = LookupOnAirportsFile.getDataById(id);
            Assertions.assertEquals(qql.getId(), id);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_get_by_id_190() {
        Long id = 190L;
        try {
            Data qql = LookupOnAirportsFile.getDataById(id);
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
            List<Data> qql = LookupOnAirportsFile.getAllData();
            Assertions.assertTrue(qql.size() > 0);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

}
