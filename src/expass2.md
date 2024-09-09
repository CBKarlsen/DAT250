## Report experiment 2:

### Introduction: 
This is going to be a short report which will be discussing the results of the second experiment. I will be discussing step-by-step what i have done and what the results were.

### Step 0:
The first step was to choose which HTTP client to use. I initially tried Bruno’s HTTP client but was unable to get it to work. I then used the HTTP client provided in the lab manual, which worked and gave the desired results. Due to personal reasons, I have not been able to attend lectures as much as I would like.
However, I started using the HTTP client built into my IDE (IntelliJ), and it worked fine for me. 
I haven’t been using it as efficiently as I should, but I am working on improving that.
### Step 1:
I used the project which was created for Expass 1. I had already configured the project and added it to my github so there wasnt any issues here. 

### Step 2:

I created the files needed in a folder called ["domain"](https://github.com/CBKarlsen/DAT250/tree/master/src/main/java/com/example/demo/domain) and in this folder i created the following classes:

Poll.java, found [here](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/domain/Poll.java):
- This class is the model for the poll object. It has the following fields:
    - id
    - question
    - options
    - votes
    - valid
    - getValid
    - user which created the poll
    - date created

User.java, found [here](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/domain/User.java):
- This class is the model for the user object. It has the following fields:
    - id
    - username
    - password
    - email(actually missing this on the current version)
    - date created

Vote.java, found [here](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/domain/Vote.java):
- This class is the model for the vote object. It has the following fields:
    - id
    - poll
    - option
    - user
    - date created

VoteOption.java, found [here](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/domain/VoteOption.java):
- This class is the model for the vote option object. It has the following fields:
    - id
    - poll
    - option
    - votes

These files I created as speciefied in the lab model and I used the IntelliJ IDE generative tool to create the getters and setters for the classes. 

### Step 3:
Setting up the "test", HTTP client in intelliJ was quite difficult when i did not have the working logic behind the files. This is my first time doing something like this so i tried my way forward.
I started with a POST request to create a user. which worked, did not have time to implement the email field. Then a GET call to se the users I had created which showed first one, then two users. The i tried to DELETE and to my suprise that worked aswell. 

### Step 4:
- PollController: Manages polls and votes, allowing users to create polls, cast votes, and update or delete polls.
- UserController: Manages user data, allowing users to be created and queried by username.
- The PollManager creates 4 hashmaps. One for users, one for poll, userpolls, and pollvotes. This is to "manage" the data. It facilitates the creation and management of polls, allows users to vote, and ensures proper clean-up when polls are deleted.
- The VoteController provides a way to retrieve a list of all votes through an HTTP GET request to /votes.
- The PollManager handles the underlying logic of retrieving and managing the votes.
- The response will contain a set of Vote objects, and the operation will always return an HTTP 200 OK response with the list of votes in the body.


Files:
- [UserController.java](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/controller/UserController.java)
- [Poll controller](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/controller/Pollcontroller.java)
- [VoteController.java](https://github.com/CBKarlsen/DAT250/blob/master/src/main/java/com/example/demo/controller/VoteController.java)

### Step 5:
I created a single automated test, i am struggling to test the others as I find it difficult to understand how to do it. However as you can see i passed 3/4 on the automatic test.
Found here [Test](https://github.com/CBKarlsen/DAT250/blob/master/src/test/java/com/example/demo/DemoApplicationTests.java)

### Conclusion:
I’ve learned a lot from this lab, but I made the mistake of not planning ahead, which caused me to overcomplicate many things. I’ll try to do better next time by attending more lectures and asking more questions. I also plan to improve my planning process and avoid rushing through assignments. Additionally, I will focus on understanding the logic behind the code better.
For future work, I would spend more time on the implementation of polls and perhaps divide it into a separate VoteOption controller.
