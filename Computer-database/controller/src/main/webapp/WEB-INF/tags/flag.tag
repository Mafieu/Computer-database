<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hashtag"%>
<%@ attribute name="id" required="false" type="java.lang.Integer"
	description="id for edit page"%>
<%@ attribute name="page" required="false"
	type="com.excilys.malbert.controller.model.Page"
	description="page object for dashboard"%>

<div class="navbar-right">
	<c:choose>
		<c:when test="${empty id && !empty page}">
			<hashtag:link column="${page.column}" limit="${page.limit}"
				target="dashboard" page="${page.page}" orderBy="${page.order}"
				search="${page.search}" lang="en" body='<img src="img/flag_en.jpg">'></hashtag:link>
			<hashtag:link column="${page.column}" limit="${page.limit}"
				target="dashboard" page="${page.page}" orderBy="${page.order}"
				search="${page.search}" lang="fr" body='<img src="img/flag_fr.jpg">'></hashtag:link>
		</c:when>
		<c:when test="${empty page && !empty id}">
			<a href="editComputer?id=${id}&lang=en"><img
				src="img/flag_en.jpg" alt="English"
				style="width: 35px; height: 30px"></a>
			<a href="editComputer?id=${id}&lang=fr"><img
				src="img/flag_fr.jpg" alt="Français"
				style="width: 35px; height: 30px"></a>
		</c:when>
		<c:otherwise>
			<a href="addComputer?lang=en"><img src="img/flag_en.jpg"
				alt="English" style="width: 35px; height: 30px"></a>
			<a href="addComputer?lang=fr"><img src="img/flag_fr.jpg"
				alt="Français" style="width: 35px; height: 30px"></a>
		</c:otherwise>
	</c:choose>
	<form action="logout" method="post">
		<input class="btn btn-primary" type="submit" value="Logout">
	</form>
</div>