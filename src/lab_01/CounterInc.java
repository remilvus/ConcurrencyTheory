package lab_01;

import java.util.Random;

public class CounterInc implements Runnable{
    @Override
    public void run() {
        // sleep increases probability of error (because the threads end almost immediately)
        try {
            Thread.sleep( Math.abs(new Random().nextInt()) % 400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Counter.inc();
    }
}
