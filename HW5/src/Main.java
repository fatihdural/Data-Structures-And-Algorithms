import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Main class, it take an image, read image pixels and print.
 */
public class Main {
    /**
     * Testing function called main()
     */
    public static void main(String[] args) {
        Thread thread1 = new Thread(new startThread(args[0]));  // thread1 is starting using args[0] filepath.
        thread1.start();
    }

    private static MyPriority PQLEX = new MyPriority(new LexComparator() );     // queues.
    private static MyPriority PQEUC = new MyPriority(new EucComparator() );
    private static MyPriority PQBMX = new MyPriority(new BmxComparator() );
    /**
     * Read image, offer and print. Start thread2,3,4.
     */
    static class startThread implements Runnable{   // thread1.
        int count = 0;
        String filename;
        /**
         * Constructor
         */
        public startThread(String filename){    // take filepath with filename
            this.filename = filename;
        }
        /**
         * Run method, it called when 100 pixels inserted.
         */
        @Override
        public void run() {
            BufferedImage img = null;
            try{
                File f = new File(filename);
                img = ImageIO.read(f);      // read file.
            }catch(IOException e){
                System.out.println(e);
            }
            int width = img.getWidth();     // take width and height information.
            int height = img.getHeight();
            secondThread scndThread = new secondThread(width, height);  // create object for threads.
            thirdThread thrdThread = new thirdThread(width, height);
            fourthThread frthThread = new fourthThread(width, height);

            Thread thread2 = new Thread(scndThread);
            Thread thread3 = new Thread(thrdThread);
            Thread thread4 = new Thread(frthThread);

            int [][] RGB = new int[width][height];
            for( int i = 0; i < width; i++){        // first take all elements from picture.
                for( int j = 0; j < height; j++){
                    RGB[i][j] = img.getRGB(i,j);
                }
            }
            synchronized (PQLEX){       // synchronizations
                synchronized (PQEUC){
                    synchronized (PQBMX){
                        for( int i = 0; i < width; i++){
                            for( int j = 0; j < height; j++){
                                PQLEX.offer( RGB[i][j] ); // add element.
                                PQEUC.offer( RGB[i][j] );
                                PQBMX.offer( RGB[i][j] );
                                System.out.println("Thread1: " + printPixelARGB( img.getRGB(i,j) ) );   // print.
                                count++;
                                if( count == 100 ){
                                    thread2.start();    // start threads.
                                    thread3.start();
                                    thread4.start();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     *  Remove from PQLEX the color pixels and print them on screen one after the other, up
     * until a total of WxH pixels are printed.
     */
    static class secondThread implements Runnable{
        private int w;
        private int h;
        /**
         * Constructor.
         */
        public secondThread(int width, int height){
            w = width;
            h = height;
        }
        /**
         * Thread start method.
         */
        @Override
        public void run() {
            synchronized (PQLEX){   // synchronization
                for( int i = 0; i < w; i++){
                    for( int j = 0; j < h; j++){
                        int value = PQLEX.poll();   // poll and print.
                        System.out.println("Thread2: " + printPixelARGB(value));
                    }
                }
            }
        }
    }
    /**
     * Do the same as Thread2 but it will use PQEUC.
     */
    static class thirdThread implements Runnable{
        private int w;
        private int h;
        /**
         * Constructor.
         */
        public thirdThread(int width, int height){
            w = width;
            h = height;
        }
        /**
         * Thread start method.
         */
        @Override
        public void run() {
            synchronized (PQEUC){   // synchronization
                for( int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        int value = PQEUC.poll();   // poll and print.
                        System.out.println("Thread3: " + printPixelARGB(value));
                    }
                }
            }
        }
    }
    /**
     * Do the same as Thread2 but it will use PQBMX.
     */
    static class fourthThread implements Runnable{
        private int w;
        private int h;
        /**
         * Constructor.
         */
        public fourthThread(int width, int height){
            w = width;
            h = height;
        }
        /**
         * Thread start method.
         */
        @Override
        public void run() {
            synchronized (PQBMX){   // synchronization
                for( int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        int value = PQBMX.poll();   // poll and print.
                        System.out.println("Thread4: " + printPixelARGB(value) );
                    }
                }
            }
        }
    }
    /**
     * Print the given pixel.
     */
    public static String printPixelARGB(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new String(  "[" + red + ", " + green + ", " + blue + "]");
    }
}
