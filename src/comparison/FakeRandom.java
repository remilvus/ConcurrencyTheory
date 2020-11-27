package comparison;

public class FakeRandom {
    int c = 10;
    public FakeRandom(String filename){

    }

    public int get(){
        if(c == 0) return -1;
        c--;
        return 1;
    }
}
