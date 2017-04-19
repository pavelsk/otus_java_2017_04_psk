package list;

import exceptions.MethodIsNotSupportedException;
import iterators.MyIterator;
import iterators.MyListIterator;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public static final int INCREASE_RATIO = 2;

    private T elements[];

    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        if (capacity > 0) {
            elements = (T[]) new Object[capacity];
        } else {
            elements = (T[]) new Object[DEFAULT_CAPACITY];
        }
    }

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<T>(elements, size);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size)
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(T t) {
        checkCapacity(size + 1);  // Increments modCount!!
        elements[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elements[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elements[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T e : c)
            add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        checkCapacity(size + c.size());

        Object[] a = c.toArray();
        int numNew = a.length;
        checkCapacity(size + a.length);

        System.arraycopy(a, 0, elements, index, numNew);
        size += a.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new MethodIsNotSupportedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new MethodIsNotSupportedException();
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            elements[i] = null;

        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return elements[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        };

        elements[index] = element;

        return elements[index];
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        checkCapacity(size + 1);

        System.arraycopy(elements, index, elements, index + 1,
                size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        T oldValue = this.elements[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elements, index + 1, elements, index,
                    numMoved);
        elements[--size] = null;

        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elements[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elements[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elements[i] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elements[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator<T>(this, this.size);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index);
        return new MyListIterator<T>(this, this.size, index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new MethodIsNotSupportedException();
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    private void checkCapacity(int capacity) {
        if (elements.length == 0) {
            capacity = Math.max(DEFAULT_CAPACITY, capacity);
        }

        if (capacity - elements.length > 0) {
            if (elements.length == MAX_ARRAY_SIZE) {
                throw new OutOfMemoryError();
            }

            capacity *= INCREASE_RATIO;

            if (capacity > MAX_ARRAY_SIZE) {
                capacity = MAX_ARRAY_SIZE;
            }

            elements = Arrays.copyOf(elements, capacity);
        }
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elements, index + 1, elements, index,
                    numMoved);
        elements[--size] = null;
    }
}
