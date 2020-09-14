import java.util.Comparator;
/**
 * Comparator class for PQBMX.
 */
public class BmxComparator implements Comparator {
    /**
     * Overridden compare method.
     */
    @Override
    public int compare(Object o1, Object o2) {      //  represent red green blue color.
        int red1 = ((int) o1 >> 16) & 0xff;
        int green1 = ((int) o1 >> 8) & 0xff;
        int blue1 = ((int) o1) & 0xff;
        int red2 = ((int) o2 >> 16) & 0xff;
        int green2 = ((int) o2 >> 8) & 0xff;
        int blue2 = ((int) o2) & 0xff;

        MyArrayList result1 = new MyArrayList();
        MyArrayList result2 = new MyArrayList();

        for( int i = 0; i < 8; i++){
            result1.add(getBit(red1, i) << i);
            result1.add((getBit(green1, i) << i));
            result1.add((getBit(blue1, i) << i));

            result2.add((getBit(red2, i) << i));
            result2.add((getBit(green2, i) << i));
            result2.add((getBit(blue2, i) << i));
        }

        int last1 = 0;
        int last2 = 0;

        for(int i = 0; i < 24; i++){
            last1 += result1.get(i) * Math.pow(2,i);    // convert binary to decimal.
            last2 += result2.get(i) * Math.pow(2,i);
        }

        int last = last1 - last2;

        if( last > 0 ){ // comparison.
            return 1;
        }
        else if( last == 0 ){
            return 0;
        }
        else{
            return -1;
        }
    }
    private int getBit(int n, int k) {
        return (n >> k) & 1;
    }
}
