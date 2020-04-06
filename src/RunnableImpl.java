import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableImpl implements Runnable {
    private int ticket = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (ticket > 0) {

                try {
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName()+"票剩余"+ --ticket);
                } catch (InterruptedException ex) {
                      ex.printStackTrace();
                }
                lock.unlock();
            }
        }
    }
}
