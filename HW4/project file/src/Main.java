import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        int [][] arr ={ {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20} };
        Iterator iter = new MyIterator(arr, 4, 5);
        while(iter.hasNext()){
            System.out.print(iter.next() + " ");
        }
        System.out.println();
        int [][] arr2 ={ {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16} };
        Iterator iter2 = new MyIterator(arr2, 4, 4);
        while(iter2.hasNext()){
            System.out.print(iter2.next() + " ");
        }
    }
}
