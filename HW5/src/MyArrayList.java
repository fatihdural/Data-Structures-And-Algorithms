import java.util.Arrays;
/**
 * implementing my own data structure.
 */
public class MyArrayList{
    private int size;
    private int capacity;
    private int [] arr;
    /**
     * Default constructor, it provides the initialize.
     */
    public MyArrayList(){
        size = 0;
        capacity = 10;  // iniatilize.
        arr = new int[capacity];
    }
    /**
     * Adding new element end of the list.
     */
    public boolean add(int item){   // add item at end of the list.
        if( size == capacity ){     // if list is full, reallocate.
            capacity *= 2;
            arr = Arrays.copyOf(arr, capacity);
        }
        arr[size] = item;
        size++;
        return true;
    }
    /**
     * Returning size of list.
     */
    public int size(){
        return size;
    }
    /**
     * Return element in given index.
     */
    public int get(int index){
        return arr[index];
    }
    public int remove(int index){   // remove the element in the given index.
        if( index < 0){     // array index throw.
            throw new ArrayIndexOutOfBoundsException();
        }
        int removed = arr[index];
        for( int i = index; i+1 < size; i++){   // write on the element of previous.
            arr[i] = arr[i+1];
        }
        size--;
        return removed;
    }
    /**
     * Set element in the given index.
     */
    public int set(int index, int item){       // set the element in the given index.
        arr[index] = item;
        return item;
    }
}
