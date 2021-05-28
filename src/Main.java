import au.com.bytecode.opencsv.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        StableMariage m = new StableMariage();
        try {
            m.readParseCSV(args[0]);
            for (var entry : m.studentBidding().entrySet()) {
                System.out.print(entry.getKey().getName()+" : [");
                for (Iterator<Student> iterator = entry.getValue().iterator(); iterator.hasNext(); ) {
                    Student it = iterator.next();
                    System.out.print(it.getName());
                    if (iterator.hasNext()) System.out.print(" ,");
                }
                System.out.println("]");
            }
            for (var entry : m.schoolBidding().entrySet()) {
                System.out.print(entry.getKey().getName()+" : [");
                for (Iterator<Student> iterator = entry.getValue().iterator(); iterator.hasNext(); ) {
                    Student it = iterator.next();
                    System.out.print(it.getName());
                    if(iterator.hasNext())System.out.print(" ,");
                }
                System.out.println("]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
