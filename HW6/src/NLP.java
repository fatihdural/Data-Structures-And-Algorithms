import java.io.File;
import java.io.IOException;
import java.util.*;

public class NLP
{
    private Word_Map wmap;
    private String directory;
    /*Reads the dataset from the given dir and created a word map */
    public void readDataset(String dir)
    {
        directory = dir;            // assign directory.
        wmap = new Word_Map();      // initailize.
        File_Map fileMap = new File_Map();
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        for (File files : listOfFiles) {    // walk all files.
            if (files.isFile()) {
                try {
                    String file = files.getName();  // take file name
                    String filePath = dir.concat("\\" + file);  // with path.
                    Scanner sc2 = new Scanner(new File(filePath));
                    while (sc2.hasNextLine()) {     // take new line.
                        Scanner s2 = new Scanner(sc2.nextLine());
                        while (s2.hasNext()) {  // take new words from line.
                            String s = s2.next();
                            String word = s.trim().replaceAll("\\p{Punct}", "");    // clear.
                            if( !wmap.containsKey(word) ){
                                fileMap = createFileMap(dir, word); // create file map.
                                wmap.put(word, fileMap);    // put it.
                            }
                        }
                    }
                    System.out.println("File " + file + " is being processed..");   // a message for processing.
                } catch (IOException e) {
                    System.out.println("Error accessing input file!");
                }
            }
        }
        System.out.println("----------------------------------\n");
    }

    private File_Map createFileMap(String dir, String word){    // helper method for creating fileMap
        File_Map fileMap = new File_Map();
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        for (File files : listOfFiles) {        // walk all files.
            if (files.isFile()) {
                try {
                    ArrayList<Integer> occurance = new ArrayList<Integer>();
                    String file = files.getName();
                    String filePath = dir.concat("\\" + file);
                    Scanner sc2 = new Scanner(new File( filePath ));
                    int count = 0;
                    while( sc2.hasNextLine() ){
                        Scanner s = new Scanner(sc2.nextLine());
                        while (s.hasNext()) {
                            String scanWord = s.next().trim().replaceAll("\\p{Punct}", ""); // clear.
                            if( scanWord.equals(word) ){
                                occurance.add(count);       // add word location..
                            }
                            count++;        // increment location.
                        }
                    }
                    fileMap.put( file, occurance);  // put file and locations.

                } catch (IOException e) {
                    System.out.println("Exception handled! " + e.getCause());
                }
            }
        }
        return fileMap;
    }

    /*Finds all the bigrams starting with the given word*/
    public List<String> bigrams(String word){
        ArrayList<String> allBiagrams = new ArrayList<>();
        if( wmap.containsKey(word) ){   // if file has contains.
            File_Map fileMap = (File_Map) wmap.get(word);   // get file map.
            File folder = new File(directory);
            File[] listOfFiles = folder.listFiles();
            int count = 0;
            for (File files : listOfFiles) {        // look all files.
                count = -1;
                if (files.isFile()) {
                    try {
                        String file = files.getName();
                        String filePath = directory.concat("\\" + file);
                        Scanner sc2 = new Scanner(new File(filePath));
                        while (sc2.hasNextLine()) {
                            Scanner s2 = new Scanner(sc2.nextLine());
                            while (s2.hasNext()) {
                                String s = s2.next();
                                String scanWord = s.trim().replaceAll("\\p{Punct}", "");
                                ArrayList<Integer> locations = (ArrayList<Integer>) fileMap.get(file);
                                if( locations.contains(count) ){
                                    String bigram = new String(word + " " + scanWord) ;
                                    if( !allBiagrams.contains(bigram) )
                                        allBiagrams.add(bigram);        // add string to biagrams.
                                }
                                count++;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error accessing input file!");
                    }
                    count++;
                }
            }
        }
        return allBiagrams;
    }

    /*Calculates the tfIDF value of the given word for the given file */
    public float tfIDF(String word, String fileName)
    {
        File_Map documentWithTerm = null;
        if( wmap.containsKey(word) ){
            documentWithTerm = (File_Map) wmap.get(word);
        }
        /* IDF */
        int totalNumberDocument = 0;
        float numberDocumentsWithTerm = 0;
        for( int i = 0; i < documentWithTerm.getFnames().size(); i++ ){
            if( !documentWithTerm.getOccurances().get(i).equals( new ArrayList<Integer>() ) ){
                numberDocumentsWithTerm++;       //  Number of documents with term t in t
            }
            totalNumberDocument++;              // Total number of documents
        }
        float IDF = (float) Math.log(   (totalNumberDocument /  numberDocumentsWithTerm)  );  // Calculate IDF

        /* TF */
        ArrayList<Integer> occurance = (ArrayList<Integer>) documentWithTerm.get(fileName);
        int numberTermAppearsDocument =  occurance.size(); // number of times t appears in a document.
        float totalTermsNumbersOfDocument = 0;
        String filePath = directory.concat("\\" + fileName);
        try {
            Scanner s = new Scanner(new File( filePath ));  // Total number of terms in the document)
            while (s.hasNext()) {
                totalTermsNumbersOfDocument++;
                s.next();
            }
        }
        catch (Exception e){
            System.out.println("Exception handled " + e.getCause());
        }

        float TF = numberTermAppearsDocument / totalTermsNumbersOfDocument; // calculate TF
        float TFIDF = TF * IDF;     // calculate TFIDF
        return TFIDF;
    }

    /*Print the WordMap by using its iterator*/
    public  void printWordMap()
    {
        Iterator iter = wmap.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
}
