package com.misa.politeWorker;

public class PoliteWorkerMain {

    public static void main(String[] args) {
        //creating a two worker threads that share a resource
        //when they see that the other thread's active
        //they're going to give resource to the other thread and wait
        final Worker worker1 = new Worker("Worker 1", true);
        final Worker worker2 = new Worker("Worker 2", true);

        final SharedResource sharedResource = new SharedResource(worker1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                worker1.work(sharedResource, worker2);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                worker2.work(sharedResource, worker1);
            }
        }).start();

    }
}
