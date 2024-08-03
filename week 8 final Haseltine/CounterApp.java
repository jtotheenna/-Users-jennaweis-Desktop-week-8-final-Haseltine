package com.example.counterap;

import java.util.concurrent.CountDownLatch;

public class CounterApp {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1); // Create a latch

        Thread thread1 = new Thread(new UpCounter(latch));
        Thread thread2 = new Thread(new DownCounter(latch));

        thread1.start();
        thread2.start();
    }
}

class UpCounter implements Runnable {
    private final CountDownLatch latch;

    public UpCounter(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        for (int i = 0; i <= 20; i++) {
            System.out.println("Counting Up: " + i);
            try {
                Thread.sleep(100); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latch.countDown(); // Signal that counting up is done
    }
}

class DownCounter implements Runnable {
    private final CountDownLatch latch;

    public DownCounter(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await(); // Wait for UpCounter to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 20; i >= 0; i--) {
            System.out.println("Counting Down: " + i);
            try {
                Thread.sleep(100); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
