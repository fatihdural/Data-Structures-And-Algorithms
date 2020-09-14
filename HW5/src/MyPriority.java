import java.util.*;
/**
 * implementing my own data structure.
 */
public class MyPriority<E> {
    private final MyArrayList theData = new MyArrayList();    // private variables.
    private int size;
    Comparator comparator;      // object for compare
    /**
     * comparator constructor.
     */
    public MyPriority(Comparator comp) {
        comparator = comp;
    }
    /**
     * Adding new element end of the queue.
     */
    public boolean offer(int item) {    // add element end of the queue.
        theData.add(item);      // Add the item to the heap.
        int child = theData.size() - 1; // child is newly inserted item.
        int parent = (child - 1) / 2; // Find child’s parent.
        while (parent >= 0 && comparator.compare(theData.get(parent),
                theData.get(child)) > 0) {  // Reheap
            swap(parent, child);
            child = parent;
            parent = (child - 1) / 2;
        }
        size++;
        return true;
    }
    /**
     * Remove the element according to priority in the queue.
     */
    public int poll() {         // remove the first priority element of queue.
        if (size < 1) {
            synchronized ( theData ){
                try {
                    ((Object) theData).wait();  // wait for adding new element.
                }
                catch (Exception e){
                    System.out.println("exception handled");
                }
            }
            return 0;
        }
        int result = theData.get(0);    // Save the top of the heap.
        if (theData.size() == 1) {  // If only one item then remove it.
            theData.remove(0);
            size--;
            return result;
        }
/* Remove the last item from the ArrayList and place it into
the first position. */
        theData.set(0, theData.remove(theData.size() - 1));
        int parent = 0;     // The parent starts at the top.
        while (true) {
            int leftChild = 2 * parent + 1;
            if (leftChild >= theData.size()) {
                break; // Out of heap.
            }
            int rightChild = leftChild + 1;
            int minChild = leftChild; // Assume leftChild is smaller.
            if (rightChild < theData.size() // See whether rightChild is smaller.
                    && comparator.compare(theData.get(leftChild),
                    theData.get(rightChild)) > 0) {
                minChild = rightChild;
            }
            if (comparator.compare(theData.get(parent),
                    theData.get(minChild)) > 0) {   // Move smaller child up heap if necessary.
                swap(parent, minChild);
                parent = minChild;
            } else { // Heap property is restored.
                break;
            }
        }
        size--;
        return result;
    }
    /**
     * Swap given elements from queue.
     */
    private void swap(int x, int y){     // swap queue elements.
        int temp = theData.get(x);
        theData.set(x, theData.get(y));
        theData.set(y, temp);
    }
}
