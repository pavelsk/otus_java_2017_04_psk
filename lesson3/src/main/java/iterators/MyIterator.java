package iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyIterator<E> implements Iterator<E> {
    private int cursor;
    private int size;
    private E[] elements;

    public MyIterator(E[] elements, int size) {
        this.elements = elements;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return cursor != size;
    }

    @Override
    public E next() {
        if (cursor >= size)
            throw new NoSuchElementException();
        return (E) elements[cursor++];
    }
}
