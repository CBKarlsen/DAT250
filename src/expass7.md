## Expass 7

## Experiment 1

I did not have any issues with setting it up. Using brew the download process was quite easy.

The parameters I used to create the database was:


```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=mydatabase -e POSTGRES_USER=casper -d --name my-postgres --rm postgres
```
<br>
I am using Intellij so managing the database was quite easy. I had a issue where intellij needed a user and a password to connect which is why i created
a user in above. After this was created I did not have any more issues.

The command which was given on github, was giving me a permission error so i had to change it to the following:

```
CREATE USER jpa_client WITH PASSWORD 'secret';
GRANT USAGE ON SCHEMA public TO jpa_client;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO jpa_client;
```
<br>
Whenever I tried to run the tests they where passing, however I got a red text in the terminal. This might be from the SQL permission.
Since the test passed I decided to move onto the next experiment.

## Experiment 2



