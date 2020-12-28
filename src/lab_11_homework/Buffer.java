package lab_11_homework;

import org.jcsp.lang.*;

public class Buffer implements CSProcess {
    private final ChannelInputInt channel_producer;
    private final ChannelOutputInt channel_consumer;

    public Buffer(final ChannelInputInt prod,
                  final ChannelOutputInt cons)
    {
        channel_producer = prod;
        channel_consumer = cons;
    } // constructor

    public void run ()
    {
        int item;
        
        while(true){
            item = channel_producer.read();

//            System.out.println("(B) New item: " + item);

            channel_consumer.write(item);
        }
    }
}
