import java.util.EmptyStackException;
/**
 * implementing my own generic type data structure.
 */
public class Stack<E> {     // generic type stack
    private int size = 0;
    private int capacity = 10;
    private Object [] arr;  // because of there is not directly access in generic type, ı hold Object type array.
    /**
     * default constructor.
     */
    public Stack(){         // default constructor.
        size = 0;
        capacity = 10;
        arr = new Object[10];
    }
    /**
     * occupancy control method.
     */
    public boolean empty(){    // occupancy control
        if( size == 0 )
            return true;
        return false;
    }
    /**
     * return the last element but, not remove
     */
    public E peek(){
        return (E) arr[size - 1];
    }
    /**
     * return the last element and remove
     */
    public E pop(){
        if (empty()) {
            throw new EmptyStackException();
        }
        E temp = (E) arr[size - 1];
        size--;
        return (E) temp;
    }
    /**
     * adding a new element at the top of stack.
     */
    public E push(E item){
        if( size == capacity ){
            capacity *= 2;
            Object [] arr2 = new Object[capacity];
            for( int i = 0; i < capacity / 2; i++){
                arr2[i] = arr[i];
            }
            arr = arr2;
        }
        arr[size] = item;
        size++;
        return (E) item;
    }
}
