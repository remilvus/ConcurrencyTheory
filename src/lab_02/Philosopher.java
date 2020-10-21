package lab_02;

public class Philosopher implements Runnable{
    private final int id;
    private DiningRoom dining;
    private boolean left_handed = false;

    public Philosopher(int id, DiningRoom dining){
        this.id = id;
        this.dining = dining;

        if(id == 4) {left_handed = true;}
    }

    public void run(){
        int first = id;
        int second = (id + 1) % 5;

//        uncomment for non-blocking solution
//        if (left_handed){
//            first = (id + 1) % 5;
//            second = id;
//        }

        while(true) {
            think();
            dining.get_forks(first, second);
            eat();
            dining.put_forks(first, second);
        }
    }

    public void think(){
        System.out.println("thniks " + id);
    }
    public void eat(){
        System.out.println("eats " + id);
    }
}
