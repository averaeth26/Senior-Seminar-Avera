import java.util.ArrayList;

public class Student {
    String name;
    String email;
    String submissionTime;
    int[] choiceIDs = new int[5];
    Course[] choices = new Course[5];
    public Student(String studentInfo) {
        String[] separatedData = studentInfo.split(",");
        name = separatedData[3];
        email = separatedData[1];
        submissionTime = separatedData[0];
        for (int i = 0; i < 5; i ++) {
            choiceIDs[i] = Integer.parseInt(separatedData[i+4]);
        }
    }

    public String toString() {
        String build = name + " can be reached at " + email + " and is taking: ";
        for (Course course : choices) {
            // To be implemented...
        }
        return build;
    }

    public int arrCountContains(Course[] arr, int[] arr2) {
        int counter = 0;
        for (Course element : arr) {
            for (int element2 : arr2)
                if (element.getCourseID() == element2) {
                    counter ++;
                }
        }
        return counter;
    }

    public boolean arrContains(Course[] arr, int testVal) {
        for (Course element : arr) {
            if (element.getCourseID() == testVal) {
                return true;
            }
        }
        return false;
    }
// Notes for self:
// Generate "Hopeful" roster for each student, then, swap out any students over the maximum,
// prioritizing swapping those who had a "conflict" that block as they will still get one of their choices
    public void calculateGuaranteedSlots(Course[][] courseCalendar, ArrayList<Course> courses) {
    for (int i = 0; i < courseCalendar.length; i++) {
            if (arrCountContains(courseCalendar[i], choiceIDs) > 1) {
                continue;
            }
            for (int id : choiceIDs) {
                if (arrContains(courseCalendar[i], i)) {
                    choices[i] = courses.get(id-1);
                }
            }
        }
    }

    public int[] getChoiceIDs() {
        return choiceIDs;
    }

    public Course[] getChoices() {
        return choices;
    }
}
