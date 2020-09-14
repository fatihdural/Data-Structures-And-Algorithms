/**
 * Experiment class, islem yapilacak experimentlar bu sinif ile olusturulur.
 */
public class Experiment{
    public String setup; //explains the experimental setup
    public int day; //represents the day of start
    public String time; // Time time; // represents the time of start
    public float accuracy; //represents the output (not a valid value if the experiment is not completed)
    public boolean completed; // indicates whether it is completed or not

    /**
     * no parameter constructor.
     */
    public Experiment(){
        this.setup = null;
        this.day = 0;
        this.time = null;
        this.completed = true;
        this.accuracy = (float) 0;
    }

    /**
     * Constructor
     */
    public Experiment(String setup, int day, String time, float accuracy, boolean completed){
        this.setup = setup;
        this.day = day;
        this.time = time;
        this.completed = completed;
        this.accuracy = accuracy;
    }

    /**
     * objeyi kopyalan constructor.
     */
    public Experiment(Experiment e){
        this.setup = e.setup;
        this.day = e.day;
        this.time = e.time;
        this.completed = e.completed;
        this.accuracy = e.accuracy;
    }

    @Override
    public String toString() {          // experiment toString methodu.
        return "Experiment{" +
                "setup='" + setup + '\'' +
                ", day=" + day +
                ", time='" + time + '\'' +
                ", accuracy=" + accuracy +
                ", completed=" + completed +
                '}';
    }
}