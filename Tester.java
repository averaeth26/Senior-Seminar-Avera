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
        for (int i = 0; i < courseCalendar.length; i++) {
            Course[] block1 = d1.calculateScheduleBlocks(courseRoster.get(0), courseRoster, students);
            for (int j = 0; j < block1.length; j++) {
                courseCalendar[i][j] = block1[j];
                System.out.println(block1[j]);
            }
            System.out.println();
        }
        
    }
}        