<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
      		<a class="navbar-brand pull-left" href="dashboard?page=1&field=default&order=default"> Application - Computer Database </a>
           	<a class="navbar-brand pull-right" href="?lang=en">English</a>|
           	<p class="navbar-brand pull-right">|</p>
           	<a class="navbar-brand pull-right" href="?lang=fr">Français</a>
        </div>
    </header>
    
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${nbComputerFound} <spring:message code="dashboard.nbComputerFound"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="dashboard" method="GET" class="form-inline">
                    
                    
                    <spring:message code="dashboard.placeholder.searchName" var="searchNamePlaceholder"/>
                    <spring:message code="dashboard.placeholder.filterByName" var="filterByNamePlaceholder"/>
                    
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="${searchNamePlaceholder}" />
                        <input type="submit" id="searchsubmit" value="${filterByNamePlaceholder}"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="dashboard.button.addComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.button.editComputer"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="deleteComputer" method="POST">
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
                            <spring:message code="dashboard.column.computer.name" />
                            <a href="dashboard?field=name&order=asc">&#9650;</a>
                            <a href="dashboard?field=name&order=desc">&#9660;</a>
                        </th>
                        <th>
                            <spring:message code="dashboard.column.computer.introduction" />
                            <a href="dashboard?field=introDate&order=asc">&#9650;</a>
                            <a href="dashboard?field=introDate&order=desc">&#9660;</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="dashboard.column.computer.discontinuation" />
                            <a href="dashboard?field=disconDate&order=asc">&#9650;</a>
                            <a href="dashboard?field=disconDate&order=desc">&#9660;</a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="dashboard.column.computer.company" />
                            <a href="dashboard?field=company&order=asc">&#9650;</a>
                            <a href="dashboard?field=company&order=desc">&#9660;</a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    
                    <c:forEach var="computer" items="${computers}">
	                     <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="deleteComputer" class="cb" value="${computer.id}">
	                        </td>
	                        <td>
	                            <a href="editComputer?idToEdit=${computer.id}" onclick="">${computer.name}</a>
	                        </td>
	                        <td>${computer.ldIntroduced}</td>
	                        <td>${computer.ldDiscontinued}</td>
	                        <td>${computer.companyName}</td>
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
              <c:if test="${page+1<=lastPage}"><li><a href="dashboard?page=${page+1}">${page+1}</a></li></c:if>
              <c:if test="${page+2<=lastPage}"><li><a href="dashboard?page=${page+2}">${page+2}</a></li></c:if>
              <c:if test="${page+3<=lastPage}"><li><a href="dashboard?page=${page+3}">${page+3}</a></li></c:if>
              <c:if test="${page+4<=lastPage}"><li><a href="dashboard?page=${page+4}">${page+4}</a></li></c:if>
              <c:if test="${page+1<=lastPage}">
              	<li>
	                <a href="?page=${page+1}" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
            	</li>
              </c:if>
              
        </ul>
        
        <ul class="pagination pull-right">
        	<li><a href="dashboard?nbByPage=10&page=1">10</a></li>
        	<li><a href="dashboard?nbByPage=50&page=1">50</a></li>
        	<li><a href="dashboard?nbByPage=100&page=1">100</a></li>
        </ul>
        
        
	</div>
    </footer>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>

</body>
</html>