package com.misa.interrupting;

import java.sql.SQLOutput;

import static com.misa.ThreadColor.*;

public class InterruptingMain {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from the main thread");
        //creating an instance of a named thread and starting it
        Thread anotherThread = new com.misa.AnotherThread();
        anotherThread.setName("== Another thread");
        anotherThread.start();
        //it can never be assumed in which order lines will execute

        //this code runs the code inside of anonymous class
        Thread myRunnable = new Thread(new com.misa.MyRunnable() {
        @Override
        public void run() {
                System.out.println(ANSI_RED + "Hello from anonymous class's implementation");
            try{
                anotherThread.join();
                System.out.println(ANSI_RED + "AnotherThread terminated, or timed out, so i'm running again");
            } catch (InterruptedException e){
                System.out.println(ANSI_RED + "I was interrupted");
            }
        }
        });
        myRunnable.start();

//        anotherThread.interrupt();

        System.out.println(ANSI_PURPLE + "Hello again from the main thread");
    }
}
