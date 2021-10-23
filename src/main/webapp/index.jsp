<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Image upload form</title>
</head>
<body>
<form action="FilesHandler?action=filesUpload" method="post" enctype="multipart/form-data">
Select images <input type="file" name="files" multiple/>
<input type="submit" value="upload"/>
<br/>
<a href="${pageContext.request.contextPath}/FilesHandler?action=listingImages">View available images</a>

</form>
</body>
</html>