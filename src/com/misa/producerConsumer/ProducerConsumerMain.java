package com.misa.producerConsumer;

import com.misa.ThreadColor;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.misa.producerConsumer.ProducerConsumerMain.EOF;

public class ProducerConsumerMain {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        //ArrayBlockingQueue helps with FIFO queue
        //they are bounded and we have to pass in the capacity
        //we don't need bufferLock any more or try-finally (because of put and take)
        //also put and take methods are used, instead of add and remove,
        //because they block when the queue is locked
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(6);

        //number of threads passed will let or will not let callable execute before those three threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);


        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_GREEN);
        MyConsumer consumer = new MyConsumer(buffer, ThreadColor.ANSI_RED);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        //starting threads
        executorService.execute(producer);
        executorService.execute(consumer);
        executorService.execute(consumer1);

        //can be used when we want thread to return a value
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println(ThreadColor.ANSI_BLUE + "Callable class");
                return "This is the Callable result";
            }
        });
        //to get result, we need to call a get() method, which will froze main method
        try {
            System.out.println(future.get());
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Something went wrong");
        }
        //have to be shutdown manually
        executorService.shutdown();
        //shutdownNow() terminates any task in a queue

    }
}

class MyProducer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private final String color;

    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }
        //letting consumer that there is no more data
        System.out.println(color + "Adding EndOfFile and exiting...");

        try {
            buffer.put(EOF);
        } catch (InterruptedException e) {
            System.out.println("Producer interrupted");
        }
    }
}

class MyConsumer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private final String color;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        //checking until buffer isn't empty
        while (true) {
            synchronized (buffer) {
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    //looks at first element to see if that is the end of file
                    if (buffer.peek().equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException e) {
                    System.out.println("Consumer interrupted");
                }
            }
        }
    }
}