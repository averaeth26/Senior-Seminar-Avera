import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Tester {
    public static void main(String[] args) {
        Data d1 = new Data();
        d1.importData();
        ArrayList<Student> students = new ArrayList<Student>();
        students = d1.generateStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}