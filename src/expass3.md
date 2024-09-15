# Expass 3

## Step 1:
I had no issue in setting up the front vite project, this i had done before. However i tried to get the backend 
part of the project into a folder which was called ´backend´. This led to several issues in my code. I 
could not get the proper filing of the backend to work with this new file setup. So i dont have "backend" and "backend" directories. Spent several hours and i gave up in the end.

## Step 2:
In my creation of the project I discovered that when creating my frontend that my backend might lack a voteoptionController. 

Files created:

- [CreatePoll.svelte](https://github.com/CBKarlsen/DAT250/blob/master/pollfrontend/src/lib/CreatePoll.svelte)
- [CreateUser.svelte](https://github.com/CBKarlsen/DAT250/blob/master/pollfrontend/src/lib/CreateUser.svelte)
- [Polls.svelte](https://github.com/CBKarlsen/DAT250/blob/master/pollfrontend/src/lib/Poll.svelte)
- [Vote.svelte](https://github.com/CBKarlsen/DAT250/blob/master/pollfrontend/src/lib/CreatePoll.svelte)


I found it quite difficult to work with the frontend and backend at the same time. I had to restart the backend server several times to get the frontend to work. I also had to restart the frontend server to get the backend to work.
In the end I got something to work, but I think it could have been done a bit better visually. If i had more time i would 
have played around with the visual part of the project a bit more.
## Step 3:

While developing and testing in my browser I had errors because of CORS. I had to add the following code to my controller files.
@CrossOrigin was not added to the controller files. This was added to the controller files to fix the CORS issue.

## Step 4: 

Used fetch to retrieve data from the backend. I had to add the following code to my frontend files to get the data from the backend. 
The implementation was confusing at the start, but i got the hang of it as i went along. I think my code might be a bit unstable, but it works for now. Next week I think ill try to improve the front end segment.


## Step 5:

I could not get this to work, will try to meet at a group session next week and get som assistance on this part.
My issue was locating the dist file, i tried several things. i think the issue might be in the package.json file.