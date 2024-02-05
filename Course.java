import java.util.ArrayList;

public class Course {
    String courseName;
    int courseID;
    String instructorName;
    int numSessions;
    int roomNumber;
    int interestLevel;
    int maxCapacity = 16;
    int currentAttendance = 0;
    ArrayList<String> currentlyEnrolled = new ArrayList<String>();

    public Course(String courseInfo) {
        String[] separatedData = courseInfo.split(",");
        courseName = separatedData[0];
        courseID = Integer.parseInt(separatedData[1]);
        instructorName = separatedData[2];
    }

    public String toString() {
        String build = courseName + " (ID: " + courseID + ") is taught by " + instructorName + " in Room " + roomNumber + " and is attended by:\n";
        for (String studentName : currentlyEnrolled) {
            build += " - " + studentName + "\n";
        }
        return build;
    }

    public int getCourseID() {
        return courseID;
    }

    public void addSession() {
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

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setInterestLevel(int level) {
        interestLevel = level;
    }

    public int getInterestLevel() {
        return interestLevel;
    }

    public void addAttendee(String attendeeName) {
        currentlyEnrolled.add(attendeeName);
        currentAttendance++;
    }

    public int getAttendance() {
        return currentAttendance; 
    }

    public void resetAttendance() {
        currentAttendance = 0;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setRoomNumber(int number) {
        roomNumber = number;
    }
}
