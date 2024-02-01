import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Seminar {
    ArrayList<String> personData = new ArrayList<String>();
    ArrayList<String> lateData = new ArrayList<String>();
    ArrayList<String> courseData = new ArrayList<String>();

    public void importData() {
        try {
            File myObj = new File("SeniorSeminar_PersonData.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.charAt(0) == ',') {
                    lateData.add(data);
                }
                else {
                    personData.add(data);
                }
            }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        personData.remove(0);
        Collections.sort(personData); // From https://www.w3schools.com/java/java_arraylist.asp

        try {
            File myObj = new File("SeniorSeminar_CourseData.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                courseData.add(data);
            }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        courseData.remove(0);
    }

    public ArrayList<Student> populateStudents() {
        ArrayList<Student> currentStudents = new ArrayList<Student>();

        for (int i = 0; i < personData.size(); i++) {
            currentStudents.add(new Student(personData.get(i)));
        }
        return currentStudents;
    }

    public ArrayList<Course> populateCourses() {
        ArrayList<Course> currentCourses = new ArrayList<Course>();

        for (int i = 0; i < courseData.size(); i++) {
            currentCourses.add(new Course(courseData.get(i)));
        }
        return currentCourses;
    }

    public int countCourse(ArrayList<Student> students, int testID) {
        int count = 0;
        for (Student student : students) {
            for (int choiceID : student.getChoiceIDs()) {
                if (choiceID == testID) {
                    count ++;
                    break;
                }
            }
        }
        return count;
    }

    /* Generates a list of course sections based on interest for each course
     * Returns an ArrayList of courses representing the course roster 
     * This roster stores the number of times a section of each course will be held
     */
    public ArrayList<Course> generateCourseRoster(ArrayList<Course> courses, ArrayList<Student> students) {
        ArrayList<Integer> interestLevels = new ArrayList<Integer>();
        ArrayList<Course> courseSlots = new ArrayList<Course>();
        for (int courseNum = 0; courseNum < courses.size(); courseNum++) {
            System.out.println(courses.get(courseNum));
            interestLevels.add(countCourse(students, courses.get(courseNum).getCourseID()));
            courses.get(courseNum).setInterestLevel(countCourse(students, courses.get(courseNum).getCourseID()));
        }
        System.out.println(interestLevels);
        for (int i = 0; i < courses.size(); i++) {
            courseSlots.add(courses.get(i));
            if (courses.get(i).getInterestLevel() > courses.get(i).getMaxCapacity()) {
                courseSlots.add(courses.get(i));
            }
        }
        return courseSlots;
    }

    public int arrCount(int[] arr, int testVal) {
        int counter = 0;
        for (int value : arr) {
            if (value == testVal) {
                counter ++;
            }
        }
        return counter;
    }

    public int countDuplicateInstructors(Course[] arr) {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i].getInstructorName() == arr[j].getInstructorName() && j > i) {
                    counter ++;
                }
            }
        }
        return counter;
    }


    public ArrayList<Integer> calculateCourseConflicts(Course course, ArrayList<Course> courses, ArrayList<Student> students) {
        ArrayList<Integer> numConflicts = new ArrayList<Integer>();
        for (int i = 0; i < courses.size(); i++) {
            if (course.getInstructorName().equals(courses.get(i).getInstructorName())) {
                numConflicts.add(students.size()); // If two courses are taught by the same person, they aren't compatible
                continue;

            }
            int currentConflicts = 0;
            for (Student student : students) {
                if (arrCount(student.getChoiceIDs(), course.getCourseID()) > 0 && arrCount(student.getChoiceIDs(), courses.get(i).getCourseID()) > 0) {
                    currentConflicts ++;
                }
            }
            numConflicts.add(currentConflicts);
        }
        return numConflicts;
    }

    public int findMinIndex(ArrayList<Integer> arr) {
        int minIndex = 0;
        int min = arr.get(0);
        for (int j = 0; j < arr.size(); j++) {
            if (arr.get(j) < min) {
                minIndex = j;
                min = arr.get(j);
            }
        }
        return minIndex;
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

    public int calculateCheckSum(Course[][] courseGrid, ArrayList<Student> students) {
        int score = 0;
        for (Student student : students) {
            for (int row = 0; row < courseGrid.length; row++) {
                if (arrCountContains(courseGrid[row], student.getChoiceIDs()) == 0) {
                    score ++;
                }
            }
        }
        for (int row = 0; row < courseGrid.length; row++) {
            // System.out.println(countDuplicateInstructors(courseGrid[row]));
            score += (students.size() * countDuplicateInstructors(courseGrid[row]));
        }
        return score;
    }

    public Course[][] swapValues (Course[][] courseGrid, int index1r, int index1c, int index2r, int index2c) {
        Course temp = courseGrid[index1r][index1c];
        courseGrid[index1r][index1c] = courseGrid[index2r][index2c];
        courseGrid[index2r][index2c] = temp;
        return courseGrid;
    }

    public Course[][] calculateScheduleBlocks(ArrayList<Course> courseRoster, ArrayList<Student> students) {
        Course[][] currentGrid = new Course[5][5];
        Course[][] nextGrid = new Course[5][5];
        for (int i = 0; i < courseRoster.size(); i++) {
            currentGrid[i/5][i%5] = courseRoster.get(i);
        }
        int checkSum = calculateCheckSum(currentGrid, students);
        int nextSum;
        for (int x = 0; x < courseRoster.size(); x++) {
            for (int i = 0; i < courseRoster.size(); i++) {
                for (int j = 0; j < courseRoster.size(); j++) {
                    nextGrid = swapValues(currentGrid, i/5, i%5, j/5, j%5);
                    nextSum = calculateCheckSum(nextGrid, students);
                    if (nextSum < checkSum) {
                        System.out.println(checkSum + "->" + nextSum);
                        currentGrid = nextGrid;
                        checkSum = nextSum;
                        i = -1;
                        break;
                    }
                }
            }
        }
        return currentGrid;
    }

    /*
    Possible rework to this function:
    - Put all of the courses in in their default order and calculate a "sum" of conflicts.
    -  Then swap two of them at a time if a swap would result in a lower sum (probably recursively) until no more swaps can be made.
    // */
    // public Course[] calculateScheduleBlocks(Course course, ArrayList<Course> courseRoster, ArrayList<Student> students) {
    //     ArrayList<Integer> conflicts = calculateCourseConflicts(course, courseRoster, students);
    //     courseRoster.remove(0);
    //     conflicts.remove(0);
    //     Course[] courseBlocks = new Course[5];
    //     ArrayList<Integer> newConflicts = new ArrayList<Integer>();
    //     courseBlocks[0] = course;
        
    //     for (int i = 1; i < 5; i++) {
    //         int minIndex = findMinIndex(conflicts);
    //         for (int j = 0; j < courseRoster.size(); j++) {
    //             if (countInstructor(courseRoster, courseRoster.get(j).getInstructorName())*5 > courseRoster.size()) {
    //                 minIndex = j;
    //             }
    //         }
    //         courseBlocks[i] = courseRoster.get(minIndex);
    //         newConflicts = calculateCourseConflicts(courseBlocks[i], courseRoster, students);
    //         for (int j = 0; j < courseRoster.size(); j++) {
    //             conflicts.set(j, conflicts.get(j) + newConflicts.get(j));
    //         }
    //         courseRoster.remove(minIndex);
    //         conflicts.remove(minIndex);
    //     }
    //     return courseBlocks;
    // }

    // public Course[][] generateCourseCalendar(ArrayList<Course> courseRoster, ArrayList<Student> students) {
    //     Course[][] courseCalendar = new Course[5][5];
    //     for (int i = 0; i < courseCalendar.length; i++) {
    //         Course[] block1 = calculateScheduleBlocks(courseRoster.get(0), courseRoster, students);
    //         for (int j = 0; j < block1.length; j++) {
    //             courseCalendar[i][j] = block1[j];
    //             System.out.println(block1[j]);
    //         }
    //         System.out.println();
    //     }
    //     return courseCalendar;
    // }
   
}
