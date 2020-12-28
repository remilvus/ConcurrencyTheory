-module(lab_11).

-define(WAIT_TIME, 100).
-define(PRODUCER_TIME, ?WAIT_TIME).
-define(CONSUMER_TIME, ?WAIT_TIME*3).

-define(GIVE, give).
-define(TAKE, take).
-define(ACK, ok).

-record(buffer_msg, {source, type, item}).

-export[producer/2, consumer/2, start/0, start/0, start/2, buffer/3].

% helper function
wait_ack() ->
    receive
        #buffer_msg{type=?ACK, item=Item} -> Item 
    end.

% Producer
producer(_, Count) when Count =< 0 -> ok;
producer(Buffer_pid, Count) -> 
    Buffer_pid ! #buffer_msg{source=self(), type=?GIVE, item=Count},
    timer:sleep(?PRODUCER_TIME),
    wait_ack(),
    io:format("Pro ~p ~n", [Count]),
    producer(Buffer_pid, Count - 1).


% Consumer
consumer(_, Count) when Count =< 0 -> ok;
consumer(Buffer_pid, Count) -> 
    Buffer_pid ! #buffer_msg{source=self(), type=?TAKE},
    Item = wait_ack(),
    timer:sleep(?CONSUMER_TIME), % consumer consumes
    io:format("Rec ~p ~n", [Item]),
    consumer(Buffer_pid, Count-1).

% buffer operations
buffer_get(Consumer_PID, Queue) -> 
    {{value, Item}, Queue_2} = queue:out(Queue),
    Consumer_PID ! #buffer_msg{source=self(), type=?ACK, item=Item},
    Queue_2.

buffer_put(Source_PID, Item, Queue) ->
    Source_PID ! #buffer_msg{source=self(), type=?ACK},
    queue:in(Item, Queue).

% buffer message logic
buffer(Queue, Count, Capacity) when Count==Capacity ->
    receive
        #buffer_msg{source=Source,type=?TAKE} -> 
            buffer(buffer_get(Source, Queue), Count-1, Capacity)
    end;
% buffer empty -> receive
buffer(Queue, Count, Capacity) when Count==0 ->
    receive
        #buffer_msg{source=Source,type=?GIVE, item=Item} -> 
            buffer(buffer_put(Source, Item, Queue), Count+1, Capacity)
    end;
buffer(Queue, Count, Capacity) ->
    receive
        #buffer_msg{source=Source_PID, 
                    type=Type, 
                    item=Item} when Type == ?TAKE; Type == ?GIVE ->
                case Type of
                    ?TAKE -> buffer(buffer_get(Source_PID, Queue),
                                    Count-1,
                                    Capacity);
                    ?GIVE -> buffer(buffer_put(Source_PID, Item, Queue),
                                    Count+1,
                                    Capacity);
                    _ -> buffer(Queue, Count, Capacity)
                end;
        _ -> buffer(Queue, Count, Capacity)
    end.


start() -> start(10, 2).
start(Count, Capacity) -> 
    Queue = queue:new(),
    PID = spawn(lab_11, buffer, [Queue, 0, Capacity]),
    spawn(lab_11, consumer, [PID, Count]),
    spawn(lab_11, consumer, [PID, Count]),
    spawn(lab_11, producer, [PID, Count]),
    spawn(lab_11, producer, [PID, Count]).
