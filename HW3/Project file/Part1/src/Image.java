import java.io.*;
/**
 * this class calculates the number of white components and print that number on screen
 */
public class Image {
    /**
     * constructor takes string filename with path, and call processFile and printNumber method.
     */
    public Image(String filename){
        int number = processFile(filename); // find number of white components
        printNumber(number);                // print it.
    }
    /**
     * this method open the file, look all elements and find white compenents. And return the number of components.
     */
    private int processFile(String filename){
        int single, row_count = 1, column_count = 0;
        int count = 0;
        try{
            BufferedReader br = new BufferedReader( new FileReader(filename) );
            while( (single = br.read()) != -1 )     // finding row and column number.
            {
                if( single == 48 ){         // if elements are 0 and 1, increase the count.
                    count++;
                }
                else if( single == 49 ){
                    count++;
                }
                else if ( single == 10 ){       // 10 for new line character.
                    if( column_count == 0 )        // new line number is row count
                        column_count = count;       // it's column count until seeing the new line.
                    row_count++;
                }
            }

            int [][] arr = new int[row_count][column_count];       // 2 dimensional array for elements
            br = new BufferedReader( new FileReader(filename) );

            for( int i = 0; i < row_count; i++){        // all elements put to array.
                for( int j = 0; j < column_count; j++){
                    single = br.read();
                    if( single - 48 < 0 ){
                        j--;
                    }
                    else{
                        arr[i][j] = single - 48;
                    }
                }
            }

            count = 0;
            for(int i = 0; i < row_count; i++){
                for( int j = 0; j < column_count; j++){
                    if( arr[i][j] == 1 ){
                        count++;
                        arr[i][j] = 0;  // all 1 element will be 0 and push the stack.
                        Stack stack = new Stack();  // stack is provide walking around element that is 1.
                        stack.push(i,j,1);
                        if( j+1 < column_count && arr[i][j+1] == 1){    // all direction scan.
                            stack.push(i,j+1,1);
                            arr[i][j+1] = 0;
                        }
                        if( j-1 >= 0  && arr[i][j-1] == 1){
                            stack.push(i,j-1,1);
                            arr[i][j-1] = 0;
                        }
                        if( i+1 < row_count && arr[i+1][j] == 1){
                            stack.push(i+1,j,1);
                            arr[i+1][j] = 0;
                        }
                        if( i-1 >= 0 && arr[i-1][j] == 1){
                            stack.push(i-1,j,1);
                            arr[i-1][j] = 0;
                        }

                        while( !stack.empty() ){    // stack provides walking like that recursive
                            Stack.Coordinate temp = stack.pop();
                            int x = temp.getX();
                            int y = temp.getY();

                            if( y+1 < column_count && arr[x][y+1] == 1){
                                stack.push(x,y+1,1);
                                arr[x][y+1] = 0;
                            }
                            if( y-1 >= 0 && arr[x][y-1] == 1){
                                stack.push(x,y-1,1);
                                arr[x][y-1] = 0;
                            }
                            if( x+1 < row_count && arr[x+1][y] == 1){
                                stack.push(x+1,y,1);
                                arr[x+1][y] = 0;
                            }
                            if( x-1 >= 0 && arr[x-1][y] == 1){
                                stack.push(x-1,y,1);
                                arr[x-1][y] = 0;
                            }
                        }
                    }
                }
            }
            return count;
        }

        catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        return count;
    }
    /**
     *  print the number of white components.
     */
    private void printNumber(int number){
        System.out.println( "The number of white components : " + number );
    }
}
