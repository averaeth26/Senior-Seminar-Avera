public class Student {
    String name;
    String email;
    String submissionTime;
    int[] choices = new int[5];
    Course[] courses = new Course[5];
    public Student(String studentInfo) {
        String[] separatedData = studentInfo.split(",");
        name = separatedData[3];
        email = separatedData[1];
        submissionTime = separatedData[0];
        for (int i = 0; i < 5; i ++) {
            choices[i] = Integer.parseInt(separatedData[i+4]);
        }
    }

    public String toString() {
        String build = name + " can be reached at " + email + " and is taking: ";
        for (Course course : courses) {
            // To be implemented...
        }
        return build;
    }

    public int[] getChoices() {
        return choices;
    }
}
