import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutionManagerRealisation implements ExecutionManager {
    @Override
    public Context execute(Runnable callback, Runnable... tasks){
        return new ContextRealisation(callback, tasks);
    }
}