package lab_07.ActiveObject.MethodRequests;

public interface IMethodRequest {
    boolean canExecute(); // guard
    void execute();
    Integer get_id(); // for log
}
