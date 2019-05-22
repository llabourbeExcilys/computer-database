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
            <a class="navbar-brand" href="dashboard?page=1&field=default&order=default"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1><spring:message code="editComputer.title"/></h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" value="${computer.id}" name="id" id="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="editComputer.name"/></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" pattern="[A-Za-z0-9\. ]{3,30}" required title="3 characters minimum, 30 maximum" placeholder="Computer name" value="${computer.name}">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="editComputer.introduction"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.ldIntroduced}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="editComputer.discontinuation"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.ldDiscontinued}">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="editComputer.company"/></label>
                                <select class="form-control" id="companyId" name="companyId">	
                              		<spring:message code="editComputer.placeholder.selectCompany" var="editComputerPlaceholder"/>
                                   <option <c:if test="${computer.companyId == null}">selected="selected"</c:if>  value="">-- ${editComputerPlaceholder}</option>
                                   <c:forEach  var="company" items="${companies}">
                                   		<option <c:if test="${company.id==computer.companyId}">selected="selected"</c:if>  value="${company.id}">${company.name}</option>
                               		</c:forEach>
                                </select>
                             
                            </div>            
                        </fieldset>
						<div class="actions pull-right">
                           	<input type="submit" value="<spring:message code="editComputer.button.edit"/>" class="btn btn-primary">
                        	<spring:message code="editComputer.or"/>
                          	<a href="dashboard" class="btn btn-default"><spring:message code="editComputer.button.cancel"/></a>
                        </div>                        
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>

