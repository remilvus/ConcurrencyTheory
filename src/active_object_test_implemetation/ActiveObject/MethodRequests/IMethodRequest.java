package active_object_test_implemetation.ActiveObject.MethodRequests;

public interface IMethodRequest {
    boolean can_execute(); // guard
    void call();
}
