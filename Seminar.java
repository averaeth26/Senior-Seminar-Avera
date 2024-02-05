import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Seminar {
    ArrayList<String> personData = new ArrayList<String>();
    ArrayList<String> lateData = new ArrayList<String>();
    ArrayList<String> courseData = new ArrayList<String>();
    Scanner scan = new Scanner(System.in);

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

    public ArrayList<Student> populateLateStudents() {
        ArrayList<Student> lateStudents = new ArrayList<Student>();

        for (int i = 0; i < lateData.size(); i++) {
            lateStudents.add(new Student(lateData.get(i)));
        }
        return lateStudents;
    }

    public ArrayList<Course> populateCourses() {
        ArrayList<Course> currentCourses = new ArrayList<Course>();

        for (int i = 0; i < courseData.size(); i++) {
            currentCourses.add(new Course(courseData.get(i)));
        }
        return currentCourses;
    }

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

    /* Generates a list of course sections based on interest for each course
     * Returns an ArrayList of courses representing the course roster 
     * This roster stores the number of times a section of each course will be held
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

    // public boolean arrContains(int[] arr, int testVal) {
    //     for (int value : arr) {
    //         if (value == testVal) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

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


    // public ArrayList<Integer> calculateCourseConflicts(Course course, ArrayList<Course> courses, ArrayList<Student> students) {
    //     ArrayList<Integer> numConflicts = new ArrayList<Integer>();
    //     for (int i = 0; i < courses.size(); i++) {
    //         if (course.getInstructorName().equals(courses.get(i).getInstructorName())) {
    //             numConflicts.add(students.size()); // If two courses are taught by the same person, they aren't compatible
    //             continue;

    //         }
    //         int currentConflicts = 0;
    //         for (Student student : students) {
    //             if (arrContains(student.getChoiceIDs(), course.getCourseID()) && arrContains(student.getChoiceIDs(), courses.get(i).getCourseID())) {
    //                 currentConflicts ++;
    //             }
    //         }
    //         numConflicts.add(currentConflicts);
    //     }
    //     return numConflicts;
    // }

    // public int findMinIndex(ArrayList<Integer> arr) {
    //     int minIndex = 0;
    //     int min = arr.get(0);
    //     for (int j = 0; j < arr.size(); j++) {
    //         if (arr.get(j) < min) {
    //             minIndex = j;
    //             min = arr.get(j);
    //         }
    //     }
    //     return minIndex;
    // }

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

    // public int calculateCheckSum(Course[][] courseGrid, ArrayList<Student> students) {
    //     int score = 0;
    //     double rowAverage = 116.6;
    //     // System.out.println(rowAverage);
    //     double currentRowTotal; 
    //     for (int row = 0; row < courseGrid.length; row++) {
    //         currentRowTotal = 0;
    //         // System.out.println();
    //         for (int col = 0; col < courseGrid.length; col++) {
    //             // System.out.println(courseGrid[row][col]);
    //             currentRowTotal += courseGrid[row][col].getInterestLevel();
    //         }
    //         System.out.println(Math.abs(rowAverage - currentRowTotal));
    //         score += Math.abs(rowAverage - currentRowTotal);
    //     }

    //     // for (int x = 0; x < courseGrid.length; x++) {
    //     //             score += (students.size() * countDuplicateInstructors(courseGrid[x]));
    //     // }
    //     return score;
    // }
    // public int calculateCheckSum(Course[][] courseGrid, ArrayList<Student> students) {
    //     int score = 0;
    //     for (Student student : students) {
    //         for (int row = 0; row < courseGrid.length; row++) {
    //             if (arrCountContains(courseGrid[row], student.getChoiceIDs()) == 0) {
    //                 score ++;
    //             }
    //         }
    //     }
    //     // for (int row = 0; row < courseGrid.length; row++) {
    //     //     // System.out.println(countDuplicateInstructors(courseGrid[row]));
    //     //     score += (students.size() * countDuplicateInstructors(courseGrid[row]));
    //     // }
    //     return score;
    // }
    
    public Course[][] swapValues (Course[][] courseGrid, int index1r, int index1c, int index2r, int index2c) {
        Course[][] tempGrid = new Course[courseGrid.length][courseGrid[0].length];
        Course curr;
        for (int i = 0; i < courseGrid.length; i++) {
            for (int j = 0; j < courseGrid[0].length; j++) {
                curr = courseGrid[i][j];
                tempGrid[i][j] = new Course(curr.getCourseName() + "," + curr.getCourseID() + "," + curr.getInstructorName());
                tempGrid[i][j].setInterestLevel(curr.getInterestLevel());
            }
        }
        Course temp = tempGrid[index1r][index1c];
        tempGrid[index1r][index1c] = tempGrid[index2r][index2c];
        tempGrid[index2r][index2c] = temp;
        return tempGrid;
    }

    public Course[][] shuffleCourseCalendar(Course[][] grid) {
        for (int i = 0; i < 100; i++) {
            int rand1 = (int)(Math.random()*25);
            int rand2 = (int)(Math.random()*25);
            // System.out.println(rand1 + ", " + rand2);
            grid = swapValues(grid, rand1/5, rand1%5, rand2/5, rand2%5);
        }
        return grid;
    }

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
                maxGrid = swapValues(currentGrid, 0, 0, 0, 0); // swapValues is used here as a way to copy the grid.
            }
        }
        for (int i = 0; i < maxGrid.length; i++) {
            rooms[i] = new Room(i+1);
            for (int j = 0; j < maxGrid[i].length; j++) {
                rooms[i].addSession(maxGrid[j][i], j);
                maxGrid[i][j].setRoomNumber(j+1);
            }
        }
        return maxGrid;
    }

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

    public void placeLateStudents(Course[][] courseCalendar, ArrayList<Student> students, ArrayList<Course> courses) {
        int numChoices = 5;
        for (int choiceNum = 0; choiceNum < numChoices; choiceNum++) {
            for (int i = 0; i < students.size(); i++) {
                students.get(i).calculateSlot(courseCalendar, choiceNum, courses);
            }
        }
    }

    public void printCourseCalendar(Course[][] courseCalendar) {
        for (int row = 0; row < courseCalendar.length; row ++) {
            System.out.println("Timeslot " + (row+1)+":\n");
            for (int col = 0; col < courseCalendar[0].length; col ++) {
                System.out.println("Room " + courseCalendar[row][col].getRoomNumber() + ": " + courseCalendar[row][col].getCourseName() + " by " + courseCalendar[row][col].getInstructorName());
            }
            System.out.println("\n");
        }
    }

    public void printStudentRoster(ArrayList<Student> students) {
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

    public void printRoomRoster(Room[] rooms) {
        System.out.print("\n\nEnter a room number to search for: ");
        int testNum = Integer.parseInt(scan.nextLine());
        if (0 < testNum && testNum < rooms.length) {
            System.out.println(rooms[testNum-1]);
            return;
        }
        System.out.println("The room you're looking for doesn't exist!");
    }

    public void printCourseRoster(Course[][] courseCalender) {
        System.out.print("\n\nEnter the ID of a Course to search for: ");
        int testNum = Integer.parseInt(scan.nextLine());
        boolean found = false;
        for (int row = 0; row < courseCalender.length; row++) {
            for (Course course : courseCalender[row]) {
                if (course.getCourseID() == testNum) {
                    System.out.println("\n" + course);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("The course you're looking for doesn't exist!");
        }
    }


        // for (int row = 0; row < courseCalendar.length; row++) {
        //     // System.out.println(countDuplicateInstructors(courseGrid[row]));
        //     score -= (students.size() * countDuplicateInstructors(courseCalendar[row]));
        // }
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
