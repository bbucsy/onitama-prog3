package onitama.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ObservedSubject<T> implements Serializable {

    //Observers are part of the UI, so we don't save references to them
    //during serialization.
    private transient List<SubjectObserver<T>> observers;


    public ObservedSubject() {
    }

    public void attachObserver(SubjectObserver<T> observer) {
        if (observers == null)
            observers = new ArrayList<>();

        observers.add(observer);
        observer.update(this.getMessage());
    }

    @SuppressWarnings("unused")
    public void removeObserver(SubjectObserver<T> observer) {
        if (observers != null)
            observers.remove(observer);
    }

    protected void fireUpdated() {
        if (observers != null)
            for (SubjectObserver<T> observer : observers) {
                observer.update(this.getMessage());
            }
    }

    protected abstract T getMessage();

}
