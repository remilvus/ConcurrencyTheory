package lab_11;

import org.jcsp.lang.*;

public class Consumer1 implements CSProcess {
    private One2OneChannelInt channel;
    public Consumer1 (final One2OneChannelInt in)
    {
        channel = in;
    } // constructor

    public void run ()
    {
        for (int k = 0; k < 100; k++) {
            ChannelInputInt in = channel.in();
            int item = in.read();
            System.out.println("Consumer: " + item);
        }
    } // run
} // class Consumer