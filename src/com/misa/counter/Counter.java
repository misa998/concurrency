package com.misa.counter;

import com.misa.ThreadColor;

public class Counter {

    public static void main(String[] args) {
//	 write your code here
        Countdown countdown = new Countdown();

        CountdownThread t1 = new CountdownThread(countdown);
        t1.setName("Thread 1");
        CountdownThread t2 = new CountdownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

//printing the numbers from 10 to 1 and coloring them based on a thread name
class Countdown {
    private int i;

    public void doCountdown() {
        String color = switch (Thread.currentThread().getName()) {
            case "Thread 1" -> ThreadColor.ANSI_CYAN;
            case "Thread 2" -> ThreadColor.ANSI_PURPLE;
            default -> ThreadColor.ANSI_GREEN;
        };

        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i=" + i);
            }
        }
    }
}

//makes a thread out of a countdown class and runs the doCountdown()
class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public CountdownThread() {

    }

    public void run() {
        threadCountdown.doCountdown();
    }
}