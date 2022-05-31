package kz.pichugin.module.n1_4_concurrency;

import java.util.concurrent.Semaphore;

public class Foo {
    static volatile int count = 1;
    static Semaphore semCon = new Semaphore(1);

    public void first(Runnable r) {
        while (count != 1) {
            Thread.onSpinWait();
        }
        try {
            semCon.acquire();
            r.run();
            count = 2;
            semCon.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void second(Runnable r) {
        while (count != 2) {
            Thread.onSpinWait();
        }
        try {
            semCon.acquire();
            r.run();
            count = 3;
            semCon.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void third(Runnable r) {
        while (count != 3) {
            Thread.onSpinWait();
        }
        try {
            semCon.acquire();
            r.run();
            count = 1;
            semCon.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected synchronized void print(String msg) {
        System.out.print(msg);
    }
}