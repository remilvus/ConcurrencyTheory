package lab_03;

public class Consumer extends Thread{
    Buffer buf;
    int value;

    public Consumer(Buffer buf){
        this.buf = buf;
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
            value = buf.consume();
            System.out.println("Consumed: " + value);
        }
    }
}
