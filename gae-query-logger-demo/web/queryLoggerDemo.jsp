<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Query Analyzer Report</title>
    </head>
    <body>
        <ul>
            <c:forEach var="entry" items="${requestScope.reportEntries}">
                <li>${entry}</li>
            </c:forEach>
        </ul>
    </body>
</html>