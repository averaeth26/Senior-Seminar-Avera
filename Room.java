/**
Room.java
@author Ethan Avera
@since 1/16/24
This class handles the functionality of creating and manipulating Rooms to be used in the program.
*/
public class Room {
    int roomNumber;
    Course[] sessions = new Course[5];

    // Room Constructor
    public Room(int num) {
        roomNumber = num;
    }

    /*
    Setter method: This method adds a session to the slot data of the room
    This method has no return type
    */
    public void addSession(Course course, int slotNum) {
        sessions[slotNum] = course; 
    }

    /*
    toString Method: Formats the Room as a string to prepare it for printing
    Returns the formatted String
    */
    public String toString() {
        String build = "Room " + roomNumber + " is hosting sessions:\n";
        for (int i = 0; i < sessions.length; i++) {
            build += " - " + sessions[i].getCourseName()+" at timeslot " + (i+1) + " \n";
        }
        return build;
    }

    /*
    Getter method:
    This method returns the room number of a room
    */
    public int getRoomNumber() {
        return roomNumber;
    }

}
