import java.util.concurrent.Callable;

public class Task<T> {
    private Callable<? extends T> callable;
    private T object = null;
    public Task(Callable<? extends T> callable){
        this.callable = callable;
    }
    public  T get() {
        if (object == null) {
            synchronized (this) {
                if (object == null) {
                    try {
                        object = callable.call();
                        return object;
                    } catch (Exception e) {}
                }
            }
        }
        return object;
    }
}