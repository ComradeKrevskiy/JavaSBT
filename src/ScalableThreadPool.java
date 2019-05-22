import java.util.concurrent.ConcurrentLinkedQueue;

public class ScalableThreadPool implements ThreadPool {
    private final Object lock = new Object();
    private final int min;
    private final int max;
    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private volatile int currentThread;
    private volatile int currentWorkedThreads;
    public ScalableThreadPool(int min,int max){
        this.max=max;
        this.min=min;
    }
    public class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    if (currentThread > min && tasks.isEmpty()) {
                        currentThread--;
                        break;
                    }
                }
                Runnable task;
                synchronized (lock){
                    while (tasks.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {}
                    }
                    currentWorkedThreads++;
                    task = tasks.poll();
                }
                task.run();
                currentWorkedThreads--;
            }
        }
    }
    @Override
    public void start(){
        for(int i = 0; i < min; i++){
            MyThread thread = new MyThread();
            thread.start();
            currentThread++;
        }
    }
    public synchronized void execute(Runnable runnable){
        synchronized (lock){
            tasks.add(runnable);
            if(currentWorkedThreads + tasks.size()>=currentThread && currentThread<max){
                currentThread++;
                new ScalableThreadPool.MyThread().start();
            } else lock.notify();
        }
    }
}
