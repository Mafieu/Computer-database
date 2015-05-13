<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="hashtag" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message
					code="application.title" /></a>
			<!-- Imgs not loaded -->
			<div class="navbar-right">
				<hashtag:link column="${page.column}" limit="${page.limit}"
					target="dashboard" page="${page.page}" orderBy="${page.order}"
					search="${page.search}" lang="fr"
					body='<img src="img/flag_fr.jpg">'></hashtag:link>
				<hashtag:link column="${page.column}" limit="${page.limit}"
					target="dashboard" page="${page.page}" orderBy="${page.order}"
					search="${page.search}" lang="en"
					body='<img src="img/flag_en.jpg">'></hashtag:link>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				${page.totalCount}
				<spring:message code="dashboard.numberComputer" />
			</h1>
			<c:if test="${error}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="glyphicon glyphicon-exclamation-sign"
						aria-hidden="true"></span> ${errorMessage}
				</div>
			</c:if>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET"
						class="form-inline">
						<input name="page" hidden="true" value="1" /> <input name="limit"
							hidden="true" value="${page.limit}" /> <input name="order"
							hidden="true" value="${page.order}" /> <input name="column"
							hidden="true" value="${page.column}" />
						<c:choose>
							<c:when test="${page.isSearchValid()}">
								<input type="search" id="searchbox" name="search"
									class="form-control" value="${page.search}" />
							</c:when>
							<c:otherwise>
								<input type="search" id="searchbox" name="search"
									class="form-control"
									placeholder=<spring:message code="dashboard.placeholder.search"/> />
							</c:otherwise>
						</c:choose>
						<input type="submit" id="searchsubmit"
							value=<spring:message code="dashboard.button.search"/>
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
							code="dashboard.button.add" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="dashboard.button.edit" /></a>
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
						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><c:choose>
								<c:when
									test="${page.column == 'computer.name' && page.order == 'asc'}">
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=desc&column=computer.name&search=${page.search}"><spring:message
											code='dashboard.name' /> <span
										class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span></a>
								</c:when>
								<c:otherwise>
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=asc&column=computer.name&search=${page.search}"><spring:message
											code='dashboard.name' /> <span
										class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span></a>
								</c:otherwise>
							</c:choose></th>
						<th><c:choose>
								<c:when
									test="${page.column == 'introduced' && page.order == 'asc'}">
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=desc&column=introduced&search=${page.search}"><spring:message
											code='dashboard.introduced' /> <span
										class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span></a>
								</c:when>
								<c:otherwise>
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=asc&column=introduced&search=${page.search}"><spring:message
											code='dashboard.introduced' /> <span
										class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span></a>
								</c:otherwise>
							</c:choose></th>
						<th><c:choose>
								<c:when
									test="${page.column == 'discontinued' && page.order == 'asc'}">
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=desc&column=discontinued&search=${page.search}"><spring:message
											code='dashboard.discontinued' /> <span
										class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span></a>
								</c:when>
								<c:otherwise>
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=asc&column=discontinued&search=${page.search}"><spring:message
											code='dashboard.discontinued' /> <span
										class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span></a>
								</c:otherwise>
							</c:choose></th>
						<th><c:choose>
								<c:when
									test="${page.column == 'company.name' && page.order == 'asc'}">
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=desc&column=company.name&search=${page.search}"><spring:message
											code='dashboard.company' /> <span
										class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span></a>
								</c:when>
								<c:otherwise>
									<a
										href="dashboard?page=${page.page}&limit=${page.limit}&order=asc&column=company.name&search=${page.search}"><spring:message
											code='dashboard.company' /> <span
										class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span></a>
								</c:otherwise>
							</c:choose></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.data}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editComputer?id=${computer.id}" onclick="">${computer.name}</a>
							</td>
							<td><c:if test="${computer.introduced != null}">${computer.introduced}</c:if>
							</td>
							<td><c:if test="${computer.discontinued != null}">${computer.discontinued}</c:if>
							</td>
							<td><c:if test="${computer.companyName != null}">${computer.companyName}</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<hashtag:pagination currentPage="${page.page}" limit="${page.limit}"
			size="${page.totalCount}" orderBy="${page.order}"
			column="${page.column}" search="${page.search}"></hashtag:pagination>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.cookie.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>