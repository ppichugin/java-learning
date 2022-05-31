package kz.pichugin.module.n1_4_concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FooMain {
    public static void main(String[] args) {
        Foo foo = new Foo();
        Runnable printFirst = () -> foo.print("first");
        Runnable printSecond = () -> foo.print("second");
        Runnable printThird = () -> foo.print("third");

        Runnable testOne = () -> foo.first(printFirst);
        Runnable testTwo = () -> foo.second(printSecond);
        Runnable testThree = () -> foo.third(printThird);

//        Thread threadA = new Thread(() -> foo.first(printFirst), "Thread A");
//        Thread threadB = new Thread(() -> foo.second(printSecond), "Thread B");
//        Thread threadC = new Thread(() -> foo.third(printThird), "Thread C");
//        threadA.start();
//        threadC.start();
//        threadB.start();

        Executor myExecutor = Executors.newFixedThreadPool(3);
        //NOT WORKING
//        CompletableFuture<Void> first = CompletableFuture.runAsync(printFirst, myExecutor);
//        CompletableFuture<Void> second = CompletableFuture.runAsync(printSecond, myExecutor);
//        CompletableFuture<Void> third = CompletableFuture.runAsync(printThird, myExecutor);
//        CompletableFuture.allOf(first, second, third);


        //NOT WORKING
        CompletableFuture
                .runAsync(testOne, myExecutor)
                .thenRunAsync(testThree, myExecutor)
                .thenRunAsync(testTwo, myExecutor);

    }
}