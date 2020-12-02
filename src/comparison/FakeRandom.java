package comparison;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class FakeRandom {
    List<Integer> list;

    public FakeRandom(String fileName){
        list = new LinkedList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach((String line) -> list.add(Integer.parseInt(line)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int get(){
        if(list.isEmpty()) return -1;
        return list.remove(0);
    }
}
