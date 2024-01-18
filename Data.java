import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Data {
    ArrayList<String> personData = new ArrayList<String>();
    ArrayList<String> lateData = new ArrayList<String>();
    ArrayList<String> courseData = new ArrayList<String>();

    public void importData() {
        try {
            File myObj = new File("SeniorSeminar_PersonData.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.charAt(0) == ',') {
                    lateData.add(data);
                }
                else {
                    personData.add(data);
                }
            }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        personData.remove(0);
        Collections.sort(personData); // From https://www.w3schools.com/java/java_arraylist.asp

        try {
            File myObj = new File("SeniorSeminar_CourseData.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                courseData.add(data);
            }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<Student> generateStudents() {
        ArrayList<Student> currentStudents = new ArrayList<Student>();

        for (int i = 0; i < personData.size(); i++) {
            currentStudents.add(new Student(personData.get(i)));
        }
        return currentStudents;
    }

    public int countCourses() {
        return 0; //Work in progress
    }
}
