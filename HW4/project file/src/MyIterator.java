import java.util.Iterator;
public class MyIterator implements Iterator {

    public int toRight(int a[][], int row, int column, int r, int c){   // to right walking
        if( c < column && a[r][c] != -1 ){
            printArr[size] = a[r][c];
            size++;
            a[r][c] = -1;
            c = toRight(a, row, column, r, c+1);
            return c;
        }
        return c;
    }

    public int toDown(int a[][], int row, int column, int r, int c){    // to down walking
        if( r >= 0 && c >= 0 && r < row && a[r][c] != -1 ){
            printArr[size] = a[r][c];
            size++;
            a[r][c] = -1;
            c = toDown(a, row, column, r+1, c);
            return c;
        }
        return r;
    }

    public int toLeft(int a[][], int row, int column, int r, int c){     // to down left
        if( r >= 0 && c >= 0 && r < row && c < column && a[r][c] != -1 ){
            printArr[size] = a[r][c];
            size++;
            a[r][c] = -1;
            c = toLeft(a, row, column, r, c-1);
            return c;
        }
        return c;
    }

    public int toUp(int a[][], int row, int column, int r, int c){       // to down up
        if( r >= 0 && c >= 0 && r < row && c < column && a[r][c] != -1 ){
            printArr[size] = a[r][c];
            size++;
            a[r][c] = -1;
            c = toUp(a, row, column, r-1, c);
            return c;
        }
        return r;
    }

    public boolean control(int a[][], int row, int column, int r, int c){
            if( r+1 < row && c+1 < column  ){               // control for element
                if(a[r][c] == -1){
                    if( a[r][c+1] == -1 ){
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return true;
    }

    public void convertSpiral(int a[][], int row, int column, int r, int c) // convert matrix.
    {
        if( row == 0 ){
            toRight(a,row,column,0,0);
            return;
        }
        else if( column == 0 ){
            toDown(a,row,column, 0,0);
            return;
        }

        else if( r >= 0 && c >= 0 && r < row && c < column ){
            c = toRight(a, row, column, r, c);
            r = toDown(a, row, column, r+1, c-1);
            c = toLeft(a, row, column, r-1, c-2);
            r = toUp(a, row, column, r-2, c + 1);

            if( !control(a, row, column, r+1, c+2) ){
                convertSpiral(a, row, column, r+1, c+2);

            }
        }
    }
    @Override       // override functions.
    public boolean hasNext() {
        if( size < row * column ){
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        int temp = printArr[size];
        size++;
        return temp;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private int [][] arr;
    private int [] printArr;
    private int row;
    private int column;
    private int size;

    MyIterator(){
        arr = null;
        printArr = null;
        row = 0;
        column = 0;
        size = 0;
    }
    MyIterator(int [][] arr, int row, int column){
        this.arr = arr;
        this.row = row;
        this.column = column;
        printArr = new int[row*column];
        convertSpiral(arr, row, column, 0, 0);
        size = 0;
    }
}
