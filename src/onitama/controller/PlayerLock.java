package onitama.controller;

/**
 * This class is used by the <code>MainController</code> to handle waiting for user input in the game loop.
 * It needs to synchronize a boolean, because the game loop runs on a different thread, than the UI.
 */
public class PlayerLock {

    /**
     * Because synchronizing a Boolean isn't considered best practice, this object is used
     * for it.
     */
    private final Object lock = new Object();
    /**
     * Stores weather the owner of this object is waiting for an input from the View.
     */
    private boolean waitingForInput = false;

    /**
     * Starts waiting for human input. The UI can signal the lock by the
     * <code>playerInputDone()</code> function of this class.
     *
     * @throws InterruptedException
     */
    public void waitForPlayerInput() throws InterruptedException {
        synchronized (lock) {
            waitingForInput = true;
            while (waitingForInput)
                lock.wait();
        }
    }

    /**
     * Return weather the owner of this is waiting for an input from the UI
     *
     * @return True if user input is required.
     */
    public boolean isWaitingForInput() {
        synchronized (lock) {
            return waitingForInput;
        }
    }

    /**
     * Signals the lock that the player has made an input in the UI.
     */
    public void playerInputDone() {
        synchronized (lock) {
            waitingForInput = false;
            lock.notify();
        }
    }

}
