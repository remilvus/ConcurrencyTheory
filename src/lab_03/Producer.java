package lab_03;

public class Producer extends Thread{
    Buffer buf;
    int value = 0;

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
            buf.produce(value);
            System.out.println("Produced: " + value);
            value++;
        }
    }}
