import java.util.Comparator;
/**
 * Comparator class for PQLEX.
 */
public class LexComparator implements Comparator {
    /**
     * Overridden compare method.
     */
    @Override
    public int compare(Object o1, Object o2) {
        int red1 = ((int) o1 >> 16) & 0xff;     //  represent red green blue color.
        int green1 = ((int) o1 >> 8) & 0xff;
        int blue1 = ((int) o1) & 0xff;
        int red2 = ((int) o2 >> 16) & 0xff;
        int green2 = ((int) o2 >> 8) & 0xff;
        int blue2 = ((int) o2) & 0xff;
        if( red1 == red2 ){         // comparison
            if(green1 == green2){
                if( blue1 < blue2){
                    return 1;
                }
                else if( blue1 == blue2  ){
                    return 0;
                }
                else{
                    return -1;
                }
            }
            else if( green1 < green2 ){
                return 1;
            }
            else{
                return -1;
            }
        }
        else if( red1 < red2 ){
            return 1;
        }
        else{
            return -1;
        }
    }
}
