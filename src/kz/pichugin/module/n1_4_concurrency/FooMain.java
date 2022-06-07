package kz.pichugin.module.n1_4_concurrency;

import java.util.concurrent.CompletableFuture;

public class FooMain {
    public static void main(String[] args) {
        Foo foo = new Foo();
        Runnable printFirst = () -> foo.print("first");
        Runnable printSecond = () -> foo.print("second");
        Runnable printThird = () -> foo.print("third");

        Runnable run1 = () -> foo.first(printFirst);
        Runnable run2 = () -> foo.second(printSecond);
        Runnable run3 = () -> foo.third(printThird);

        System.out.print("Solution on threads: ");
        Thread threadA = new Thread(() -> foo.first(printFirst), "Thread A");
        Thread threadB = new Thread(() -> foo.second(printSecond), "Thread B");
        Thread threadC = new Thread(() -> foo.third(printThird), "Thread C");
        threadC.start();
        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.print("\nSolution on CompletableFuture: ");
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(run1);
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(run2);
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(run3);
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task2, task1, task3);

        while (!allTasks.isDone()) {
        }
        System.out.println("\nCompleted");
    }
}