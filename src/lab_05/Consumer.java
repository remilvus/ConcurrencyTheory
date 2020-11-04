package lab_05;

public class Consumer extends Thread{
    Buffer buf;
    int value;
    int k = 3;
    int id;
    public Consumer(Buffer buf, int need, int id){
        this.buf = buf;
        k = need;
        this.id = id;
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
            System.out.println(">" + id + "< Consumed: " + k);
        }
    }
}
