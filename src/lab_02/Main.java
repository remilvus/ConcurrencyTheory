package lab_02;

import lab_01.Counter;

public class Main {

    public static void main(String[] args){
        long start=System.currentTimeMillis();

        Philosopher p = new Philosopher();

        p.think();

        long stop=System.currentTimeMillis();

        System.out.println("Czas wykonania (ms): "+(stop-start));
    }

}
