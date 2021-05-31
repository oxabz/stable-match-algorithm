import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        StableMariage m = new StableMariage();
        try {
            if(args.length <2) {
                throw new InvalidParametersExeption("Not enough parameters");
            }
            m.readParseCSV(args[1]);
            HashMap<School, Set<Student>> result;
            if(args[0].equals("student")) {
                result = m.studentBidding();
            } else if(args[0].equals("school")){
                result = m.schoolBidding();
            }
            else {
                throw new InvalidParametersExeption("Unknown bidding parameter pick one between student and school");
            }
            for (var entry : result.entrySet()) {
                System.out.print(entry.getKey().getName() + " : [");
                for (Iterator<Student> iterator = entry.getValue().iterator(); iterator.hasNext(); ) {
                    Student it = iterator.next();
                    System.out.print(it.getName());
                    if (iterator.hasNext()) System.out.print(" ,");
                }
                System.out.println("]");
            }

        } catch (IOException e) {
            System.err.println("Error reading the file");
            System.err.println("Make sure the file is of the right format and that the path to file is right");
        } catch(CSVInvalideException e){ System.err.println(e.getMessage());}
        catch (InvalidParametersExeption invalidParametersExeption) {
            System.err.println(invalidParametersExeption.getMessage());
            System.err.println("Usage : ./stable-match-algorythm.jar [student/school] <path-to-csv-file>");
        }
    }


}