package test;


import common.help.MyTimestamp;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class CommonTest {
    @org.junit.jupiter.api.Test
    public void test_string_to_timestamp_1() {
        String t = "2022-06-21T02:10:57.2066531";
        try {
            LocalDateTime qql = MyTimestamp.parse(t);
            System.out.println(qql);
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_string_to_timestamp_2() {
        String t = "2022-06-21T01:07:08.8060455";
        try {
            LocalDateTime qql = MyTimestamp.parse(t);
            System.out.println(qql);
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @org.junit.jupiter.api.Test
    public void test_string_to_timestamp_3_fail() {
        String t = "2022-06-2 01:07:08.8060455";
        try {
            LocalDateTime qql = MyTimestamp.parse(t);
            System.out.println(qql);
            Assertions.assertNull(qql);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

}
