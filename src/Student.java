import java.util.HashMap;
import java.util.List;

public class Student {
    private String name;
    private School[] pref;

    public Student(String name, int schoolCount) {
        this.name = name;
        pref = new School[schoolCount];
    }

    void addPref(School school, int rank){
        pref[rank-1] = school;
    }

    School getChoice(int rank){
        return pref[rank-1];
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