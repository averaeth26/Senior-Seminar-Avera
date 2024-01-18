public class Student {
    String name;
    String email;
    String submissionTime;
    int[] choices = new int[5];
    Course[] courses = new Course[5];
    public Student(String data) {
        String[] separatedData = data.split(",");
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
            
        }
        return build;
    }
}
