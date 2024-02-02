import java.util.ArrayList;

public class Course {
    String courseName;
    int courseID;
    String instructorName;
    int numSessions;
    int interestLevel;
    int maxCapacity = 16;
    int currentAttendance = 0;
    Student[] currentlyEnrolled = new Student[16];

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

    public void addNumSessions() {
        numSessions++;
    }

    public int getNumSessions() {
        return numSessions;
    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInterestLevel(int level) {
        interestLevel = level;
    }

    public int getInterestLevel() {
        return interestLevel;
    }

    public void addAttendee() {
        currentAttendance++;
    }

    public int getAttendance() {
        return currentAttendance;
    }

    public String getCourseName() {
        return courseName;
    }
}
