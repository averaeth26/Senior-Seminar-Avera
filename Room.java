public class Room {
    int roomNumber;
    Course[] sessions = new Course[5];

    public Room(int num) {
        roomNumber = num;
    }

    public void addSession(Course course, int slotNum) {
        sessions[slotNum] = course; 
    }

    public String toString() {
        String build = "Room " + roomNumber + " is hosting sessions:\n";
        for (int i = 0; i < sessions.length; i++) {
            build += " - " + sessions[i].getCourseName()+" at timeslot " + (i+1) + " \n";
        }
        return build;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

}
