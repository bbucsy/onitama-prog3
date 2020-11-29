package onitama.controller;

public class PlayerLock {

    private final Object lock = new Object();
    private boolean waitingForInput = false;


    public void waitForPlayerInput() throws InterruptedException {
        synchronized (lock) {
            waitingForInput = true;
            while (waitingForInput)
                lock.wait();
        }
    }

    public boolean isWaitingForInput() {
        synchronized (lock) {
            return waitingForInput;
        }
    }

    public void playerInputDone() {
        synchronized (lock) {
            waitingForInput = false;
            lock.notify();
        }
    }

}
