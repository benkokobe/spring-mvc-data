<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link href="resources/styles/springsource.css" rel="stylesheet" type="text/css"/> 
	<title>DR list</title>
</head>

<body>


<div id="main_wrapper">

<h1>DR patch list for1: <c:out value="${user.drName}"/> </h1>
<table> 
<thead>
     <tr>
       <td>Members List: <a href="drPatchMembersList?drName=${user.drName}">${user.drName}</a></td>
     </tr>
     <tr>
       <td>Transfer operations List: <a href="drPatchOperationsList?drName=${user.drName}">${user.drName}</a></td>
     </tr>
</thead>

</table> 
<table> 
  <thead>
	<tr>
	   <td>Patch ID</td>
	   <td>Group Name</td>
	   <td>Subject</td>
	   <td>Version</td>
	</tr>
  </thead>
  <tbody>
    
    <c:forEach items="${patchList}" var="patch">
	     <tr>
		     <td><a href="accountDetails?entityId=${patch.patchId}">${patch.patchId}</a></td>
		     <td>${patch.nomGrp}</td>
		     <td>${patch.sujPat}</td>
		     <td>${patch.verPat}</td>
		 </tr>
	</c:forEach>
  </tbody>
</table> 
</div>

</body>

</html>