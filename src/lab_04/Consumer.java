package lab_04;

public class Consumer extends Thread{
    Buffer buf;
    int value;
    int k;

    public Consumer(Buffer buf, int need){
        this.buf = buf;
        k = need;
    }


    private void do_stuff(){
//
//        try {
//            sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
        while(true){
            do_stuff();
            value = buf.consume(k);
//            System.out.println("Consumed: " + value);
        }
    }
}
