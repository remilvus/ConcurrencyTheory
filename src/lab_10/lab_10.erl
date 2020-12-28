-module(lab_10).
% -compile(export_all).

-export[start1/0, start2/0, a/2, b/2, c3/0, c1_a/0, c1_b/0, c2/0].


a(PID_c, Count) -> 
    case Count of
        0 -> ok;
        Count -> PID_c ! {Count, aaa},
                 a(PID_c, Count - 1)
    end.

b(PID_c, Count) -> 
    case Count of
        0 -> ok;
        Count -> PID_c ! {Count, bbb},
                 b(PID_c, Count - 1)
    end.


c1_a() ->
    receive
        {_, aaa} -> io:format("aaa~n", []), c1_b()
    end.

c1_b() ->
    receive
        {_, bbb} -> io:format("bbb~n", []), c1_a()
    end.

c2() ->
    receive
        {Iter, Msg} when Msg == aaa; Msg == bbb ->
             io:format("~p ~p ~n", [Msg, Iter]),
             c2()
    end.

c3() ->
    receive
        Msg -> io:format("~p ~n", [Msg]), c3()
    end.

start1() -> 
    PID_c = spawn(lab_10, c1_a, []),
    spawn(lab_10, a, [PID_c, 100]),
    spawn(lab_10, b, [PID_c, 100]).


start2() -> 
    PID_c = spawn(lab_10, c2, []),
    spawn(lab_10, a, [PID_c, 500]),
    spawn(lab_10, b, [PID_c, 500]).

start2() -> 
    PID_c = spawn(lab_10, c3, []),
    spawn(lab_10, a, [PID_c, 500]),
    spawn(lab_10, b, [PID_c, 500]).