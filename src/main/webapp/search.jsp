<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action = "getResults">
         Client Id: <input type = "text" name = "clientid">
         <br />
         Password: <input type = "password" name = "password" />
         <br />
         Search Term: <input type = "text" name = "searchterm" />
         <input type = "submit" value = "🔍" />
         <br />
         Track: <input type = "checkbox" name = "track" checked="checked" />
         <br />
         Album: <input type = "checkbox" name = "album" checked="checked" />
         <br />
         Artist: <input type = "checkbox" name = "artist" checked="checked" />
         <br />
         Playlist: <input type = "checkbox" name = "playlist" checked="checked" />         
      </form>

</body>
</html>