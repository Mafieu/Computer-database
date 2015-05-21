<!DOCTYPE html>
<%@ page pageEncoding="UTF-8"%>
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
			<a class="navbar-brand" href="login"><spring:message
					code="application.title" /></a>
			<!-- Imgs not loaded -->
			<div class="navbar-right">
				<hashtag:link target="login" lang="fr"
					body='<img src="img/flag_fr.jpg">'></hashtag:link>
				<hashtag:link target="login" lang="en"
					body='<img src="img/flag_en.jpg">'></hashtag:link>
			</div>
		</div>
	</header>

	<h1>Spring Security Custom Login Form (XML)</h1>
	<div id="login-box">
		<h2>Login with Username and Password</h2>
		<form name='loginForm'
			action="<c:url value='j_spring_security_check' />" method='POST'>
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="submit" /></td>
				</tr>
			</table>
		</form>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.cookie.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>