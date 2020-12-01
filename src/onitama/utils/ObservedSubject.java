package onitama.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements a very basic Observer-Observable connection, because the built in Observable abstract class
 * and the corresponding Observer interface are marked as deprecated.
 *
 * It is a generic abstract class, so that there is no need for casting the message
 * @param <T> The type of the message that is sent from the observable
 */
public abstract class ObservedSubject<T> implements Serializable {


    /**
     * List of observers.
     * This is transient, because there is no point in serializing references to the gui
     */
    private transient List<SubjectObserver<T>> observers;

    /**
     * Constructor
     */
    public ObservedSubject() {
    }

    /**
     * Adds an observer to the list of observers.
     * @param observer
     */
    public void attachObserver(SubjectObserver<T> observer) {
      /*If there the list isn't initialized because the object has been deserialized
        or it's the first observer to be added, the function creates the list.*/
        if (observers == null)
            observers = new ArrayList<>();

        observers.add(observer);
        observer.update(this.getMessage());
    }

    /**
     * Removes an observer
     * @param observer the observer to be removed
     */
    @SuppressWarnings("unused")
    public void removeObserver(SubjectObserver<T> observer) {
        if (observers != null)
            observers.remove(observer);
    }

    /**
     * Calls the update function of every observable.
     */
    protected void fireUpdated() {
        if (observers != null)
            for (SubjectObserver<T> observer : observers) {
                observer.update(this.getMessage());
            }
    }

    /**
     * Abstract function that needs to be overriden
     * @return The message given to the observers
     */
    protected abstract T getMessage();

}
