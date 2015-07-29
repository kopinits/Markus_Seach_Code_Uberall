<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
    ${error_message}<br><br>
    Results for  ${searchModel.companyName} at ${searchModel.street},  ${searchModel.number} -  ${searchModel.city}<br><br>
    ${success_message}
</body>
</html>