package lab_04;

public class Producer extends Thread{
    Buffer buf;
    int value = 0;
    int k = 10;
    public Producer(Buffer buf){
        this.buf = buf;
    }

    private void do_stuff(){

    }

    @Override
    public void run() {
//        for(int i = 0; i < 100; i++){
        while(true){
            do_stuff();
            buf.produce(value, k);
            System.out.println("Produced: " + value);
            value++;
        }
    }}
