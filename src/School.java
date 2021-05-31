import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class School {
    private String name;
    private int cap;
    private Student[] pref;

    public School(String name, int cap, int studentCount) {
        this.name = name;
        this.cap = cap;
        pref = new Student[studentCount];
    }

    void addPref(Student student, int rank) throws CSVInvalideException {
        if(pref[rank-1]!= null)
            throw new CSVInvalideException("la liste de préférence de " + this.name + " est invalide");
        pref[rank-1]=student;
    }

    int getPref(Student student){
        int i = 0;
        while (pref[i] != student){
            i++;
        }
        return i+1;
    }

    public int getCap() {
        return cap;
    }

    public String getName() {
        return name;
    }

    public List<Student> getChoice(boolean[] mask){
        var r = new ArrayList<Student>();
        int i = 0;
        while (r.size()<cap && i < mask.length){
            if (mask[i]){
                r.add(pref[i]);
            }
            i++;
        }
        return r;
    }
}
