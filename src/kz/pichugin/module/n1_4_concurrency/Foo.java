package kz.pichugin.module.n1_4_concurrency;

import java.util.concurrent.Semaphore;

public class Foo {
    private static final Semaphore sem2_3 = new Semaphore(0);
    private static final Semaphore sem3_1 = new Semaphore(0);

    public void first(Runnable r) {
        r.run();
        sem2_3.release();
    }

    public void second(Runnable r) {
        try {
            sem2_3.acquire();
            r.run();
            sem3_1.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void third(Runnable r) {
        try {
            sem3_1.acquire();
            r.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected synchronized void print(String msg) {
        System.out.print(msg);
    }
}