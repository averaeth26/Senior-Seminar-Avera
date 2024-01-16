public class Student {
    String name;
    String email;
    String submissionTime;
    int[] choices = new int[5];
    public Student(String data) {
        String[] separatedData = data.split(",");
        name = separatedData[3];
        email = separatedData[1];
        submissionTime = separatedData[0];
        for (int i = 0; i < 5; i ++) {
            choices[i] = Integer.parseInt(separatedData[i+4]);
        }
    }

}
