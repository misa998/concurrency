package com.misa.deadlock;

public class DeadlockMain {
    //threads of the app will use these two objects to synchronize
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();

    }

    private static class Thread1 extends Thread{
        public void run(){

            synchronized (lock1){
                System.out.println("Thread 1 has lock1");
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    System.out.println("Thread 1 Interrupted");
                }
                //deadlock
                System.out.println("Thread 1 waiting for lock2");
                synchronized (lock2){
                    System.out.println("Thread 1 has lock1 and lock2");
                }
                System.out.println("Thread 1 released lock2");
            }
            System.out.println("Thread 1 released lock1");
        }
    }

    private static class Thread2 extends Thread{
        public void run(){

            synchronized (lock1){
                System.out.println("Thread 2 has lock1");
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    System.out.println("Thread 2 Interrupted");
                }
                System.out.println("Thread 2 waiting for lock2");
                synchronized (lock2){
                    System.out.println("Thread 2 has lock1 and lock2");
                }
                System.out.println("Thread 2 released lock2");
            }
            System.out.println("Thread 2 released lock1");
        }
    }
}


