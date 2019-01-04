package nero.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {


    public static void main(String[] args) {
        ReadWrite readWrite = new ReadWrite();
        for (int i = 0; i < 100; i++) {


            new Thread(() -> {
                readWrite.getObject();
            }).start();

            int finalI = i + 1;

            new Thread(() -> {
                readWrite.setObject(finalI);
            }).start();
        }

    }


}

class ReadWrite{

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Integer object = null;

    public void getObject() {

        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            System.out.println("准备读数据" + object);
            TimeUnit.MICROSECONDS.sleep(10000);
            System.out.println("读出的数据为" + object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    public void setObject(Integer object) {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            System.out.println("准备写数据" + object);
            TimeUnit.MICROSECONDS.sleep(10000);
            System.out.println("写数据" + object);
            this.object = object;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
