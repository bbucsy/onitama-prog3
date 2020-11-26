package onitama.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservedSubject <T> {

    private transient List<SubjectObserver<T>> observers;


    public ObservedSubject(){
        observers = new ArrayList<>();
    }

    public void attachObserver(SubjectObserver<T> observer){
        observers.add(observer);
        observer.update(this,this.getMessage());
    }

    public void removeObserver(SubjectObserver<T> observer){
        observers.remove(observer);
    }

    protected void fireUpdated(){
        for(SubjectObserver observer : observers){
            observer.update(this,this.getMessage());
        }
    }

    protected abstract T getMessage();

}
