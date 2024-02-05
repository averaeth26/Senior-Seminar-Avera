import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
Seminar.java
@author Ethan Avera
@since 1/16/24
This class handles all of the overarching scheduling/formatting logic to make the program run
This includes calculating the optimal schedule, putting students onto that schedule, and printing the different rosters
*/
public class Seminar {
    ArrayList<String> personData = new ArrayList<String>();
    ArrayList<String> lateData = new ArrayList<String>();
    ArrayList<String> courseData = new ArrayList<String>();
    Scanner scan = new Scanner(System.in);

    /*
    This method reads the imput files and extracts the necessary data from them
    This method has no return value
    */
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

    /*
    This method fills a list of students based on the data loaded above
    This method then returns that list
    */
    public ArrayList<Student> populateStudents() {
        ArrayList<Student> currentStudents = new ArrayList<Student>();

        for (int i = 0; i < personData.size(); i++) {
            currentStudents.add(new Student(personData.get(i)));
        }
        return currentStudents;
    }

    /*
    This method fills a list of students who did not fill out the form on time based on the data loaded above
    This method then returns that list
    */
    public ArrayList<Student> populateLateStudents() {
        ArrayList<Student> lateStudents = new ArrayList<Student>();

        for (int i = 0; i < lateData.size(); i++) {
            lateStudents.add(new Student(lateData.get(i)));
        }
        return lateStudents;
    }

    /*
    This method fills a list of courses based on the data loaded above
    This method then returns that list
    */
    public ArrayList<Course> populateCourses() {
        ArrayList<Course> currentCourses = new ArrayList<Course>();

        for (int i = 0; i < courseData.size(); i++) {
            currentCourses.add(new Course(courseData.get(i)));
        }
        return currentCourses;
    }

    /*
    This method counts the number of students who have chosen a given course
    This method then returns that number
    */
    public int countCourseInterest(ArrayList<Student> students, int testID) {
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

    /*
    Generates a list of course sections based on interest for each course
    Returns an ArrayList of courses representing the course roster 
    This roster stores the number of times a section of each course will be held
    */
    public ArrayList<Course> generateCourseRoster(ArrayList<Course> courses, ArrayList<Student> students) {
        ArrayList<Course> courseSlots = new ArrayList<Course>();
        for (int courseNum = 0; courseNum < courses.size(); courseNum++) {
            courses.get(courseNum).setInterestLevel(countCourseInterest(students, courses.get(courseNum).getCourseID()));
        }
        for (int i = 0; i < courses.size(); i++) {
            courseSlots.add(new Course(courses.get(i).getCourseName() + "," + courses.get(i).getCourseID() + "," + courses.get(i).getInstructorName()));
            courses.get(i).addSession();
            if (courses.get(i).getInterestLevel() > courses.get(i).getMaxCapacity()) {
                courseSlots.add(new Course(courses.get(i).getCourseName() + "," + courses.get(i).getCourseID() + "," + courses.get(i).getInstructorName()));
                courses.get(i).addSession();
            }
        }
        for (int courseNum = 0; courseNum < courseSlots.size(); courseNum++) {
            courseSlots.get(courseNum).setInterestLevel(countCourseInterest(students, courseSlots.get(courseNum).getCourseID()));
        }
        return courseSlots;
    }

    /*
    This method is a helper method for the placeStudents() method below
    This method counts the number of times that an instructor is teaching multiple classes at once
    This method then returns that number
    */
    public int countDuplicateInstructors(Course[] arr) {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                // System.out.println(arr[i].getInstructorName() + arr[j].getInstructorName());
                if (arr[i].getInstructorName().equals(arr[j].getInstructorName())) {
                    total ++;
                }
            }
        }
        return total;
    }

    /*
    This method counts the number of occurrences of the values of one array in another array
    This method then returns that number
    */
    public int arrCountContains(Course[] arr, int[] arr2) {
        int counter = 0;
        for (Course element : arr) {
            for (int element2 : arr2) {
                if (element != null && element.getCourseID() == element2) {
                    counter ++;
                }
            }
        }
        return counter;
    }

    /*
    This is a helper method for the calculateScheduleBlocks() method below
    This method swaps two values within a 2d course array and returns the modified 2d array
    */
    public Course[][] swapValues (Course[][] courseGrid, int index1r, int index1c, int index2r, int index2c) {
        Course[][] tempGrid = new Course[courseGrid.length][courseGrid[0].length];
        Course curr;
        for (int i = 0; i < courseGrid.length; i++) {
            for (int j = 0; j < courseGrid[0].length; j++) {
                curr = courseGrid[i][j];
                tempGrid[i][j] = new Course(curr.getCourseName() + "," + curr.getCourseID() + "," + curr.getInstructorName());
                tempGrid[i][j].setInterestLevel(curr.getInterestLevel());
                tempGrid[i][j].setAttendance(curr.getAttendance(), curr.getEnrolled());

            }
        }
        Course temp = tempGrid[index1r][index1c];
        tempGrid[index1r][index1c] = tempGrid[index2r][index2c];
        tempGrid[index2r][index2c] = temp;
        return tempGrid;
    }

    /*
    This is a helper method for the calculateScheduleBlocks() method below
    This method randomly shuffles the values within a 2d course array and returns the shuffled array
    */
    public Course[][] shuffleCourseCalendar(Course[][] grid) {
        for (int i = 0; i < 100; i++) {
            int rand1 = (int)(Math.random()*25);
            int rand2 = (int)(Math.random()*25);
            // System.out.println(rand1 + ", " + rand2);
            grid = swapValues(grid, rand1/5, rand1%5, rand2/5, rand2%5);
        }
        return grid;
    }

    /*
    This method optimizes the course schedule based on the number of student choices it is able to supply
    This method works by slowly working its way from a randomized grid upwards until it has reached the highest score it can obtain from that grid
    Ideally, the program would be able to score every possible permutation of the course schedule (25! permutations) and pick the best one
    Unfortunately, however, 25! is far too large a number of grids to calculate in a reasonable amount of time
    As a result, I decided to just pick 100 random grids (100 attempts) and run the optimization on each of them to hopefully get a grid that's at least close to perfect
    This does mean that the schedule won't be exactly the same each time the program is run, but it will always be a fairly solid optimization
    */
    public Course[][] calculateScheduleBlocks(ArrayList<Course> courseList, ArrayList<Student> students, ArrayList<Course> courses, Room[] rooms) {
        Course[][] currentGrid = new Course[5][5];
        Course[][] nextGrid = new Course[5][5];
        for (int i = 0; i < courseList.size(); i++) {
            currentGrid[i/5][i%5] = courseList.get(i);
        }
        int checkSum;
        int maxSum = 0;
        Course[][] maxGrid = new Course[5][5];;
        int nextSum;
        for (int attempt = 0; attempt < 100; attempt++) {
            currentGrid = shuffleCourseCalendar(currentGrid);
            checkSum = placeStudents(currentGrid, students, courses);

            for (int i = 0; i < courseList.size(); i++) {
                for (int j = i; j < courseList.size(); j++) {
                    nextGrid = swapValues(currentGrid, i/5, i%5, j/5, j%5);
                    nextSum = placeStudents(nextGrid, students, courses);
                    if (nextSum > checkSum) {
                        // System.out.println(checkSum + "->" + nextSum);
                        currentGrid = nextGrid;
                        checkSum = nextSum;
                        i = -1;
                        break;
                    }
                }
            }
            if (checkSum > maxSum) {
                maxSum = checkSum;
                System.out.println(maxSum);
                maxGrid = swapValues(currentGrid, 0, 0, 0, 0); // swapValues is used here as a way to copy the grid.
            }
        }
        placeStudents(maxGrid, students, courses);
        for (int i = 0; i < maxGrid.length; i++) {
            rooms[i] = new Room(i+1);
            for (int j = 0; j < maxGrid[i].length; j++) {
                rooms[i].addSession(maxGrid[j][i], j);
                maxGrid[i][j].setRoomNumber(j+1);
            }
        }
        return maxGrid;
    }

    /*
    A minor runtime optimization that sorts courses from high interest to low interest
    This way, the high interest courses which more students will want will be first and the program will more often be able to stop sooner
    */
    public Course[] sortByInterest(Course[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[j].getInterestLevel() > arr[i].getInterestLevel()) {
                    Course temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /*
    This is a helper method for the calculateScheduleBlocks() function above
    This method calculates the total number of choices students will be granted from a given schedule and returns it
    */
    public int placeStudents(Course[][] courseCalendar, ArrayList<Student> students, ArrayList<Course> courses) {
        for (int row = 0; row < courseCalendar.length; row++) {
            sortByInterest(courseCalendar[row]);
            for (int col = 0; col < courseCalendar[0].length; col++) {
                courseCalendar[row][col].resetAttendance();
            }
        }
        for (Student student : students) {
            student.resetChoices();
        }
        int score = 0;
        int numChoices = 5;
        for (int choiceNum = 0; choiceNum < numChoices; choiceNum++) {
            for (int i = 0; i < students.size(); i++) {
                students.get(i).calculateSlot(courseCalendar, choiceNum, courses);
            }
        }

        for (Student student : students) {
            score += arrCountContains(student.getChoices(), student.getChoiceIDs());
        }
        for (int row = 0; row < courseCalendar.length; row++) {
            score -= (students.size() * countDuplicateInstructors(courseCalendar[row]));
        }
        return score;
    }

    /*
    This method fills in the choices of students who didn't fill out the form based on remaining availability after optimization
    This method has no return value
    */
    public void placeLateStudents(Course[][] courseCalendar, ArrayList<Student> students, ArrayList<Course> courses) {
        int numChoices = 5;
        for (int choiceNum = 0; choiceNum < numChoices; choiceNum++) {
            for (int i = 0; i < students.size(); i++) {
                students.get(i).calculateSlot(courseCalendar, choiceNum, courses);
            }
        }
    }

    /*
    This method prints out the course calender for the whole seminar event.
    This method has no return value
    */
    public void printCourseCalendar(Course[][] courseCalendar) {
        for (int row = 0; row < courseCalendar.length; row ++) {
            System.out.println("Timeslot " + (row+1)+":\n");
            for (int col = 0; col < courseCalendar[0].length; col ++) {
                System.out.println("Room " + courseCalendar[row][col].getRoomNumber() + ": " + courseCalendar[row][col].getCourseName() + " by " + courseCalendar[row][col].getInstructorName());
            }
            System.out.println("\n");
        }
    }

    /*
    This method prints out the course roster for a specified student
    This method has no return value
    */
    public void printStudentRoster(Course[][] courseCalendar, ArrayList<Student> students) {
        System.out.print("\n\nEnter the name of a student to search for: ");
        String testName = scan.nextLine();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(testName)) {
                System.out.println("\n" + student);
                return;
            }
        }
        System.out.println("\nThe student you're looking for doesn't exist!");
    }

    /*
    This method prints out the course roster for a specified room
    This method has no return value
    */
    public void printRoomRoster(Room[] rooms) {
        System.out.print("\n\nEnter a room number to search for: ");
        int testNum = Integer.parseInt(scan.nextLine());
        if (0 < testNum && testNum < rooms.length) {
            System.out.println(rooms[testNum-1]);
            return;
        }
        System.out.println("The room you're looking for doesn't exist!");
    }

    /*
    This method prints out the student roster for a specified course
    This method has no return value
    */
    public void printCourseRoster(Course[][] courseCalender) {
        System.out.print("\n\nEnter the ID number of a Course to search for: ");
        int testNum = Integer.parseInt(scan.nextLine());
        boolean found = false;
        for (int row = 0; row < courseCalender.length; row++) {
            for (Course course : courseCalender[row]) {
                if (course.getCourseID() == testNum) {
                    System.out.println(row);

                    System.out.println("\n" + course);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("The course you're looking for doesn't exist!");
        }
    }
}
