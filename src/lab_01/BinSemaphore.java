package lab_01;

import javax.management.InvalidAttributeValueException;

public class BinSemaphore {
    private Boolean value = true;

    public synchronized void get_sem(){
        try {
            while(!value){
                wait();
            }
            value = false;
        }
        catch(InterruptedException e){
            System.out.println("InterruptedException was caught");
        }
    }

    public synchronized void signal_sem(){
        value = true;
        notify();
    }
}
