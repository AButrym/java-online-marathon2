<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update existing User</title>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<form action="/records/update" method="post">
    <table>
        <tr>
            <td>
                <label for="firstname">First name: </label>
            </td>
            <td>
                <input type="text" id="firstname" name="firstname"
                       value="<%=request.getAttribute("firstname")%>" disabled>
            </td>
        </tr>
        <tr>
            <td>
                <label for="lastname">Last name: </label>
            </td>
            <td>
                <input type="text" id="lastname" name="lastname"
                       value="<%=request.getAttribute("lastname")%>" disabled>
            </td>
        </tr>
        <tr>
            <td>
                <label for="address">Address: </label>
            </td>
            <td>
                <textarea name="address" id="address" cols="20" rows="2"
                          placeholder="Address #1" required
                ><%=request.getAttribute("address")%></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Update">
            </td>
            <td>
                <input type="reset" value="Clear">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
