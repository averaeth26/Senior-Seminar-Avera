import java.util.ArrayList;

/**
Room.java
@author Ethan Avera
@since 1/16/24
This class handles the functionality of creating and manipulating Students to be used in the program.
*/
public class Student {
    String name;
    String email;
    String submissionTime;
    int[] choiceIDs = new int[5];
    Course[] choices = new Course[5];

    // Student Constructor
    public Student(String studentInfo) {
        String[] separatedData = studentInfo.split(",");
        name = separatedData[3];
        email = separatedData[1];
        submissionTime = separatedData[0];
        for (int i = 0; i < 5; i ++) {
            choiceIDs[i] = Integer.parseInt(separatedData[i+4]);
        }
    }

    /*
    toString Method: Formats the Course as a string to prepare it for printing
    Returns the formatted String
    */
    public String toString() {
        String build = name;
        if (email.length() > 0) {
            build += " can be reached at " + email + " and";
        }
        build += " is taking:\n";
        for (int i = 0; i < choices.length; i++) {
            build += "Timeslot " + (i+1) + ": " + choices[i].getCourseName() + " in Room " + choices[i].getRoomNumber() + "\n";
        }
        return build;
    }

    /*
    This method counts the number of occurrences of the values of one array in another array
    This method then returns that number
    */
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

    /*
    This method checks if a value occurs in an array
    This method then returns the result of that check
    */
    public boolean arrContains(Course[] arr, int testVal) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].getCourseID() == testVal) {
                return true;
            }
        }
        return false;
    }

// Notes for self:
// Generate "Hopeful" roster for each student, then, swap out any students over the maximum,
// prioritizing swapping those who had a "conflict" that block as they will still get one of their choices

    /*
    A helper method for the placeStudents() method in seminar
    This method calculates exacttly 1 slot for a student each time it is run
    It will continue to call itself recursively until it is able to fill that slot
    This makes it easy to generate schedules as calling this method five times for each student will guarantee they each have all 5 of their choices filled out.
    This method was the hardest for me to get working as working with method calls across files can make tracing issues very difficult
    */
    public void calculateSlot(Course[][] courseCalendar, int choiceNum, ArrayList<Course> courses) {
        if (choiceNum > 4) {
            for (int i = 0; i < courseCalendar.length; i++) {
                if (choices[i] != null) {
                    continue;
                }
                for (int j = 0; j < courseCalendar[0].length; j++) {
                    // System.out.println(courseCalendar[i][j].getAttendance());
                    if (!arrContains(choices, courseCalendar[i][j].getCourseID()) && courseCalendar[i][j].getAttendance() < courseCalendar[i][j].getMaxCapacity()) {
                        choices[i] = courseCalendar[i][j];
                        choices[i].addAttendee(name);
                        return;
                    }
                }
            }
        }
        if (choiceNum > 4) {
            return;
        }

        int prevChoiceIndex = -1;
        int prevChoiceConflicts = -1;
        for (int i = 0; i < courseCalendar.length; i++) {
            // System.out.println(courses.get(choiceIDs[choiceNum]-1).getNumSessions());
            if (choiceIDs[choiceNum] > 0 && courses.get(choiceIDs[choiceNum]-1).getNumSessions() == 1) {
                for (int j = 0; j < courseCalendar[0].length; j++) {
                    if (courseCalendar[i][j].getCourseID() == choiceIDs[choiceNum] && choices[i] == null && courseCalendar[i][j].getAttendance() < courseCalendar[i][j].getMaxCapacity()) {
                        choices[i] = courseCalendar[i][j];
                        choices[i].addAttendee(name);
                        return;
                    }
                }
            }
            else {
                if (arrContains(courseCalendar[i], choiceIDs[choiceNum])) {
                    if (prevChoiceIndex < 0) {
                        prevChoiceIndex = i;
                        prevChoiceConflicts = arrCountContains(courseCalendar[i], choiceIDs);
                    }
                    else {
                        if (arrCountContains(courseCalendar[i], choiceIDs) <= prevChoiceConflicts) {
                            for (int j = 0; j < courseCalendar[0].length; j++) {
                                if (courseCalendar[i][j].getCourseID() == choiceIDs[choiceNum] && choices[i] == null && courseCalendar[i][j].getAttendance() < courseCalendar[i][j].getMaxCapacity()) {
                                    choices[i] = courseCalendar[i][j];
                                    choices[i].addAttendee(name);
                                    return;
                                }
                            }
                        }
                        else {
                            for (int j = 0; j < courseCalendar[0].length; j++) {
                                if (courseCalendar[prevChoiceIndex][j].getCourseID() == choiceIDs[choiceNum]  && choices[prevChoiceIndex] == null && courseCalendar[prevChoiceIndex][j].getAttendance() < courseCalendar[prevChoiceIndex][j].getMaxCapacity()) {
                                    choices[prevChoiceIndex] = courseCalendar[prevChoiceIndex][j];
                                    choices[prevChoiceIndex].addAttendee(name);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        calculateSlot(courseCalendar, choiceNum+1, courses);
    }

    /*
    Getter Method:
    This method returns the ids of each of a student's choices
    */
    public int[] getChoiceIDs() {
        return choiceIDs;
    }

    /*
    Getter Method:
    This method returns the list of actual recieved choices of a student
    */
    public Course[] getChoices() {
        return choices;
    }

    /*
    Getter Method:
    This method returns the student's name
    */
    public String getName() {
        return name;
    }

    
    /*
    Setter Method:
    This method clears the student's choices so that they can be refilled again later
    */
    public void resetChoices() {
        choices = new Course[5];
    }
}