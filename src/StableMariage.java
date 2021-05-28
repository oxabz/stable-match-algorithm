import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StableMariage {

    School[] schools;
    Student[] students;

    public void readParseCSV(String path) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(path));
        List<String[]> r = reader.readAll();
        schools = new School[r.size()-1];
        for (int i = 1; i < r.size(); i++) {
            schools[i-1] = new School(r.get(i)[0], Integer.parseInt(r.get(i)[1]), r.get(0).length-2);
        }
        students = new Student[r.get(0).length-2];
        for (int i = 2; i < r.get(0).length; i++) {
            students[i-2] = new Student(r.get(0)[i], r.size()-1);
        }

        for (int i = 1; i < r.size(); i++) {
            for (int j = 2; j < r.get(0).length; j++) {
                schools[i-1].addPref(students[j-2], Integer.parseInt(r.get(i)[j].split(",")[0]));
                students[j-2].addPref(schools[i-1], Integer.parseInt(r.get(i)[j].split(",")[1]));
            }
        }
    }



    HashMap<School, Set<Student>> studentBidding(){
        int[] choiceIdx = new int[students.length];
        for (int i = 0; i < choiceIdx.length; i++) {
            choiceIdx[i] = 1;
        }
        HashMap<School, Set<Student>> schoolChoices = new HashMap<>();
        for (School school :
                schools) {
            schoolChoices.put(school, new LinkedHashSet<>());
        }

        boolean change;
        do {
            change = false;
            for (int i = 0; i < students.length; i++) {
                change = schoolChoices.get(students[i].getChoice(choiceIdx[i])).add(students[i]) || change;
            }
            for (var entry :
                    schoolChoices.entrySet()) {
                School school = entry.getKey();
                int capacity = school.getCap();
                var candidates = entry.getValue();
                while (candidates.size()>capacity){ //TODO: Optimiser la boucle;
                    var removedStudent = candidates.stream()
                            .max(Comparator.comparingInt(school::getPref)).get();

                    candidates.remove(removedStudent);
                    choiceIdx[getStudentIdx(removedStudent)]++;
                }
            }
        }while (change);
        return schoolChoices;
    }

    int getStudentIdx(Student student){
        int i = 0;
        while (students[i] != student){
            i++;
        }
        return i;
    }

    int getSchoolIdx(School school){
        int i = 0;
        while (schools[i] != school){
            i++;
        }
        return i;
    }

    HashMap<School, Set<Student>> schoolBidding(){
        boolean potentialChoice[][] = new boolean[schools.length][students.length];
        for (int i = 0; i < potentialChoice.length; i++) {
            for (int j = 0; j < potentialChoice[i].length; j++) {
                potentialChoice[i][j] = true;
            }
        }
        HashMap<Student, Set<School>> studentOptions = new HashMap<>();
        for (Student student :
                students) {
            studentOptions.put(student, new LinkedHashSet<>());
        }
        boolean change;
        do {
            change = false;
            for (int i = 0, schoolsLength = schools.length; i < schoolsLength; i++) {
                School school = schools[i];
                for (Student selectedStudent:
                        school.getChoice(potentialChoice[i])) {
                    change = studentOptions.get(selectedStudent).add(school) || change;
                }

            }
            int studentIdx = 0;
            for (Iterator<Map.Entry<Student, Set<School>>> iterator = studentOptions.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Student, Set<School>> entry = iterator.next();
                Student student = entry.getKey();
                var potentialSchool = entry.getValue();
                School selectedSchool = potentialSchool.stream().min(Comparator.comparingInt(student::getPref)).orElse(null);
                potentialSchool.remove(selectedSchool);
                for (School school :
                        potentialSchool) {
                    potentialChoice[getSchoolIdx(school)][school.getPref(student)-1] = false;
                }
                entry.setValue(new LinkedHashSet<>());
                if (selectedSchool!=null)
                    entry.getValue().add(selectedSchool);
                studentIdx++;
            }
        }while (change);
        var r = new HashMap<School, Set<Student>>();
        for (School s :
                schools) {
            r.put(s, new LinkedHashSet<>());
        }
        for (var entry :
                studentOptions.entrySet()) {
            r.putIfAbsent(entry.getValue().iterator().next(), new HashSet<>());
            r.get(entry.getValue().iterator().next()).add(entry.getKey());
        }
        return r;
    }


}
