package org.example;

public class DeadLock {

    public static void main(String[] args) {

        Object resourceA = new Object();
        Object resourceB = new Object();

        new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + " resource A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " try get resource B");
                synchronized ((resourceB)) {
                    System.out.println(Thread.currentThread().getName() + " resource B");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " resource A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " try get resource A");
                synchronized ((resourceA)) {
                    System.out.println(Thread.currentThread().getName() + " resource A");
                }
            }
        }).start();
    }

}
