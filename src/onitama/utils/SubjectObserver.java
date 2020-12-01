package onitama.utils;

/**
 * Interface implementing a very basic observer interface, because the built one is marked as deprecated.
 * @param <T> The type of the message sent to the observers
 */
public interface SubjectObserver<T> {

    /**
     * The function called by the observed subjects, when an update is fired.
     * @param message A message sent by the observed object, usually containing the changes or the object itself
     */
    void update(T message);
}
