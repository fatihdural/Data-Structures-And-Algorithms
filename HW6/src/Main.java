import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String dir = "dataset/";        // dataset - src - input.txt are same priority directory. If you can, change it.
        String instructionsFile = "input.txt";  // if you change input.txt location, please give correct path.
        NLP natLanProc = new NLP();     // create NLP object.
        natLanProc.readDataset(dir);   // read data set and create Word Map.
        //nlp.printWordMap();           // if you want, you can print Word Map.
        process(natLanProc, dir, instructionsFile);        // homework job done.
    }

    private static void process( NLP nlp, String directory, String instructionsFile ){
        try {
            Scanner sc2 = new Scanner(new File( instructionsFile ));
            while( sc2.hasNextLine() ){
                Scanner s = new Scanner(sc2.nextLine());
                while (s.hasNext()) {
                    String word = s.next().trim().replaceAll("\\p{Punct}", "");
                    if( word.equals("tfidf") ){ // if word is tfidf
                        word = s.next().trim().replaceAll("\\p{Punct}", "");    // corrected
                        String fileName = s.next().trim().replaceAll("\\p{Punct}", ""); // corrected
                        float TFIDF = nlp.tfIDF(word, fileName);        // calculate TFIDF = TF * IDF.
                        System.out.println(TFIDF);
                    }
                    else if( word.equals("bigram") ){   // if word is biagram
                        word = s.next().trim().replaceAll("\\p{Punct}", "");    // corrected
                        ArrayList<String> bigram = (ArrayList<String>) nlp.bigrams(word);   // calculate biagram
                        System.out.print("[");      // print standart output.
                        for( int i = 0; i < bigram.size()-1; i++){
                            System.out.print(bigram.get(i) + ", ");
                        }
                        System.out.print(bigram.get(bigram.size() - 1));
                        System.out.print("]\n");
                    }
                    System.out.println();
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception handled! " + e.getCause());
        }
    }
}
