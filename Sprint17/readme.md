# Marathon Web Project
## Sprint #17 - Spring Security
### Task:

Add to the Web application from the previous sprint a *Security level*, which includes user *Authentication* and *Authorization*.

Implemented security level should include the next scenarios:

1. If the user has not yet logged in, then after visiting any page redirect him to the login page, which is available at URL *"/form-login"*. The login form should include fields for entering login and password, and the button *“LogIn”*. As the “username” for login, use the user’s email.

2. If the user entered the invalid credentials, then redirect him back to the login page with error information.

3. If the user's login and password entered correctly, then allow him access to web application according to his role:

4. If the role of the user is *MENTOR*, then allow him full access to all resources of the web application.

5. If the role of the user TRAINEE, then:

   - After going to the *“Marathons”* page, display for user the list of marathons where he participates without possibility of remove and edit marathons.
   - When user clicks on marathon name instead of *“Students”* page, redirect the user to the *“Sprints”* page for the selected marathon.
   - Prohibit for user viewing list of student, adding, editing, and deleting students.
   - Prohibit for user create marathons and sprints, and adding and removing students from the marathon.
   - In case of an attempt unauthorized access to forbidden URL, redirect the user to the *“Access Denied”* page and set the status code to *403*.

6. If the user clicks the *"LogOut"* button, then end the user session and redirect him to the login page.

For store user credentials use “users” and “roles” tables from database.

The user password should encoded using the BCrypt encryption algorithm.

Submit links to github repository and make short video (2-5 minutes) where demonstrate the functionality as the result of your work!

### youtube demo: *WIP*
