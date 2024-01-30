import java.util.ArrayList;

public class Student {
    String name;
    String email;
    String submissionTime;
    int[] choiceIDs = new int[5];
    ArrayList<Course> choices = new ArrayList<Course>();
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

    public int[] generateChoiceConflicts(Course[][] courseCalendar, ArrayList<Course> courses) {
        int[] choiceConflicts = new int[5];
        for (int i = 0; i < choices.size(); i++) {
            for (int j = 0; j < courseCalendar.length; j++) {
                if (courseCalendar[i][j] == choices.get(j)) {
                    choiceConflicts[i] ++;
                }
            }
        }
        return choiceConflicts;
    }

    public int[] getChoiceIDs() {
        return choiceIDs;
    }
}
