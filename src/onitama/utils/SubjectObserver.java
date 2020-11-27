package onitama.utils;

public interface SubjectObserver<T> {
    void update(T message);
}
