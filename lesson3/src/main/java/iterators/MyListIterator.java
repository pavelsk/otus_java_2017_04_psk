package iterators;

import java.util.*;

public class MyListIterator<E> implements ListIterator<E> {
    private int cursor;
    private int last = -1;
    private int size;
    private List<E> list;

    public MyListIterator(List<E> list, int size) {
        this.list = list;
        this.size = size;
    }

    public MyListIterator(List<E> list, int size, int index) {
        this.list = list;
        this.size = size;
        cursor = index;
    }

    @Override
    public boolean hasNext() {
        return cursor != size;
    }

    @Override
    public E next() {
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        cursor = i + 1;
        return (E) list.get(last = i);
    }

    @Override
    public boolean hasPrevious() {
        return cursor != 0;
    }

    @Override
    public E previous() {
        int i = cursor - 1;
        if (i < 0)
            throw new NoSuchElementException();
        cursor = i;
        return (E) list.get(last = i);
    }

    @Override
    public int nextIndex() {
        return cursor + 1;
    }

    @Override
    public int previousIndex() {
        return cursor - 1;
    }

    @Override
    public void remove() {
        list.remove(last);
        cursor = last;
        last = -1;
        size--;
    }

    @Override
    public void set(E e) {
        list.set(last, e);
        last = -1;
    }

    @Override
    public void add(E e) {
        list.set(cursor + 1, e);
        last = -1;
        size++;
    }
}
