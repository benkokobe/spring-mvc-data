<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="resources/styles/springsource.css"/>" type="text/css"/>
	<title>DR Transfer Report</title>
</head>
<body>
<div id="main_wrapper">
<table> 
<thead>
     <tr>
       <td>Welcome to Transfer Report Generator Helper Web App ( click the links below)!</td>
     </tr>
</thead>
</table>

     <ul>
		<li><a href="conflict">Conflicts Excel generator</a></li>
		<li><a href="deployment">Deployment request Excel generator</a></li>
		<li><a href="jspdeployment">Deployment request Report in JSP</a></li>
	</ul>
</div>
</body>
</html>
