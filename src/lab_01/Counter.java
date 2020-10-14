package lab_01;

import java.util.Vector;

public class Counter{
    public static int c;

    public static void inc(){
        c++;
    }

    public static void dec(){
        c--;
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
