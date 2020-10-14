package lab_01;

public class Main {

    public static void main(String[] args){
        long start=System.currentTimeMillis();

        Counter counter = new Counter();
        try {
            counter.go();
        }
        catch(InterruptedException e){
            System.out.println("InterruptedException was caught");
        }

        long stop=System.currentTimeMillis();

        System.out.println("wartość licznika: " + Counter.c);

        System.out.println("Czas wykonania (ms): "+(stop-start));
    }

}
