package kz.pichugin.module.n1_4_concurrency;

import java.util.concurrent.Semaphore;

public class Foo {
    static volatile int count = 1;
    static Semaphore semCon = new Semaphore(1);

    public void first(Runnable r) throws InterruptedException {
        while (count != 1) {
            Thread.onSpinWait();
        }
        semCon.acquire();
        r.run();
        count = 2;
        semCon.release();
    }

    public void second(Runnable r) throws InterruptedException {
        while (count != 2) {
            Thread.onSpinWait();
        }
        semCon.acquire();
        r.run();
        count = 3;
        semCon.release();
    }

    public void third(Runnable r) throws InterruptedException {
        while (count != 3) {
            Thread.onSpinWait();
        }
        semCon.acquire();
        r.run();
        count = 1;
        semCon.release();
    }

    protected synchronized void print(String msg) {
        System.out.print(msg);
    }
}