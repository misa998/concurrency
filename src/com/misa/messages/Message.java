package com.misa.messages;

public class Message {
    private String message;
    private boolean empty = true;

    //used by the consumer
    //we don't want message to be read, while it is in a process of writing
    public synchronized String read() {
        //looping until there is a message
        while (empty) {
            try {
                //thread will wait and release it's lock on the message object
                //when the loop condition passes
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //messages are read
        empty = true;
        notifyAll();
        //returning to the caller (consumer)
        return message;
    }

    //used by the producerConsumer
    public synchronized void write(String message) {
        //we want consumer to read each message before we write another one
        //looping till the message is empty
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        empty = false;
        notifyAll();
        //writing the message
        this.message = message;
    }
}
