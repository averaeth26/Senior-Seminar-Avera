import java.util.ArrayList;

/**
Course.java
@author Ethan Avera
@since 1/16/24
This class handles the functionality of creating and manipulating Courses to be used in the program.
*/
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

    // Course Constructor
    public Course(String courseInfo) {
        String[] separatedData = courseInfo.split(",");
        courseName = separatedData[0];
        courseID = Integer.parseInt(separatedData[1]);
        instructorName = separatedData[2];
    }

    /*
    toString Method: Formats the Course as a string to prepare it for printing
    Returns the formatted String
    */
    public String toString() {
        String build = courseName + " (ID: " + courseID + ") is taught by " + instructorName + " in Room " + roomNumber + " and is attended by:\n";
        for (String studentName : currentlyEnrolled) {
            build += " - " + studentName + "\n";
        }
        return build;
    }

    
    /*
    Getter Method:
    This method returns the course's id number
    */
    public int getCourseID() {
        return courseID;
    }

    /*
    Setter Method:
    This method adds 1 session to the course when called
    */
    public void addSession() {
        numSessions++;
    }

    /*
    Getter Method:
    This method returns the number of sessions a course is running (1 or 2)
    */
    public int getNumSessions() {
        return numSessions;
    }


    /*
    Getter Method:
    This method returns the max capacity of a course (always 16)
    */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /*
    Getter Method:
    This method returns the name of a course's instructor
    */
    public String getInstructorName() {
        return instructorName;
    }

    /*
    Getter Method:
    This method returns the room number within which a course takes place
    */
    public int getRoomNumber() {
        return roomNumber;
    }

    /*
    Setter Method:
    This method sets the interest level of a course equal to a given value
    The interest level is the number of students who picked it as any of their choices
    This method has no return type
    */
    public void setInterestLevel(int level) {
        interestLevel = level;
    }

    /*
    Getter Method:
    This method returns the interest level of a course
    */
    public int getInterestLevel() {
        return interestLevel;
    }

    /*
    Setter Method:
    This method adds 1 attendee to a course when called
    This method has no return type
    */
    public void addAttendee(String attendeeName) {
        currentlyEnrolled.add(attendeeName);
        currentAttendance++;
    }

    /*
    Getter Method:
    This method returns the current level of attendance of a course (0 through 16)
    */
    public int getAttendance() {
        return currentAttendance; 
    }

    /*
    Setter Method:
    This method resets the enrollment and attendance of a class
    This method has no return type
    */
    public void resetAttendance() {
        currentAttendance = 0;
        currentlyEnrolled.clear();
    }

    /*
    Setter Method:
    This method sets the enrollment and attendance of a class to specified values
    This method has no return type
    */
    public void setAttendance(int value, ArrayList<String> enrollment) {
        currentAttendance = value;
        currentlyEnrolled = enrollment;
    }

    /*
    Getter Method:
    This method returns the list of students currently enrolled in a course
    */
    public ArrayList<String> getEnrolled() {
        return currentlyEnrolled;
    }

    /*
    Getter Method:
    This method returns name of a course
    */
    public String getCourseName() {
        return courseName;
    }

    /*
    Setter Method:
    This method sets the room number of a course to a specified value.
    This method has no return type
    */
    public void setRoomNumber(int number) {
        roomNumber = number;
    }
}
