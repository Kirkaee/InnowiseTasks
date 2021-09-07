package org.example;

public class BottleNeck {

    int i = 0;

    synchronized void count(int i) throws InterruptedException {
        i++;
        System.out.println(Thread.currentThread().getName() + " " + i);
        Thread.sleep(5000);
    }

    public static void main(String[] args) {
        BottleNeck bottleNeck = new BottleNeck();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            try {
                bottleNeck.count(bottleNeck.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            try {
                bottleNeck.count(bottleNeck.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            try {
                bottleNeck.count(bottleNeck.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            try {
                bottleNeck.count(bottleNeck.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        }).start();
    }
}
