package onitama.controller;

public class PlayerLock {

    private final Object lock = new Object();
    private boolean isLocked = false;


    public void waitForPlayerInput() throws InterruptedException {
        synchronized (lock){
            isLocked = false;
            while (!isLocked)
                lock.wait();
        }
    }

    public void playerInputDone(){
        synchronized (lock){
            isLocked = true;
            lock.notify();
        }
    }

}
