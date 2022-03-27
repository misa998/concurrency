package com.misa.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreMain {
    public static void main(String[] args) throws InterruptedException {
        Semaphore s = new Semaphore(1);
        System.out.println(s.availablePermits());
        s.acquire();
        System.out.println(s.availablePermits());
        s.release();
        System.out.println(s.availablePermits());

        ExecutorService executor = Executors.newCachedThreadPool();
        // creates 200 threads
        for (int i = 0; i < 200; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}

class Connection {
    private static final Connection instance = new Connection();

    private final Semaphore semaphore = new Semaphore(1);
    private final AtomicInteger connectionsCnt = new AtomicInteger(0);

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    /**
     * This method acquires the permit before and releases it
     * after the {@link #connectTail()} has finished.
     * Only 1 permit is allowed.
     */
    public void connect() {
        try {
            System.out.println(Thread.currentThread().getName() + " - Waiting to acquire permit.");
            semaphore.acquire();
            connectTail();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + " - Released the permit.");
        }
    }

    /**
     * Simulates the connection by sleeping for 200 ms.
     * Also regulates the number of connections.
     */
    private synchronized void connectTail() throws InterruptedException {
        connectionsCnt.incrementAndGet();
        System.out.println("Connections count: " + connectionsCnt);
        Thread.sleep(200);
        connectionsCnt.decrementAndGet();
    }
}
