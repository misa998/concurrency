package com.misa.messages;

import java.util.Random;

public class Writer implements Runnable {
    private final Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String[] messages = {
                "Sralo konjce na betonce",
                "Ko ce ga lize",
                "Vulgaran si, Milose",
                "Nemoj vise"
        };
        //for a random delay
        Random random = new Random();

        //loop through messages
        for (String s : messages) {
            message.write(s);
            try {
                //sleeping after writing a message
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        message.write("Finished");
    }
}
