package kz.pichugin.module.n1_4_concurrency;

public class FooMain {
    public static void main(String[] args) {
        Foo foo = new Foo();

        Runnable printFirst = () -> foo.print("first");
        Runnable printSecond = () -> foo.print("second");
        Runnable printThird = () -> foo.print("third");

        Thread threadA = new Thread(() -> foo.first(printFirst), "Thread A");
        Thread threadB = new Thread(() -> foo.second(printSecond), "Thread B");
        Thread threadC = new Thread(() -> foo.third(printThird), "Thread C");

        threadA.start();
        threadC.start();
        threadB.start();
    }
}