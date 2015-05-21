<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="hashtag" tagdir="/WEB-INF/tags"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard"><spring:message
				code="application.title" /></a>
		<hashtag:flag id="${computer.id}" page="${page}" />
	</div>
</header>