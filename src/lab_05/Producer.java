package lab_05;

public class Producer extends Thread{
    Buffer buf;
    int value = 0;
    int k = 10;
    int id;

    public Producer(Buffer buf, int output, int id){
        this.buf = buf;
        k = output;
        this.id = id;
    }

    private void do_stuff(){

    }

    @Override
    public void run() {
//        for(int i = 0; i < 100; i++){
        while(true){
            do_stuff();
            buf.produce(value, k);
            System.out.println("(" + id + ") Produced " + k);
            value++;
        }
    }}
