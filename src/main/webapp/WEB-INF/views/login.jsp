<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
<h1 id="banner">Login to Security Demo</h1>
<form name="f" action="<c:url value='login.html'/>"
	  method="POST">
	<table>
		<tr>
			<td>Username:</td>
			<td><input type='username' name='username' id="username"/></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='password' id="password"></td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit" id="submit">&nbsp;<input name="reset" type="reset"></td>
		</tr>
	</table>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</body>
</html>