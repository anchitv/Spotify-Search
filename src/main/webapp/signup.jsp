<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action = "addUser" method = "GET">
         Client Id: <input type = "text" name = "ClientId">
         <br />
         Name: <input type = "text" name = "UserName" />
         <br />
         Client Secret: <input type = "text" name = "ClientSecret" />
         <br />
         Password: <input type = "password" name = "Password" />
         <input type = "submit" value = "Submit" />
      </form>

</body>
</html>