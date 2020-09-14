import java.util.EmptyStackException;
/**
 * implementing my own data structure.
 */
public class Stack {

    /**
     * this inner class has for holding the coordinates and its data.
     */
    public class Coordinate{    // this inner class hold coordinate and its data.
        private int x = 0;
        private int y = 0;
        private int data = 0;
        /**
         * x-coordinate, y-coordinate and data ( 1 - 0 ) constructor.
         */
        public Coordinate(int x, int y, int data){
            this.x = x;
            this.y = y;
            this.data = data;
        }
        /**
         * get x-coordinate
         */
        public int getX(){
            return x;
        }
        /**
         * get y-coordinate
         */
        public int getY(){
            return y;
        }
        /**
         * get data (1 - 0).
         */
        public int getData(){
            return data;
        }
    }

    private int size = 0;
    private int capacity = 10;
    private Coordinate [] arr;
    /**
     * stack default constructor.
     */
    public Stack(){     // This stack holds elements in array which is coordinate type.
        size = 0;
        capacity = 10;
        arr = new Coordinate[10];
    }
    /**
     * occupancy control method.
     */
    public boolean empty(){
        if( size == 0 )
            return true;
        return false;
    }
    /**
     * return the last element but, not remove
     */
    public Coordinate peek(){
        return arr[size - 1];
    }
    /**
     * return the last element and remove
     */
    public Coordinate pop(){
        if (empty()) {
            throw new EmptyStackException();
        }
        Coordinate temp = arr[size - 1];
        size--;
        return temp;
    }
    /**
     * adding a new element at the top of stack.
     */
    public Coordinate push(int x, int y, int data){

        if( size == capacity ){
            capacity *= 2;
            Coordinate [] arr2 = new Coordinate[capacity];
            for( int i = 0; i < capacity / 2; i++){
                arr2[i] = arr[i];
            }
            arr = arr2;
        }
        arr[size] = new Coordinate(x,y,data);
        size++;
        return new Coordinate(x,y,data);
    }
}
