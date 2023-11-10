package Iterator;

public interface MediaIterator<T> {
    boolean hasNext();

    T getNext();

    void reset();
}
