package com.misa.politeWorker;

public class Worker {
    private final String name;
    private boolean active;

    public Worker(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void work(SharedResource sharedResource, Worker otherWorker) {
        //checking if it has a shared resource, if it doesn't it will wait 10ms and loop
        while (active) {
            if (sharedResource.getOwner() != this) {
                try {
                    wait(10);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
                continue;
            }
            //when it owns a SR, it checks if other resource is active, and loop to the beginning
            if (otherWorker.isActive()) {
                System.out.println(getName() + ": give the resource to the worker " + otherWorker
                                                                                              .getName());
                sharedResource.setOwner(otherWorker);
                continue;
            }
            System.out.println(getName() + ": working on the common resource");
            active = false;
            sharedResource.setOwner(otherWorker);
        }
    }
}
