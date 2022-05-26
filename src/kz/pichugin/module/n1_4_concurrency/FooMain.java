package kz.pichugin.module.n1_4_concurrency;

public class FooMain {
    public static void main(String[] args) {
        Foo foo = new Foo();

        Runnable printFirst = () -> foo.print("first");
        Runnable printSecond = () -> foo.print("second");
        Runnable printThird = () -> foo.print("third");

        Thread threadA = new Thread(() -> {
            try {
                foo.first(printFirst);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread A");
        Thread threadB = new Thread(() -> {
            try {
                foo.second(printSecond);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread B");
        Thread threadC = new Thread(() -> {
            try {
                foo.third(printThird);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread C");

        threadA.start();
        threadC.start();
        threadB.start();
    }
}