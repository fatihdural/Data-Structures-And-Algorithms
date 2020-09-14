import java.util.Comparator;
import java.lang.Math;
/**
 * Comparator class for PQEUC.
 */
public class EucComparator implements Comparator {
    /**
     * Overridden compare method.
     */
    @Override
    public int compare(Object o1, Object o2) {  //  represent red green blue color.
        int red1 = ((int) o1 >> 16) & 0xff;
        int green1 = ((int) o1 >> 8) & 0xff;
        int blue1 = ((int) o1) & 0xff;
        int red2 = ((int) o2 >> 16) & 0xff;
        int green2 = ((int) o2 >> 8) & 0xff;
        int blue2 = ((int) o2) & 0xff;

        double result1 = red1 * red1 + green1 * green1 + blue1 * blue1;
        double result2 = red2 * red2 + green2 * green2 + blue2 * blue2;
        double result =  Math.sqrt(result1) - Math.sqrt(result2);

        if( result < 0 ){       // comparison.
            return 1;
        }
        else if( result == 0 ){
            return 0;
        }
        else{
            return -1;
        }
    }
}
