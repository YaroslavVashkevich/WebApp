<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Title</title></head>
<body>      
<img src="${pageContext.request.contextPath}/images/users/1.jpg" alt="User Image">
<form method="post" action="registration" enctype="multipart/form-data">
    <label for="name">Name:        
        <input type="text" name="name" id="name">
    </label><br>
    <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday" required>
    </label><br>
    <label for="ImageId">Image:
        <input type="file" name="image" id="imageId" required>
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId">
    </label><br>
    <label for="passwordId">Password:
        <input type="password" name="password" id="passwordId">
    </label><br>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select><br>
<c:forEach var="gender" items="${requestScope.genders}">
    <input type="radio" name="gender" value="${gender}"> ${gender}<br>
</c:forEach>
<button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
<div style="color:red">
<c:forEach var="error" items="${requestScope.errors}">
    <span>${error.message}</span><br>
</c:forEach>
</div></c:if>
</form>
</body>
</html>
