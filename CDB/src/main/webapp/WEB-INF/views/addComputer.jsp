<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">


</head>
<body>

    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
      		<a class="navbar-brand pull-left" href="dashboard?page=1&field=default&order=default"> Application - Computer Database </a>
           	<a class="navbar-brand pull-right" href="?lang=en">English</a>|
           	<p class="navbar-brand pull-right">|</p>
           	<a class="navbar-brand pull-right" href="?lang=fr">Français</a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="addComputer.title"/></h1>
					<form:form action="addComputer" method="POST" modelAttribute="computerDTO">
                            <div class="form-group">
								<form:label for="computerName" path="name"><spring:message code="addComputer.name"/></form:label>
								<spring:message code="addComputer.placeholder.computerName" var="placeHolderName"/>
								<form:input class="form-control" id="computerName" path="name" placeholder="${placeHolderName}"/>
                                <font color=red><form:errors path="name"/></font>
                            </div>
                            <div class="form-group">
								<form:label for="introduced" path="ldIntroduced"><spring:message code="addComputer.introduction"/></form:label>
								<form:input type="date" class="form-control" id="introduced" path="ldIntroduced"/>        
								<font color=red><form:errors path="ldIntroduced"/></font>								                    
                            </div>
                            <div class="form-group">
								<form:label for="discontinued" path="ldDiscontinued"><spring:message code="addComputer.discontinuation"/></form:label>
								<form:input type="date" class="form-control" id="discontinued" path="ldDiscontinued"/>
								<font color=red><form:errors path="ldDiscontinued"/></font>								                                                
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="addComputer.company"/></label>
								<form:select class="form-control" id="companyId" path="companyId">
								    <spring:message code="addComputer.placeholder.selectCompany" var="selectCompanyPlaceholder"/>
									<form:option value="" label="-- ${selectCompanyPlaceholder}"/>
	              					<form:options items="${companies}" itemValue="id" itemLabel="name"/>
								</form:select>                            
                            </div>
							<div class="actions pull-right">
                           		<input type="submit" value="<spring:message code="addComputer.button.add"/>" class="btn btn-primary">
                        		<spring:message code="addComputer.or"/>
                          		<a href="dashboard" class="btn btn-default"><spring:message code="addComputer.button.cancel"/></a>
                        	</div>
					</form:form>
				</div>
            </div>
        </div>
    </section>
</body>
</html>

