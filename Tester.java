import java.util.ArrayList;

/**
Tester.java
@author Ethan Avera
@since 1/11/24
The tester class contains the main method.
This is the class that is initiated when running the program.
*/
public class Tester {
    /* Rules to Note:
        - Instructors cannot be in two rooms at once.
        - Students cannot be in two rooms at once. 
        - Sessions cannot run more than twice.
        - There are 400 total available choice slots.
        - There are 340 total choice requests put in on time.
        - There are also an additional 35 total choices from students that missed the form submission deadline.
    */

    /* 
    This is the method that actually runs the program.
    This method doesn't return any values.
    */
    public static void main(String[] args) {
        Seminar s1 = new Seminar();
        System.out.println("\033[H\033[2J");
        s1.importData();
        ArrayList<Student> students = s1.populateStudents();
        ArrayList<Student> lateStudents = s1.populateLateStudents();
        ArrayList<Student> allStudents = new ArrayList<Student>();
        for (Student student : students) {
            allStudents.add(student);
        }
        for (Student student : lateStudents) {
            allStudents.add(student);
        }
        ArrayList<Course> courses = s1.populateCourses();
        System.out.println("Please wait one moment while the program runs some calculations...");
        System.out.println("Also, please note that the schedule generated will differ slightly between runs due to runtime optimizations, but all schedules will be optimized");
        Room[] rooms = new Room[5];
        ArrayList<Course> courseRoster = s1.generateCourseRoster(courses, students);
        Course[][] courseCalendar = s1.calculateScheduleBlocks(courseRoster, students, courses, rooms);
        s1.placeLateStudents(courseCalendar, lateStudents, courses);
        s1.printCourseCalendar(courseCalendar);
        s1.printStudentRoster(courseCalendar, allStudents);
        s1.printCourseRoster(courseCalendar);
        s1.printRoomRoster(rooms);
    }
}         