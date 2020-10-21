package lab_01;

import jdk.dynalink.beans.StaticClass;

import java.util.Vector;

public class Counter{
    public static int c;
    private static final BinSemaphore bin_sem = new BinSemaphore();

    public static void inc(){
        bin_sem.get_sem();
        c++;
        bin_sem.signal_sem();
    }

    public static void dec(){
        bin_sem.get_sem();
        c--;
        bin_sem.signal_sem();
    }

    public void go() throws InterruptedException{
        int THREAD_NUM = 1000;

        Vector<Thread> threads = new Vector<>(THREAD_NUM);

        for(int i=0; i < THREAD_NUM; i++){
            if (i % 2 == 0){
                threads.add(new Thread(new CounterDec()));
            } else {
                threads.add(new Thread(new CounterInc()));
            }
        }

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
