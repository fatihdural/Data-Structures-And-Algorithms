import java.util.*;
public class File_Map implements Map
{
    private ArrayList<String> fnames = new ArrayList<String>();
    private ArrayList<List<Integer>> occurances = new ArrayList< List<Integer> >();
    private int size = 0;

    public ArrayList<String> getFnames(){
        return fnames;
    }   // get functions
    public ArrayList<List<Integer>> getOccurances(){
        return occurances;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if( size == 0 )
            return true;
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        for( int i = 0; i < fnames.size(); i++){
            if( fnames.get(i).equals(key) )     // if file contains, return true.
                return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return occurances.contains(value);      // if integer value contains in list, return
    }

    @Override
    public Object get(Object key) {
        if( fnames.contains(key) ){
            int indis = 0;
            for( int i = 0; i < fnames.size(); i++){    // get the locations List<Integer> occurances.
                if( fnames.get(i).equals(key) ){
                    indis = i;
                    break;
                }
            }
            return occurances.get(indis);
        }
        else
            return null;
    }

    @Override
    /*Each put operation will extend the occurance list*/
    public Object put(Object key, Object value) {
        fnames.add((String)key);            // use array list add method.
        occurances.add((List<Integer>)value);
        return value;
    }

    @Override
    public Object remove(Object key) {
        if( fnames.contains(key) ){
            int indis = 0;
            for( int i = 0; i < fnames.size(); i++){    // remove the given element
                if( fnames.get(i) == key ){             // using array list remove method.
                    fnames.remove(i);
                    indis = i;
                    break;
                }
            }
            return occurances.remove(indis);
        }
        else
            return null;
    }

    @Override
    public void putAll(Map m) {
        Set keys =  m.keySet();
        ArrayList values = (ArrayList) m.values();
        Iterator iter = keys.iterator();
        Iterator iter2 = values.iterator();
        while(iter.hasNext()){                  // put All element using iterator
            put(iter.next(), iter2.next());
        }
    }

    @Override
    public void clear() {
        fnames = null;              // clear the file map.
        occurances = null;
        size = 0;
    }

    @Override
    public Set keySet() {       // return a set contains the elements.
        Set keys = new HashSet();
        for( int i = 0; i < fnames.size(); i++){
            keys.add(fnames.get(i));
        }
        return keys;
    }

    @Override
    public Collection values() {        // return a collection contains the elements.
        ArrayList values = occurances;
        return values;
    }

    @Override
    public Set<Entry> entrySet() {      // return entry set.
        Set entries = new HashSet();
        for( int i = 0; i < fnames.size(); i++){
            Entry entry = new AbstractMap.SimpleEntry(fnames.get(i), occurances.get(i));
            entries.add(entry);
        }
        return entries;
    }
    @Override
    public String toString() {      // toString method.
        for( int i = 0; i < fnames.size(); i++){
            if( !occurances.get(i).equals( new ArrayList<Integer>() ) ){    // not print empt cell.
                System.out.println( "Textfile: " + fnames.get(i) + ", Locations:" + occurances.get(i).toString());
            }
        }
        return "";
    }
}
