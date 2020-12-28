package lab_11_homework;

import org.jcsp.lang.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channels, creates processes then
 * executes them in parallel, using JCSP.
 */
public final class Main {
    static final int consumers = 2;
    static final int producers = 4;
    static final int bufferSize = 10;
    static final int productCount = 30;
    static final int producerPortion = productCount / producers;

    public static void main(String[] args) {
        new Main();
    } // main

    public Main(){
        // Creation of producers-buffer channels
        final Any2OneChannelInt[] chanProdBuf = initAny2OneChannelInt(bufferSize);

        final ChannelInputInt[] inProdBuf = initChannelInputInt(chanProdBuf, bufferSize);
        final ChannelOutputInt[] outProdBuf = initChannelOutputInt(chanProdBuf, bufferSize);

        // Creation of buffer-consumer channels
        final One2AnyChannelInt[] chanBufCons = initOne2AnyChannelInt(bufferSize);

        final ChannelInputInt[] inBufCons = initChannelInputInt(chanBufCons, bufferSize);
        final ChannelOutputInt[] outBufCons = initChannelOutputInt(chanBufCons, bufferSize);


        System.out.println("Process creation...");
        CSProcess[] procBuffer = new CSProcess[bufferSize];
        Arrays.setAll(procBuffer, idx -> new Buffer(inProdBuf[idx], outBufCons[idx]));

        // concatenate consumers' & producers' streams
        Stream<CSProcess> processesP = makeProducers(outProdBuf);
        Stream<CSProcess> processesC = makeConsumers(inBufCons);
        Stream<CSProcess> processes = Stream.concat(processesP, processesC);

        // concatenate with buffer
        CSProcess[] procList = Stream.concat(processes,
                                             Arrays.stream(procBuffer)).toArray(CSProcess[]::new);
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel

    }

    private Stream<CSProcess> makeProducers(ChannelOutputInt[] outProdBuf){
        // create channel for producer-manager requests
        final One2OneChannelInt[] chanProdManReqest = initOne2OneChannelInt(producers);

        final ChannelInputInt[] inProdManRequest = initChannelInputInt(chanProdManReqest, producers);
        final ChannelOutputInt[] outProdManRequest = initChannelOutputInt(chanProdManReqest, producers);

        // create channel for producer-manager data
        final One2OneChannelInt[] chanProdManData = initOne2OneChannelInt(producers);

        final ChannelInputInt[] inProdManData = initChannelInputInt(chanProdManData, producers);
        final ChannelOutputInt[] outProdManData = initChannelOutputInt(chanProdManData, producers);

        // create producers
        CSProcess[] procProducer = new CSProcess[producers];
        Arrays.setAll(procProducer, idx -> new Producer(outProdManRequest[idx], inProdManData[idx],
                                                        outProdBuf, producerPortion));

        // create producers' mmanager
        CSProcess procProducerManager = new BufferManager(inProdManRequest, outProdManData, bufferSize);

        return Stream.concat(Stream.of(procProducerManager), Arrays.stream(procProducer));
    }

    private Stream<CSProcess> makeConsumers(ChannelInputInt[] inBufCons){
        // create channel for consuemr-manager requests
        final One2OneChannelInt[] chanConsManRequest = initOne2OneChannelInt(consumers);

        final ChannelInputInt[] inConsManRequest = initChannelInputInt(chanConsManRequest, consumers);
        final ChannelOutputInt[] outConsManRequest = initChannelOutputInt(chanConsManRequest, consumers);

        // create channel for consumer-manager data
        final One2OneChannelInt[] chanConsManData = initOne2OneChannelInt(consumers);

        final ChannelInputInt[] inConsManData = initChannelInputInt(chanConsManData, consumers);
        final ChannelOutputInt[] outConsManData = initChannelOutputInt(chanConsManData, consumers);

        // create consumers
        CSProcess[] procConsumer = new CSProcess[consumers];
        Arrays.setAll(procConsumer, idx -> new Consumer(outConsManRequest[idx], inConsManData[idx], inBufCons));

        // create consumers' mmanager
        CSProcess procConsumerManager = new BufferManager(inConsManRequest, outConsManData, bufferSize);

        return Stream.concat(Stream.of(procConsumerManager), Arrays.stream(procConsumer));
    }

    private One2OneChannelInt[] initOne2OneChannelInt(int size){
        One2OneChannelInt[] channel = new One2OneChannelInt[size];
        Arrays.setAll(channel, ignore -> Channel.one2oneInt());
        return channel;
    }

    private Any2OneChannelInt[] initAny2OneChannelInt(int size){
        Any2OneChannelInt[] channel = new Any2OneChannelInt[size];
        Arrays.setAll(channel, ignore -> Channel.any2oneInt());
        return channel;
    }

    private One2AnyChannelInt[] initOne2AnyChannelInt(int size){
        One2AnyChannelInt[] channel = new One2AnyChannelInt[size];
        Arrays.setAll(channel, ignore -> Channel.one2anyInt());
        return channel;
    }
    
    private ChannelInputInt[] initChannelInputInt(One2OneChannelInt[] channels, int size){
        final ChannelInputInt[] inputs = new ChannelInputInt[size];
        Arrays.setAll(inputs, idx -> channels[idx].in());
        return inputs;
    }

    private ChannelInputInt[] initChannelInputInt(Any2OneChannelInt[] channels, int size){
        final ChannelInputInt[] inputs = new ChannelInputInt[size];
        Arrays.setAll(inputs, idx -> channels[idx].in());
        return inputs;
    }

    private ChannelInputInt[] initChannelInputInt(One2AnyChannelInt[] channels, int size){
        final ChannelInputInt[] inputs = new ChannelInputInt[size];
        Arrays.setAll(inputs, idx -> channels[idx].in());
        return inputs;
    }

    private ChannelOutputInt[] initChannelOutputInt(One2OneChannelInt[] channels, int size){
        final ChannelOutputInt[] outputs = new ChannelOutputInt[size];
        Arrays.setAll(outputs, idx -> channels[idx].out());
        return outputs;
    }

    private ChannelOutputInt[] initChannelOutputInt(Any2OneChannelInt[] channels, int size){
        final ChannelOutputInt[] outputs = new ChannelOutputInt[size];
        Arrays.setAll(outputs, idx -> channels[idx].out());
        return outputs;
    }

    private ChannelOutputInt[] initChannelOutputInt(One2AnyChannelInt[] channels, int size){
        final ChannelOutputInt[] outputs = new ChannelOutputInt[size];
        Arrays.setAll(outputs, idx -> channels[idx].out());
        return outputs;
    }
} // class Main
