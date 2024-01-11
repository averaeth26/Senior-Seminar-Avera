import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Tester {
  public static void main(String[] args) {
    ArrayList<String> names = new ArrayList<String>();
    try {
      File myObj = new File("SeniorSeminar_PersonData.csv");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        names.add(data.split(",")[0]);
        System.out.println(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}