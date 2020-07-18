<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of Records from Address Book</title>
    <style>
        .sort { margin: 20px 0; }
        table { border-collapse: collapse; border: 2px solid slategrey; }
        td, th { border: 1px solid dimgrey; padding: 5px; }
        td:first-of-type { text-align: center; }
        tr:nth-of-type(even) { background-color: rgba(250, 250, 210, 0.5); }
        th:nth-of-type(even),
        td:nth-of-type(even) { background-color: rgba(220, 250, 230, 0.5); }
        .btn:hover { background-color: lightblue; }
    </style>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<div class="sort">
    Sort by:
    <a href="/records/list?sort=asc">ascending</a>
    &nbsp;&verbar;&nbsp;
    <a href="/records/list?sort=desc">descending</a>
</div>

<table>
    <tr>
        <th>No.</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Address</th>
        <th colspan="3">Operation</th>
    </tr>
    <%
        int counter = 1;
        for (String[] recordFields : (List<String[]>) request.getAttribute("records")) {
            String query = String.format("first-name=%s&last-name=%s",
                    recordFields[0], recordFields[1]);
    %>
    <tr>
        <td><%=counter++%></td>
        <td><%=recordFields[0]%></td>
        <td><%=recordFields[1]%></td>
        <td><%=recordFields[2]%></td>
        <td class="btn"><a href="/records/read?<%=query%>">Read</a></td>
        <td class="btn"><a href="/records/update?<%=query%>">Update</a></td>
        <td class="btn"><a href="/records/delete?<%=query%>">Delete</a></td>
    </tr>
    <%  }
    %>
</table>
</body>
</html>
