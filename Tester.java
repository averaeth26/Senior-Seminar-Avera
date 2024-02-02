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
        // for (Student student : students) {
        //     System.out.println(student);
        // }
        // for (Course course : courses) {
        //     System.out.println(d1.calculateCourseConflicts(course, courses, students));
        // }
        ArrayList<Course> courseRoster = d1.generateCourseRoster(courses, students);
        Course[][] courseCalendar = d1.calculateScheduleBlocks(courseRoster, students);
        for (int row = 0; row < courseCalendar.length; row ++) {
            int counter = 0;
            System.out.println("");
            for (int col = 0; col < courseCalendar[0].length; col ++) {
                System.out.println(courseCalendar[row][col]);
                counter += courseCalendar[row][col].getInterestLevel();
            }
            System.out.println(counter);
        }
        d1.placeStudents(courseCalendar, students);
        for (Student student : students) {
            // student.calculateSlot(courseCalendar, courses);
            System.out.println(student.getName() + ": " + d1.arrCountContains(student.getChoices(), student.getChoiceIDs()));
        }
    }
}         