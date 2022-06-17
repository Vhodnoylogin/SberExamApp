package com.exam.restserviceg.app.test;

public class Run {
    public static void f(Father a) {
        System.out.println("Father");
    }

    public static void f(Son a) {
        System.out.println("Son");
    }

    public static void f(Dauther a) {
        System.out.println("Dauther");
    }

    public static void main(String[] args) {
        Father f = new Father();
        Father s = new Son();
        Father d = new Dauther();

        f(f);
        f(s);
        f(d);
    }

    public static class Dauther extends Father {
    }

    public static class Father {
    }

    public static class Son extends Father {
    }
}
