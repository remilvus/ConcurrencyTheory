package lab_02;

import lab_01.BinSemaphore;

import java.util.Arrays;

public class DiningRoom {
    private BinSemaphore[] forks = new BinSemaphore[5];

    public DiningRoom(){
        for (int i=0; i<5; i++){
            forks[i] = new BinSemaphore();
        }
    }

    public void get_forks(int left, int right){
//        while( ! (forks[left] && forks[right])){
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        forks[left].get_sem();

        forks[right].get_sem();
    }


    public synchronized void put_forks(int left, int right){
        forks[left].signal_sem();
        forks[right].signal_sem();
    }
}
