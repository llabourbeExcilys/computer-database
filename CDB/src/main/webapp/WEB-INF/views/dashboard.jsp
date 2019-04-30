<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>


<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                121 Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer.html">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    
                    <c:forEach var="computer" items="${computers}">
	                     <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="editComputer.html" onclick="">${computer.name}</a>
	                        </td>
	                        <td>${computer.ldIntroduced}</td>
	                        <td>${computer.ldDiscontinued}</td>
	                        <td>${computer.company.name}</td>
	                    </tr>
                    </c:forEach>
                
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
              <c:if test="${(page-1) > 0}">
            	 <li>
            	 	<a href="?page=${page-1}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                 	</a>
             	 </li>
              </c:if>
              <li><a href="?page=${page}">${page}</a></li>
              <c:if test="${page+1<=lastPage}"><li><a href="?page=${page+1}">${page+1}</a></li></c:if>
              <c:if test="${page+2<=lastPage}"><li><a href="?page=${page+2}">${page+2}</a></li></c:if>
              <c:if test="${page+3<=lastPage}"><li><a href="?page=${page+3}">${page+3}</a></li></c:if>
              <c:if test="${page+4<=lastPage}"><li><a href="?page=${page+4}">${page+4}</a></li></c:if>
              <c:if test="${page+1<=lastPage}">
              	<li>
	                <a href="?page=${page+1}" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
            	</li>
              </c:if>
              
        </ul>
        
        <ul class="pagination pull-right">
        	<li><a href="?nbByPage=10">10</a></li>
        	<li><a href="?nbByPage=50">50</a></li>
        	<li><a href="?nbByPage=100">100</a></li>
        </ul>
        
        
	</div>
    </footer>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>

</body>
</html>