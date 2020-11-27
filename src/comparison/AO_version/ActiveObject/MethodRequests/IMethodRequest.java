package comparison.AO_version.ActiveObject.MethodRequests;

public interface IMethodRequest {
    boolean canExecute(); // guard
    void execute();
    Integer get_id(); // for log
}
