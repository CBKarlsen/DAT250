## Report experiment 2:

### Introduction: 
This is going to be a short report which will be discussing the results of the second experiment. I will be discussing step-by-step what i have done and what the results were.

### Step 0:
The first step was to choose which one of the HTTP clients i wanted to use. I tried using Bruno's HTTP client but i was not able to get it to work. I then tried using the HTTP client that was provided in the lab manual. I was able to get it to work and i was able to get the results that i wanted. I haven´t been able to attent lectures as much as i want because of personal reasons. However i started using the HTTP client built into my IDE which is IntelliJ, this worked fine for me. I have not been using as effieciently as i am suposed to but i am working on it.

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

These files I created as speciefied in the lab model and i used the IntelliJ IDE generative tool to create the getters and setters for the classes. 
Everything was working fine until i started the next step.

### Step 3:
Setting up the "test", HTTP client in intelliJ was quite difficult when i did not have the working logic behind the files. This is my first time doing something like this so i tried my way forward.
I started with a POST request to create a user. which worked, did not have time to implement the email field. Then a GET call to se the users I had created which showed first one, then two users. The i tried to DELETE and to my suprise that worked aswell. 

### Step 4:
This was a real challenge for me, I found this to be really difficult to work on this project as there was some vague examples and I don´t understand it well.
I think my lack of understanding made me over complicate a lot of stuff. I got some things to work and some other things not to work. However i tried to debug and find out what i was doing wrong. One of my challenges was to implement a way to change username. I do not know if it works as i tried really hard in my HTTP client to test it, i only got bad requests. 
