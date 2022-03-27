package com.misa.messages;

public class MessagesMain {

    public static void main(String[] args) {
        Message message = new Message();
        // or Thread writer = new Thread(new Writer(message));
        //    writer.start();
        // because Writer doesn't implement Thread, but Runnable
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}