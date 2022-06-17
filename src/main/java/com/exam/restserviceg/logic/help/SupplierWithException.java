package com.exam.restserviceg.logic.help;

@FunctionalInterface
public interface SupplierWithException<T> {
    T action() throws Exception;
}
