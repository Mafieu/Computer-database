<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<c:import url="header.jsp"></c:import>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computerDTO.id}</div>
					<h1><spring:message code="editComputer.title" /></h1>
					<c:if test="${error}">
						<div class="alert alert-danger">You have an error</div>
					</c:if>

					<form:form modelAttribute="computerDTO" method="POST" action="editComputer" onsubmit="return checkValues()">
						<form:input path="id" hidden="true" value="${computer.id}" />
						<fieldset>
							<div class="form-group" id="computerNameDiv">
								<label for="computerName"><spring:message
										code="addComputer.name" /></label>
								<form:input type="text" class="form-control" path="name"
									value="${computer.name}" />
							</div>
							<div class="text-center form-group"><form:errors class="alert alert-danger" path="name"/></div>
							<div class="form-group" id="introducedDiv">
								<label for="introduced"><spring:message
										code="addComputer.introduced" /></label>
								<form:input type="date" class="form-control" path="introduced"
									value="${computerDTO.introduced}" />
							</div>
							<div class="text-center form-group"><form:errors class="alert alert-danger" path="introduced"/></div>
							<div class="form-group" id="discontinuedDiv">
								<label for="discontinued"><spring:message
										code="addComputer.discontinued" /></label>
								<form:input type="date" class="form-control" path="discontinued"
									value="${computer.discontinued}" />
							</div>
							<div class="text-center form-group"><form:errors class="alert alert-danger" path="discontinued"/></div>
							<div class="form-group" id="companyDiv">
								<label for="companyId"><spring:message
										code="addComputer.company" /></label>
								<form:select class="form-control" path="companyId">
									<!-- Actual value -->
									<c:if test="${computer.companyName != null}">
										<option value="${computer.companyId}">${computer.companyName}</option>
									</c:if>
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<c:if test="${company.id != computer.companyId}">
											<option value="${company.id}">${company.name}</option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>
							<div class="text-center form-group"><form:errors class="alert alert-danger" path="companyId"/></div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code='addComputer.button.add' />"
								class="btn btn-primary">
							<spring:message code="addComputer.or" />
							<a href="dashboard" class="btn btn-default"><spring:message
									code="addComputer.button.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.cookie.js"></script>
	<script src="js/moment.min.js"></script>
	<script src="js/editComputer.js"></script>
</body>
</html>