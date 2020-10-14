package lab_01;

import java.util.Random;

public class CounterDec implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep( Math.abs(new Random().nextInt()) % 400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Counter.dec();
    }
}