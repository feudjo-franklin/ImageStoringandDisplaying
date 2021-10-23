<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.studyeasy.hibernate.entity.Files" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listing Images</title>
</head>
<body>
<h1>listing images</h1>

<%
	String path = (String) request.getAttribute("path");
	List<Files> files = (List<Files>) request.getAttribute("files");
	for(Files file : files){
		out.print("<br/><img src="+path+file.getFileName()+">");
	}


%>
</body>
</html>