import java.util.*;

public class Word_Map implements Map, Iterable
{
    final static int INITCAP = 10;  //initial capacity
    int CURRCAP = INITCAP;   //current capacity
    final static float LOADFACT = 0.75f;
    private Node table[];
    private int size;
    private Node head;

    public Word_Map() {
        this.table = new Node[INITCAP];
        size = 0;
        head = new Node();
    }

    @Override
    public Iterator iterator() {
        Iterator iter = new Iterator() {    // create iterator and return.
            int curr = 0;
            @Override
            public boolean hasNext() {
                if( curr < size )   // when there are element, return true, else false.
                    return true;
                return false;
            }
            @Override
            public Object next() {
                Node temp = head.next;
                for( int i = 0; i < curr; i++) {
                    temp = temp.next;       // scroll using linked structure.
                }
                curr++;
                return temp;
            }
        };
        return iter;
    }

    static class Node {
        private String key;
        public File_Map value;
        private Node next;
        private Node(){
            this.next = null;
        }
        public Node(Object key, Object value){  // assignment elements.
            this.key = (String) key;
            this.value = (File_Map) value;
            this.next = null;
        }
        @Override
        public String toString() {      // toString method.
            System.out.println("Word Map: \nKey: " + key);
            System.out.println("Value is a File Map: ");
            return value.toString();
        }
    }

    private int find(Object key){       // find index.
        int index = key.hashCode() % table.length;  // using hashCode.
        if( index < 0 ){
            index += table.length;
        }
        while( (table[index] != null) && ( !key.equals(table[index].key) ) ){
            index++;
            if( index >= table.length )
                index = 0;
        }
        return index;
    }

    @Override
    public int size() { // return size.
        return size;
    }

    @Override
    public boolean isEmpty() {  // fullness control
        if( size == 0 )
            return true;
        return false;
    }

    @Override
    public boolean containsKey(Object key) {    // control key contains.
        if( get(key) == null )
            return false;
        return true;
    }

    @Override
    public boolean containsValue(Object value) {    // control value contains.
        Node temp = this.head;
        while(true){
            if( temp.next == null )
                return false;
            if( temp.next.value == value )
                return true;
            temp = temp.next;
        }
    }

    @Override
    public Object get(Object key) { // return value of the key.
        int index = find(key);
        if( table[index] != null )
            return table[index].value;
        else
            return null;
    }

    @Override
    public Object put(Object key, Object value) {
        int index = find(key);
        if( table[index] == null ){
            table[index] = new Node(key, value);        // adding new element.
            size++;
            double loadFactor = (double) size / table.length;
            if( loadFactor > LOADFACT )
                rehash();
            Node temp = this.head;      // linked structure.
            while(true){
                if( temp.next == null ){
                    temp.next = new Node(key, value);
                    return table[index];
                }
                temp = temp.next;
            }
        }
        return table[index];
    }
    private void rehash(){ // rehash method, it provides taking memory.
        Node [] oldTable = this.table;
        table = new Node[2 * oldTable.length + 1];
        size = 0;
        this.head = new Node();
        for( int i = 0; i < oldTable.length; i++ ){
            if( oldTable[i] != null ){
                put( oldTable[i].key, oldTable[i].value );
            }
        }
    }

    @Override
    /*You do not need to implement remove function
    * */
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) { // put all element given map.
         Set keys =  m.keySet();
         ArrayList values = (ArrayList) m.values();
         Iterator iter = keys.iterator();
         Iterator iter2 = values.iterator();
         while(iter.hasNext()){
             put(iter.next(), iter2.next());
         }
    }

    @Override
    public void clear() {   // clear method.
        this.table = null;
        head = new Node();
        size = 0;
    }

    @Override
    public Set keySet() {       // return a set that contains keys.
        Set keys = new HashSet();
        Node temp = this.head;
        while(true){
            if( temp.next == null ){
                return keys;
            }
            else{
                keys.add(temp.next);
            }
            temp = temp.next;
        }
    }

    @Override
    public Collection values() {
        Collection values = new ArrayList();
        Node temp = this.head;      // return a collection using linked structure.
        while(true){
            if( temp.next == null ){
                return values;
            }
            else{
                values.add(temp.next);
            }
            temp = temp.next;
        }
    }

    @Override
    /*You do not need to implement entrySet function
     * */
    public Set<Entry> entrySet() {
        return null;
    }
}
