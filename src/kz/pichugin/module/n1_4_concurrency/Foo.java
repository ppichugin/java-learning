package kz.pichugin.module.n1_4_concurrency;

import java.util.concurrent.Semaphore;

public class Foo {
    static volatile int count = 1;
    static Semaphore semCon = new Semaphore(1);

    public void first(Runnable r) {
        tryBlock(r, 1, 2);
    }

    public void second(Runnable r) {
        tryBlock(r, 2, 3);
    }

    public void third(Runnable r) {
        tryBlock(r, 3, 1);
    }

    protected synchronized void print(String msg) {
        System.out.println(msg);
    }

    private void tryBlock(Runnable r, int awaitingCounter, int nextThreadToRelease) {
        try {
            while (count != awaitingCounter) {
                Thread.onSpinWait();
            }
            semCon.acquire();
            r.run();
            count = nextThreadToRelease;
            semCon.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}