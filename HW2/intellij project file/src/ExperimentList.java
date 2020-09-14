import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ExperimentList class, Iterable Interface ini implement eder.
 */

public class ExperimentList implements Iterable{
    /**
     * Linkedlist yapısı gereği içerde bir node tutulur.
     */
    private static class Node{          // her noda icinde bir experiment datasi
        private Experiment data;            // next node
        private Node next = null;            // nextDay node
        private Node nextDay = null;
        private Node(Experiment e){
            data = e;
        }   // experiment objesini dataya kopyalayan constructor.

    }
    private Node head = null;      // ExperimentList icerde node tipinde head tutar.

    @Override
    public String toString(){       // toString methodu
        return head.data.toString();
    }
    /**
     * MyIterator class, Iterator Interface ini implement eder.
     */
    private class MyIterator implements Iterator{   // kendi iterator classim
        private ExperimentList exp;                 // Iterator class ini implement eder.

        public MyIterator(ExperimentList exp){      // experiment list constructor.
            this.exp = exp;
        }
        @Override
        public String toString() {      // toString methodu.
            return exp.toString();
        }

        @Override
        public boolean hasNext() {     // next eleman kontrolu
            if( exp.head == null ){
                return false;
            }
            return true;
        }

        @Override
        public Object next() {      // ilerleme.
            ExperimentList temp = exp;
            temp.head = temp.head.next;
            return temp;
        }

        @Override
        public void remove() {      // son elemani silen fonksiyon
            Node current = exp.head;
            if( current.next == null ){
                exp.head = null;
            }

            while( current.next.next != null ){
                current = current.next;
            }
            current.next = null;            // current.next son elemani gosterir.

        }
    }


    @Override
    public Iterator iterator(){ // MyIterator tipinde bir obje dondurur.
        return new MyIterator(this);
    }

    /**
     * Tüm listeyi ekrana basar.
     */
    public void listAll()
    {
        System.out.println("List experiment view:");
        Node last = head;
        while( last != null) {          // tum experimentlari ekrana bas
            System.out.println(last.data.toString());
            last = last.next;
        }
        System.out.println("List day view:");      // tum day lari ekrana bas.
        last = head;
        while( last != null) {
            System.out.println(last.data.toString());
            last = last.nextDay;
        }
    }

    /**
     * insert experiment to the end of the day
     */
    public void addExp(Experiment e){ // gunun sonu eleman ekle
        Node temp = new Node(e);    // verilen experiment tipinde node olustur
        temp.next = null;
        int flag = 0;
        if( head == null){      // hic eleman yoksa direk ekler
            head = temp;
            head.next = null;
            head.nextDay = null;
        }

        else{
            Node last = head;

            while( last.next != null ){     // sona kadar don
                if( last.data.day > e.day ){
                    flag = 0;
                    break;
                }
                if( last.next.data.day > e.day ){   // diger gune gecince gir.
                    temp.next = last.next;      // node araya ekle
                    last.next = temp;
                    flag++;
                    break;
                }
                last = last.next;   // liste icinde ilerle.

            }
            if( flag == 0){
                if( last.data.day <= e.day) {   // ek condiontilar icin ekle
                    last.next = temp;
                }
                else{
                    temp.next = last;
                    head = temp;
                }
            }
        }
        Node tempp = head;
        Node changeable = tempp;
        int past = head.data.day;           // next day bilgisini en son ekliyorum
        while( tempp.next != null ){
            if( tempp.next.data.day != past ){
                changeable.nextDay = tempp.next;
                past = tempp.next.data.day;
                changeable = tempp.next;
            }
            tempp = tempp.next;
        }
    }

    /**
     * insert experiment to the end of the list
     */

    public void addExpList(Experiment e){   // bir method daha yazdim
        Node last = head;               // listenin en sonuna ekleme yapiyor, gun bilgine bakmadan
        Node exp = new Node(e);

        if( head == null){  // hic eleman yoksa direk ekle.
            head = exp;
        }
        else{
            while( last.next != null ){ // son elemana kadar git.
                last = last.next;
            }
            last.next = exp;        // son elemana ekle
        }
    }

    /**
     * get the experiment with the given day and position
     */
    public Experiment getExp(int day, int index){   // istenen gun ve index de ki elemani getir.
        Node temp = head;
        int i = 0;
        Experiment ret = new Experiment();
        while( temp.next != null){
            while( temp.data.day == day ){  // gun bilgisi oldugu surece don
                if( i == index){        // index e gelince elemani dondur
                    ret = temp.data;
                    return ret;
                }
                i++;
                temp = temp.next;
            }
            temp = temp.nextDay;
        }
        return ret;
    }

    /**
     * set the experiment with the given day and position
     */
    public void setExp(int day, int index, Experiment e){   // istenilen gun ve index elemani uzerine yaz
        Node temp = new Node(e);    // experiment node u.
        temp.next = null;

        if( head == null){     // hic eleman yoksa direk ekle
            head = temp;
            head.next = null;
        }

        else{
            Node last = head;
            int flag = 0, i = 0;
            while (last.next != null){
                if( last.next.data.day == day ){    // elemani bul ve uzerine yaz.
                    while( last.next.data.day == day ){
                        if( i + 1 == index){
                            temp.next = last.next.next;
                            last.next = temp;
                            break;
                        }
                        i++;
                        last = last.next;
                    }
                    temp = temp.next;
                }
                last = last.next;
            }
        }

        Node tempp = head;
        Node changeable = tempp;
        int past = head.data.day;
        while( tempp.next != null ){        // nextDay i degistir.
            if( tempp.next.data.day != past ){
                changeable.nextDay = tempp.next;
                past = tempp.next.data.day;
                changeable = tempp.next;
            }
            tempp = tempp.next;
        }
    }

    /**
     * remove the experiment specified as index from given day
     */
    public int removeExp(int day, int index){
        Node last = head;
        int i = 0;
        if( last.data.day == day ){     // ilk eleman serisinde eleman silinecekse gir.
            if( index == 0 ){   // ilk eleman silinmesi istiyorsa
                head = head.next;       // elemani artir
                Node temp = head;
                while( temp.next.data.day == day ){
                    temp = temp.next;
                }
                head.nextDay = temp.next;   // nextDay i duzelt.
            }
            else{       // index 0 degilse gir
                i++;
                while( last.next.data.day == day ){ // indexi bul elemani sil
                    if( i == index ){               // baglantilari duzelt.
                        last.next = last.next.next;
                    }
                    i++;
                    last = last.next;
                }
            }
        }

        else {
            while( last.next != null ){
                if( last.next.data.day == day ){    // day i bul
                    i = 0;
                    int first = last.data.day;
                    while( last.next.data.day == day ){
                        if( i  == index){
                            if( index == 0 ){       // ilk index de next day degiseceginden ozel kosul
                                Node temp = head;
                                if( temp.data.day == first ){   // remove algoritmasi
                                    head.nextDay = last.next.next;
                                }
                                else{
                                    while( temp.next.data.day <  first ){   // 0 inci index silindiginde
                                        temp = temp.next;
                                    }
                                    temp.next.nextDay = last.next;
                                }
                                if( last.next.next != null ){
                                    if( last.next.data.day == last.next.next.data.day){
                                        last.next.next.nextDay = last.next.nextDay;
                                    }
                                }
                            }
                            last.next = last.next.next;
                            return 0;
                        }
                        i++;
                        last = last.next;
                    }
                }
                last = last.next;           // listede ilerle
            }
        }
        return 1;
    }

    /**
     * list all completed experiments in a given day
     */
    public void listExp(int day){       // verilen gunde completed true lari ekrana bas.
        Node last = head;
        while( last != null ){
            if( last.data.day == day ){
                if( last.data.completed == true ){
                    System.out.println(last.data);
                }
            }
            last = last.next;
        }
    }

    /**
     * remove all experiments in a given day
     */
    public void removeDay(int day){ // verilen gundeki tum experimentlar silinir.
        Node last = head;
        if( last.data.day == day ){ // ilk gun serisinden istenirse direk next day e atla.
            head = last.nextDay;
        }
        else{
            while( last.next != null ){     // gunun basini bul
                if( last.next.data.day == day ){    // removeExp ile sil.
                    removeExp(day, 0);
                }
                else
                    last = last.next;       // ilerle.
            }
        }
    }

    /**
     * sorts the experiments in a given day according to the accuracy, the changes will be done on the list
     */
    public void orderDay(int day){
        Node last  = head;          // istenen gunun basini bul
        Node sabit = null;
        ExperimentList temp = new ExperimentList();
        while( last.next != null ){
            if( last.next.data.day == day ){
                sabit = last;
                while( last.next.data.day == day){
                    temp.addExp(last.next.data);
                    last = last.next;
                    if( last.next == null){
                        break;
                    }
                }
                break;
            }
            last = last.next;
        }
        ExperimentList ordered =  temp.orderExperiments();  // istenilen parcayi orderExperiment le sirala.
        sabit.next = ordered.head;      // sonra listeye bagla.
        Node last2 = ordered.head;
        while( last2.next != null ){
            last2 = last2.next;
        }
        last2.next = last.next;
    }

    /**
     * sorts all the experiments in the list according to the accuracy,
     * the original list should not be changed since the day list may be damage
     */
    public ExperimentList orderExperiments(){   // tum experimentlari sirala
        Node last = head;
        Node last2 = head;
        ExperimentList ordered = new ExperimentList();
        Node min = new Node(new Experiment());
        min.data.accuracy = 9999999;
        float min2 = -9999999;
        int count = -1;
        while(last != null){       // genel dongu
            int count_in = 0;
            while(last2 != null){       // her eleman icin dongu
                if( min.data.accuracy > last2.data.accuracy && last2.data.accuracy >= min2){
                    if( last2.data.accuracy != min2 || count_in >= count){
                        min = last2;        // min se ve kullanilmamissa ekle
                        count_in++;
                    }
                }
                last2 = last2.next;
            }
            count = count_in;
            Experiment temp = new Experiment(min.data);
            ordered.addExpList(temp);
            min2 = min.data.accuracy;
            min = new Node(new Experiment());
            min.data.accuracy = 9999999;
            last = last.next;
            last2 = head;
        }
        return ordered;
    }
}

