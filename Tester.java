import java.util.ArrayList;

public class Tester {
    /* Rules to Note:
        - Instructors cannot be in two rooms at once.
        - Students cannot be in two rooms at once. 
        - Sessions cannot run more than twice.
        - There are 400 total available choice slots.
        - There are 345 total choice requests put in on time.
        - There is also an additional 30 total choices from students that missed the form submission deadline.
    */
    public static void main(String[] args) {
        Seminar d1 = new Seminar();
        System.out.println("\033[H\033[2J");
        d1.importData();
        ArrayList<Student> students = d1.populateStudents();
        ArrayList<Student> lateStudents = d1.populateLateStudents();
        ArrayList<Student> allStudents = new ArrayList<Student>();
        for (Student student : students) {
            allStudents.add(student);
        }
        for (Student student : lateStudents) {
            allStudents.add(student);
        }
        ArrayList<Course> courses = d1.populateCourses();
        Room[] rooms = new Room[5];
        ArrayList<Course> courseRoster = d1.generateCourseRoster(courses, students);
        Course[][] courseCalendar = d1.calculateScheduleBlocks(courseRoster, students, courses, rooms);
        d1.placeLateStudents(courseCalendar, lateStudents, courses);
        d1.printCourseCalendar(courseCalendar);
        d1.printStudentRoster(allStudents);
        d1.printCourseRoster(courseCalendar);
        d1.printRoomRoster(rooms);
    }
}         