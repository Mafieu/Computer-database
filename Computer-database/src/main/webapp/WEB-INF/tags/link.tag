<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="body" required="true" type="java.lang.String" description="Body of the link"%>
<%@ attribute name="target" required="true" type="java.lang.String" description="Servlet to request"%>
<%@ attribute name="page" required="true" type="java.lang.Integer" description="Page to request"%>
<%@ attribute name="limit" required="true" type="java.lang.Integer" description="Number of computer per page"%>
<%@ attribute name="active" required="true" type="java.lang.Boolean" description="Active link or not"%>
<%@ attribute name="btn" required="true" type="java.lang.Boolean" description="Button like or not"%>
<%@ attribute name="orderBy" type="java.lang.String" description="Order by ascending or descending"%>
<%@ attribute name="column" type="java.lang.String" description="Column to order on"%>

<c:choose>
	<c:when test="${active == true && btn == true}">
		<a class="btn btn-default" href="${target}?page=${page}&limit=${limit}&order=${orderBy}&column=${column}">${body}</a>
	</c:when>
	<c:when test="${active == false && btn == true}">
		<a class="btn btn-default disabled" href="${target}?page=${page}&limit=${limit}&order=${orderBy}&column=${column}">${body}</a>
	</c:when>
	<c:when test="${active == true && btn == false}">
		<a href="${target}?page=${page}&limit=${limit}&order=${orderBy}&column=${column}">${body}</a>
	</c:when>
	<c:otherwise>
		<a class="disabled" href="${target}?page=${page}&limit=${limit}&order=${orderBy}&column=${column}">${body}</a>
	</c:otherwise>
</c:choose>