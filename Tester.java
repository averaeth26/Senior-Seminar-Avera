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
        d1.importData();
        ArrayList<Student> students = d1.populateStudents();
        ArrayList<Course> courses = d1.populateCourses();
        for (Student student : students) {
            System.out.println(student);
        }
        ArrayList<Course> courseRoster = d1.generateCourseRoster(courses, students);
        Course[][] courseCalendar = new Course[5][5];
        courseCalendar = d1.generateCourseCalendar(courseRoster, students);
        for (Student student : students) {
            student.calculateGuaranteedSlots(courseCalendar, courses);
        }
        System.out.println(students.get(2).getName());
        for (Course val : students.get(2).getChoices()) {
            System.out.println(val);
        }
    }
}        