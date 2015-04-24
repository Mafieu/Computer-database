<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>
                    <c:if test="${error}">
                    	<div class="alert alert-danger">You have an error</div>
                    </c:if>

                    <form action="editComputer" onsubmit="return checkValues()" method="POST">
                        <input type="hidden" name="computerId" value="${computer.id}"/>
                        <fieldset>
                            <div class="form-group" id="computerNameDiv">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" value="${computer.name}">
                            </div>
                            <div class="form-group" id="introducedDiv">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" value="${computer.introduced}">
                            </div>
                            <div class="form-group" id="discontinuedDiv">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="${computer.discontinued}">
                            </div>
                            <div class="form-group" id="companyDiv">
                                <label for="companyId">Company</label>
                                <select class="form-control" name="companyId" id="companyId">
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
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="js/jquery.min.js"></script>
    <script src="js/editComputer.js"></script>
</body>
</html>