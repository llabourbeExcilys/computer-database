<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
                    <h1>Add Computer</h1>
					<form:form method="POST" action="addComputer" modelAttribute="ComputerForm">
                            <div class="form-group">
								<form:label for="computerName" path="name">Name</form:label>
								<form:input class="form-control" id="computerName" path="name" placeholder="${placeHolderName}"/>
                            </div>
                            <div class="form-group">
								<form:label for="introduced" path="ldIntroduced">Introduction date</form:label>
								<form:input type="date" class="form-control" id="introduced" path="ldIntroduced"/>                            
                            </div>
                            <div class="form-group">
								<form:label for="discontinued" path="ldDiscontinued">Discontinuation date</form:label>
								<form:input type="date" class="form-control" id="discontinued" path="ldDiscontinued"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
								<form:select class="form-control" id="companyId" path="companyId">
									<form:option value="" label="--Select Company"/>
	              						<form:options items="${companies}" itemValue="id" itemLabel="name"/>
								</form:select>                            
                            </div>
							<div class="actions pull-right">
                           		<input type="submit" value="Add" class="btn btn-primary">
                          		or
                          		<a href="dashboard" class="btn btn-default">Cancel</a>
                        	</div>
					</form:form>
				</div>
            </div>
        </div>
    </section>
</body>
</html>

