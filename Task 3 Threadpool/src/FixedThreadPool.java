import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FixedThreadPool implements ThreadPool {
    private int threadCount;
    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final Object lock = new Object();
    private final List<Thread> threads;
    public FixedThreadPool(int threadCount){
        this.threadCount = threadCount;
        threads = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++){
            threads.add(new MyThread());
        }
    }
    @Override
    public  void start(){
        for(int i=0;i<threadCount;i++){
            threads.get(i).start();
        }
    }
    public synchronized void execute(Runnable runnable)
    {
        synchronized (lock){
            tasks.add(runnable);
            lock.notify();
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run(){
            while (true){
                Runnable task;
                synchronized (lock){
                    while (tasks.isEmpty()){
                        try{
                            lock.wait();
                        } catch (InterruptedException e) {}
                    }
                    task = tasks.poll();
                }
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}