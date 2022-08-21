package com.mengtu.structer.linear;

import java.util.Arrays;
import java.util.Objects;

public class MyArray<E> extends AbstractList<E> {

    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public MyArray(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }

    public MyArray() {
        this(DEFAULT_CAPACITY);
    }


    /**
     * 清楚所有元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * 元素的个数
     */
    public int size() {
        return size;
    }

    /**
     * 元素是否为空
     *
     * @return 返回结果
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某个元素
     *
     * @param element 元素
     * @return 返回
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加
     *
     * @param element sa
     */
    public void add(E element) {
        add(size,element);
    }

    public void add(int index,E element){
        rangeCheckForAdd(index);
        ensureCapacity(size+1);
        if (size - 1 - index >= 0) System.arraycopy(elements, index + 1, elements, index + 1 + 1, size - 1 - index);
        elements[index] = element;
        size++;
    }
    /**
     * 获取元素
     */
    public E get(int index) {

        return elements[index];
    }
    private void rangeCheckForAdd(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
    }
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    public E remove(int index) {
        E old = elements[index];
        for (int i = index; i < size -1; i++) {
            elements[i] = elements[i+1];
        }
        size--;
        elements[size] = null;
        return old;
    }

    private void ensureCapacity(int capacity){
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        int newCapacity = oldCapacity + oldCapacity >> 1;
        E newElements[] = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
    public int indexOf(E element) {
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        }else {
            for (int i = 0; i < size; i++)
                if (element.equals(elements[i])) return i;
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != 0){
                sb.append(", ");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArray<?> array = (MyArray<?>) o;
        return size == array.size && Arrays.equals(elements, array.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    public static void main(String[] args) {
       /* MyArray<Integer> array = new MyArray<>();
        array.add(10);
        array.add(11);
        array.add(12);
        array.add(13);
        System.out.println(array);
//        array.remove(0);
//        System.out.println(array);
        array.add(0,9);
        System.out.println(array);*/
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
