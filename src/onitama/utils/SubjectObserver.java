package onitama.utils;

public interface SubjectObserver<T> {
    void update (ObservedSubject<T> sender, T message);
}
