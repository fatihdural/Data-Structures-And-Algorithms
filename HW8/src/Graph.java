import java.util.*;

public class Graph{         // Graph class.
    private int [][] edges;     // edges keep edges.
    private int numV;           // number of vertices.
    public void createGraph(Scanner scan){
        int flag = 0;
        while (scan.hasNextLine()) {     // take new line.
            Scanner s2 = new Scanner(scan.nextLine());
            int source = 0;
            int dest = 0;
            while (s2.hasNext()) {  // take new num from line.
                String word = s2.next();
                int num = Integer.parseInt(word);
                if( flag == 0 ){        // for first line
                    numV = num;         // number of vertices
                    flag++;
                }
                else if( flag == 1){
                    flag++;
                    edges = new int[numV][numV];        // memory allocation.
                    for( int i = 0; i < numV; i++){
                        for( int j = 0; j < numV; j++){
                            edges[i][j] = 0;
                        }
                    }
                }
                else if( flag % 2 == 0 ) {      // numbers start from 1 ... numV.
                    source = num-1;
                    flag++;
                }
                else{
                    dest = num-1;
                    flag++;
                }
            }
            if( flag > 2){                      // transitive property
                edges[source][dest] = 1;
                for( int i = 0; i < numV; i++){
                    if( edges[i][source] == 1 ){
                        if( edges[i][dest] == 0 ){
                            edges[i][dest] = 2;    // assign 2, do not mix with the normal think.
                        }
                    }
                }
            }
        }
        int popularCount = calculatePopular();  // calculate and print the result
        System.out.println(popularCount);
    }

    public int getNumV(){
        return numV;
    }

    private int calculatePopular(){
        int count = 1;          // Calculate the number of people who are considered popular by every other person.
        int popularCount = 0;
        for( int i = 0; i < numV; i++){
            for(int j = 0; j < numV; j++){
                if( edges[j][i] != 0 && (j!= i) ){  // except himself.
                    count++;
                }
            }
            if( count == numV ){
                popularCount++;
            }
            count = 1;
        }
        return popularCount;
    }
    private void printGraph(){
        for( int i = 0; i < numV; i++){
            for( int j = 0; j < numV; j++){
                System.out.print(edges[i][j] + " ");
            }
            System.out.println();
        }
    }
}
