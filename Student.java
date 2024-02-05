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
        String build = name;
        if (email.length() > 0) {
            build += " can be reached at " + email + " and";
        }
        build += " is taking:\n";
        for (Course course : choices) {
            build += " - " + course.getCourseName() + "\n";
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
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].getCourseID() == testVal) {
                return true;
            }
        }
        return false;
    }

    public int findMinIndex(ArrayList<Integer> arr) {
        int minIndex = 0;
        int min = arr.get(0);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) < min) {
                minIndex = i;
                min = arr.get(i);
            }
        }
        return minIndex;
    }

// Notes for self:
// Generate "Hopeful" roster for each student, then, swap out any students over the maximum,
// prioritizing swapping those who had a "conflict" that block as they will still get one of their choices

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
    // public void calculateSlot(Course[][] courseCalendar, ArrayList<Course> courses) {
    //     for (int i = 0; i < courseCalendar.length; i++) {
    //         courseCalendar[i] = sortByInterest(courseCalendar[i]);
    //         if (arrCountContains(courseCalendar[i], choiceIDs) > 1 || arrCountContains(courseCalendar[i], choiceIDs) < 1) {
    //             continue;
    //         }
    //         for (int id : choiceIDs) {
    //             if (arrContains(courseCalendar[i], id) && !arrContains(choices, id) && courses.get(id-1).getAttendance() < courses.get(id-1).getMaxCapacity()) {
    //                 choices[i] = courses.get(id-1);
    //             }
    //         }
    //     }

    //     for (int i = 0; i < courseCalendar.length; i++) {
    //         // System.out.println(arrCountContains(courseCalendar[i], choiceIDs));
    //         if (arrCountContains(courseCalendar[i], choiceIDs) > 1) {
    //             for (int id : choiceIDs) {
    //                 if (arrContains(courseCalendar[i], id) && !arrContains(choices, id) && courses.get(id-1).getAttendance() < courses.get(id-1).getMaxCapacity()) {
    //                     choices[i] = courses.get(id-1);
    //                     continue;
    //                 }
    //             }
    //         }
    //         if (choices[i] == null) {
    //             // if (courseCalendar[i][0].getAttendance() < courseCalendar[i][0].getMaxCapacity()) {
    //             //     choices[i] = courseCalendar[i][0];
    //             for (int j = 0; j < 5; j++) {

    //                 if (courseCalendar[i][j].getAttendance() < courseCalendar[i][j].getMaxCapacity()) {
    //                     choices[i] = courseCalendar[i][j];
    //                     break;
    //                 } 
    //             }
    //         }
    //         choices[i].addAttendee();
    //     }
    // }

    public int[] getChoiceIDs() {
        return choiceIDs;
    }

    public Course[] getChoices() {
        return choices;
    }

    public String getName() {
        return name;
    }

    public void resetChoices() {
        choices = new Course[5];
    }
}