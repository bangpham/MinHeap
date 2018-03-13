/**
 * Your implementation of a Min Heap
 *
 * @author Bang Pham
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    // Do NOT add or modify any of these instance variables
    private T[] backingArray;
    private int size;
    /**
     * No args constructor for MinHeap.
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     */
    public MinHeap() {
        this(INITIAL_CAPACITY);
    }
    /**
     * Constructor for MinHeap.
     * Creates a Heap with an initial capacity of initialCapacity.
     * @param initialCapacity capacity of the new array to initialize
     */
    @SuppressWarnings("unchecked")
    public MinHeap(int initialCapacity) {
        backingArray = (T[]) new Comparable[initialCapacity];
    }
    /**
     * Constructor for MinHeap.
     * For this constructor, initialize the backing array to fit the passed in
     * data exactly.
     * When this constructors returns, the backing array should satisfy all
     * the properties of a heap, by calling heapify helper method to sort
     * subtrees from bottom to top.
     * @param values values to initialize the heap with
     */
    @SafeVarargs
    public MinHeap(T... values) {
        this(values.length + 1);
        for (int i = 1; i < values.length + 1; i++) {
            backingArray[i] = values[i - 1];
            size++;
        }
        for (int i = size / 2; i >= 1; i--) {
            heapify(i);
        }
    }
    
    /**
     * Private helper method to sort the heap.
     * Sort by bubbling up sub trees.
     * Compare smaller child to parent, and swap if child is smaller.
     * Caller should sort from right to left, bottom to top fashion.
     * @param index index of subtree.
     */
    
    private void heapify(int index) {
        int left = leftChild(index);
        int right = rightChild(index);
        int smallest = index;
        if (size >= left
                && backingArray[left].compareTo(backingArray[index]) < 0) {
            smallest = left;
        }
        //There is no case where rightChild exist without leftChild.
        if (size >= right
                && backingArray[right].compareTo(backingArray[index]) < 0
                && backingArray[right].compareTo(backingArray[left]) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    /**
     * Helper method to resize backing array.
     * Double the size of array.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] temp =  (T[]) new Comparable[backingArray.length * 2];
        for (int i = 1; i < backingArray.length; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else {
            if ((size + 1) == backingArray.length) {
                resize();
            }
            size++;
            backingArray[size] = data;
            int current = size;
            while (current > 1
                && backingArray[current]
                        .compareTo(backingArray[parent(current)]) < 0) {
                swap(current, parent(current));
                current = parent(current);
            }
        }      
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("heap is empty");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        size--;
        heapify(1);
        backingArray[size + 1] = null;
        return temp;
    }

    /**
     * Helper method to swap values of two indices.
     * @param index1 first index to swap.
     * @param index2 second index to swap with first.
     */
    private void swap(int index1, int index2) {
        T tmp = backingArray[index1];
        backingArray[index1] = backingArray[index2];
        backingArray[index2] = tmp;
    }
    
    /**
     * Helper method to get parent index of current index.
     * @param position of current index.
     * @return parent index of current index.
     */
    private int parent(int position) {
        position = position / 2;
        return position;
    }

    /**
     * Helper method to get left child index of current index.
     * @param position of current index.
     * @return left child index of current index.
     */
    private int leftChild(int position) {
        position = position * 2;
        return position;
    }

    /**
     * Helper method to get right child index of current index.
     * @param position of current index.
     * @return right child index of current index.
     */
    private int rightChild(int position) {
        position = (position * 2) + 1;
        return position;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }
    
    // Do NOT edit or use this method in your code
    @Override
    public Comparable[] getBackingArray() {
        return backingArray;
    }
}