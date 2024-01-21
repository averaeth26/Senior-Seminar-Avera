public class Course {
    String courseName;
    int courseID;
    String instructorName;
    int numSessions;
    int maxCapacity = 16;

    public Course(String courseInfo) {
        String[] separatedData = courseInfo.split(",");
        courseName = separatedData[0];
        courseID = Integer.parseInt(separatedData[1]);
        instructorName = separatedData[2];
    }

    public String toString() {
        return courseName + " (ID: " + courseID + ") is taught by " + instructorName;
    }

    public int getCourseID() {
        return courseID;
    }


    

}
