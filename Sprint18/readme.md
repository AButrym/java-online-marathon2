# Sprint18
## Rest API. Serialization. JSON

Add to the application from the previous sprint new REST Controllers, which includes user Authentication and Authorization by JWT-token.

Implemented security level should include the next scenarios:

1. If the user's login and password send (use POST method) correctly, then allow him access according to his role

2. If the role of the user is MENTOR, then allow him full access to all resources.

3. If the role of the user TRAINEE, then:
   - Get the list of marathons where he participates (GET method).
   - Get the “Sprints” resource for the selected marathon.
   - Prohibit for user viewing list of student, adding, editing, and deleting students resources.
   - Prohibit for user create marathons and sprints resources, and adding and removing students from the marathon.

For store user credentials use "users" and "roles" tables from database.

The user password should encoded using the BCrypt encryption algorithm.

Submit links to github repository and make short video (2-5 minutes) where demonstrate the functionality as the result of your work!

**WIP**
