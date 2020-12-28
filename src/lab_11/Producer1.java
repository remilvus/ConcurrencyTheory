package lab_11;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Producer1 implements CSProcess {
    private One2OneChannelInt channel;

    public Producer1(final One2OneChannelInt in)
    {
        channel = in;
    } // constructor

    public void run ()
    {
    int item;
    ChannelOutputInt channelOutput = channel.out();
    for (int k = 0; k < 100; k++) {
        item = (int) (Math.random() * 100) + 1;
            channelOutput.write(item);
        } // for
//        channelOutput.write(-1);
        System.out.println("Producer ended.");
    }
} // class Consumer