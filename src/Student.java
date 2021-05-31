import java.util.HashMap;
import java.util.List;

public class Student {
    private String name;
    private School[] pref;

    public Student(String name, int schoolCount) {
        this.name = name;
        pref = new School[schoolCount];
    }


    void addPref(School school, int rank) throws CSVInvalideException {
        if(pref[rank-1]!=null)
            throw new CSVInvalideException("la liste des preferences de "+ this.name +" est invalide");
        pref[rank-1] = school;
    }

    School getChoice(int rank){
        return (rank<pref.length?pref[rank-1]:null);
    }

    public String getName() {
        return name;
    }

    int getPref(School school){
        int i = 0;
        while (pref[i] != school){
            i++;
        }
        return i+1;
    }
}