# Senior-Seminar-Avera
Project to help plan the classes taken by each member of a school. Optimization practice.

My plan for optimizing this program is to prioritize the first choices of those who pick first to reward the people who submited their class choices promptly, then go down the list of people by their submission time. Then I will move on to the second choice and repeat the process again and again until I have made it through all five choices. I will then add the people who didn't submit the form before the deadline randomly into classes with availability still remaining. 

1/18/24: <br>
Today, I finished formattng the student data and created a Student class to store each student's important data. I also extracted the course data from the course data file, and saved it into an ArrayList to be stored for later use. I also began planning out a Course class to categorize the data I extracted and make it easier to use in the future.

1/22/24: <br>
Today, I created a function to calculate the number of conficts between specified courses. This function will be used in the future to decide which classes should be paired in the same time blocks together.

1/24/24: <br>
Today, I worked on sorting the classes into their optimal timeslots in order to minimize the number of conflicts between courses, but I ran into some technical difficulties and wasn't able to get it fully working.
