<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

	
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 
	<link href="<c:url value="styles/springsource.css" />" rel="stylesheet"  type="text/css" />
	<title>Deployment Request Report</title>
</head>

<body>
<div id="main_wrapper">

<form:form method="post" modelAttribute="deploymentRequest">
<table>
  <tr>
    <td>Deployment Request Name:</td>
    <td><form:input path="drName"/></td>
    <td><form:errors path="drName" cssClass="error" /></td>
  </tr>
  <tr>
    <td colspan="3">
      <input type="hidden" value="0" name="_page"/>
      <input type="submit" value="Next" name="_target1" />
      <input type="submit" value="Cancel" name="_cancel" />
    </td>
  </tr>
</table>
</form:form>
</div>
</body>
</html>