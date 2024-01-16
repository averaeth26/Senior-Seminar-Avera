import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Tester {
  public static void main(String[] args) {
    ArrayList<String> personData = new ArrayList<String>();
    ArrayList<String> lateData = new ArrayList<String>();
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
    for (String name : personData) {
      System.out.println(name);
    }
  }
}