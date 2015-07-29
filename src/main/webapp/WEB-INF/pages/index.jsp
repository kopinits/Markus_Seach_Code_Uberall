<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
	<f:form action="search" method="post" modelAttribute="searchModel">
        Company Name: <f:input path="companyName" /><br>
        Street: <f:input path="street" /><br>
        Number: <f:input path="number" /><br>
        City: <f:input path="city" /><br>
      <input type="submit" value="Search">
	</f:form>
</body>
</html>