package com.misa;

import static com.misa.ThreadColor.ANSI_BLUE;
import static com.misa.ThreadColor.ANSI_PURPLE;

public class AnotherThread extends Thread{
    //creating a subclass of a Thread class to override run method and put a code for it to execute
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "Hello from " + currentThread().getName());
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            System.out.println(ANSI_BLUE + "Another thread woke me up");
            return;
        }
        System.out.println(ANSI_BLUE + "Im awake after five seconds");
    }
}
