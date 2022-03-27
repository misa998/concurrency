package com.misa.messages;

import java.util.Random;

public class Reader implements Runnable {
    private final Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        //reading and printing
        Random random = new Random();
        //looping through the received messages and waiting for the last one
        for (String latestMessage = message.read(); !latestMessage.equals("Finished");
             latestMessage = message.read()) {
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
