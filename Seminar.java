import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Seminar {
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
        courseData.remove(0);
    }

    public ArrayList<Student> populateStudents() {
        ArrayList<Student> currentStudents = new ArrayList<Student>();

        for (int i = 0; i < personData.size(); i++) {
            currentStudents.add(new Student(personData.get(i)));
        }
        return currentStudents;
    }

    public ArrayList<Course> populateCourses() {
        ArrayList<Course> currentCourses = new ArrayList<Course>();

        for (int i = 0; i < courseData.size(); i++) {
            currentCourses.add(new Course(courseData.get(i)));
        }
        return currentCourses;
    }

    public int countCourse(ArrayList<Student> students, int testID) {
        int count = 0;
        for (Student student : students) {
            for (int choiceID : student.getChoices()) {
                if (choiceID == testID) {
                    count ++;
                    break;
                }
            }
        }
        return count;
    }

    /* Generates a list of course sections based on interest for each course
     * Returns an ArrayList of courses representing the course roster 
     * This roster stores the number of times a section of each course will be held
     */
    public ArrayList<Course> generateCourseRoster(ArrayList<Course> courses, ArrayList<Student> students) {
        ArrayList<Integer> interestLevels = new ArrayList<Integer>();
        ArrayList<Course> courseSlots = new ArrayList<Course>();
        for (int courseNum = 0; courseNum < courses.size(); courseNum++) {
            System.out.println(courses.get(courseNum));
            interestLevels.add(countCourse(students, courses.get(courseNum).getCourseID()));
        }
        System.out.println(interestLevels);
        for (int i = 0; i < courses.size(); i++) {
            for (int j = Math.min(interestLevels.get(i), courses.get(i).getMaxCapacity()*2); j > 0; j-= courses.get(i).getMaxCapacity()) { // Adds either 1 or 2 sections of each course to the roster depending on the interest level
                courseSlots.add(courses.get(i));
                courses.get(i).addNumSessions();
            }
        }
        return courseSlots;
    }

    public boolean arrContains(int[] arr, int testVal) {
        for (int value : arr) {
            if (value == testVal) {
                return true;
            }
        }
        return false;
    }

    public int[] calculateCourseConflicts(Course course, ArrayList<Course> courses, ArrayList<Student> students) {
        int[] numConflicts = new int[courses.size()];
        int courseIndex = 0;
        for (Course course2 : courses) {
            if (course.getInstructorName().equals(course2.getInstructorName())) {
                numConflicts[courseIndex] = students.size(); // If two courses are taught by the same person, they aren't compatible
                courseIndex ++;
                continue;

            }
            for (Student student : students) {
                if (arrContains(student.getChoices(), course.getCourseID()) && arrContains(student.getChoices(), course2.getCourseID())) {
                    numConflicts[courseIndex] ++;
                }
            }
            courseIndex ++;
        }
        return numConflicts;
    }
}
