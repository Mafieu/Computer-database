<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="hashtag" tagdir="/WEB-INF/tags" %>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${page.totalCount} Computers found
            </h1>
            <c:if test="${error}">
            	<div class="alert alert-danger alert-dismissible" role="alert">
            		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            		${errorMessage}
            	</div>
            </c:if>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="dashboard" method="GET" class="form-inline">
                        <input name="page" hidden="true" value="1"/>
                        <input name="limit" hidden="true" value="${page.countPerPage}"/>
                        <input name="order" hidden="true" value="${page.order}"/>
                        <input name="column" hidden="true" value="${page.column}"/>
                        <c:choose>
                        	<c:when test="${page.isSearchValid()}">
                        		<input type="search" id="searchbox" name="search" class="form-control" value="${page.search}"/>
                        	</c:when>
                        	<c:otherwise>
                        		<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name"/>
                        	</c:otherwise>
                       </c:choose>
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
                        	<c:choose>
                        		<c:when test="${page.column == 'computer.name' && page.order == 'asc'}">
		                            <hashtag:link body="Computer name
		                            <span class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="desc" column="computer.name" search="${page.search}" target="dashboard"></hashtag:link>
	                            </c:when>
	                            <c:otherwise>
	                            	<hashtag:link body="Computer name
		                            <span class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="asc" column="computer.name" search="${page.search}" target="dashboard"></hashtag:link>
	                            </c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                        	<c:choose>
                        		<c:when test="${page.column == 'introduced' && page.order == 'asc'}">
		                        	<hashtag:link body="Introduced date
		                            <span class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="desc" column="introduced" search="${page.search}" target="dashboard"></hashtag:link>
		                        </c:when>
		                        <c:otherwise>
		                        	<hashtag:link body="Introduced date
		                            <span class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="asc" column="introduced" search="${page.search}" target="dashboard"></hashtag:link>
		                    	</c:otherwise>
		                    </c:choose>
                        </th>
                        <th>
                        	<c:choose>
                        		<c:when test="${page.column == 'discontinued' && page.order == 'asc'}">
		                        	<hashtag:link body="Discontinued date
		                            <span class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="desc" column="discontinued" search="${page.search}" target="dashboard"></hashtag:link>
                        		</c:when>
                        		<c:otherwise>
                        			<hashtag:link body="Discontinued date
		                            <span class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="asc" column="discontinued" search="${page.search}" target="dashboard"></hashtag:link>
                        		</c:otherwise>
                        	</c:choose>
                        </th>
                        <th>
                        	<c:choose>
                        		<c:when test="${page.column == 'company.name' && page.order == 'asc'}">
		                        	<hashtag:link body="Company
		                            <span class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="desc" column="company.name" search="${page.search}" target="dashboard"></hashtag:link>
								</c:when>
								<c:otherwise>
									<hashtag:link body="Company
		                            <span class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span>" limit="${page.countPerPage}" page="${page.page}" orderBy="asc" column="company.name" search="${page.search}" target="dashboard"></hashtag:link>
								</c:otherwise>
                          	</c:choose>
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    <c:forEach items="${page.data}" var="computer">
                    	<tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="editComputer?id=${computer.id}" onclick="">${computer.name}</a>
                        </td>
                        <td>
                        	<c:if test="${computer.introduced != null}">${computer.introduced}</c:if>
                        </td>
                        <td>
                        	<c:if test="${computer.discontinued != null}">${computer.discontinued}</c:if>
                        </td>
                        <td>
                        	<c:if test="${computer.companyName != null}">${computer.companyName}</c:if>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <hashtag:pagination currentPage="${page.page}" limit="${page.countPerPage}" size="${page.totalCount}" orderBy="${page.order}" column="${page.column}" search="${page.search}"></hashtag:pagination>
    </footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>