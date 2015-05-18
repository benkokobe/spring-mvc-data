<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<link href="resources/styles/springsource.css" rel="stylesheet" type="text/css"/> 
	<title>Transfer report</title>
</head>

<body>
<div id="main_wrapper">

<h1>Transfer report</h1>


<table> 
<thead>
     <tr>
       <td>Enter DR name and Reflot!</td>
     </tr>
</thead>
</table>
<form:form method="post" modelAttribute ="user">
<table>
  <tr>
      <td>DR Name: </td>
      <td><form:input path="username"/></td>
      <td><form:errors path="username" cssClass="errors"/></td>
  </tr>
  <tr>
      <td>REFLOT: </td>
      <td><form:input path="email"/></td>
      <td><form:errors path="email" cssClass="errors"/></td>
  </tr>
  <tr>
      <td colspan="3"><input type="submit" value="Save"></td>
      
</table>
</form:form>
</div>
</body>
</html>
