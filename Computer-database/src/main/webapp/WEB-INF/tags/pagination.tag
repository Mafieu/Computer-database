<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="hashtag" tagdir="/WEB-INF/tags" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" description="Current page"%>
<%@ attribute name="limit" required="true" type="java.lang.Integer" description="Number of computers per page"%>
<%@ attribute name="size" required="true" type="java.lang.Integer" description="Number of computers"%>
<%@ attribute name="orderBy" type="java.lang.String" description="Order by ascending or descending"%>
<%@ attribute name="column" type="java.lang.String" description="Column to order on"%>
<div class="container text-center">
	<ul class="pagination">
		<c:choose>
			<c:when test="${currentPage > 1}">
				<li>
					<hashtag:link body="<span aria-hidden='true'>&laquo;</span>" limit="${limit}" page="${currentPage - 1}" orderBy="${orderBy}" column="${column}" target="dashboard"/>
				</li>
			</c:when>
			<c:otherwise>
				<li class="disabled">
					<hashtag:link body="<span aria-hidden='true'>&laquo;</span>" limit="${limit}" page="${currentPage - 1}" orderBy="${orderBy}" column="${column}" classes="disabled" target="dashboard"/>
				</li>
			</c:otherwise>
		</c:choose>
		<c:forEach var="i" begin="0" end="${size / limit}">
			<c:choose>
				<c:when test="${currentPage == i + 1}">
					<li class="active">
						<hashtag:link body="${i + 1}" limit="${limit}" page="${i + 1}" orderBy="${orderBy}" column="${column}" classes="disabled" target="dashboard"/>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<hashtag:link body="${i + 1}" limit="${limit}" page="${i + 1}" orderBy="${orderBy}" column="${column}" target="dashboard"/>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose> 
			<c:when test="${(currentPage * limit) < size}">
				<li>
					<hashtag:link body="<span aria-hidden='true'>&raquo;</span>" limit="${limit}" page="${currentPage + 1}" orderBy="${orderBy}" column="${column}" target="dashboard"/>
				</li>
			</c:when>
			<c:otherwise>
				<li class="disabled">
					<hashtag:link body="<span aria-hidden='true'>&raquo;</span>" limit="${limit}" page="${currentPage + 1}" orderBy="${orderBy}" column="${column}" classes="disabled" target="dashboard"/>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
	
	<div class="btn-group btn-group-sm pull-right" role="group" >
		<c:choose> 
			<c:when test="${limit == 10}">
				<hashtag:link body="10" limit="10" page="${currentPage}" orderBy="${orderBy}" column="${column}" classes="btn btn-default disabled" target="dashboard"/>
			</c:when>
			<c:otherwise>
				<hashtag:link body="10" limit="10" page="${currentPage}" orderBy="${orderBy}" column="${column}" classes="btn btn-default" target="dashboard"/>
			</c:otherwise>
		</c:choose>
		<c:choose> 
			<c:when test="${limit == 50}">
				<hashtag:link body="50" limit="50" page="${currentPage}" orderBy="${orderBy}" column="${column}" classes="btn btn-default disabled" target="dashboard"/>
			</c:when>
			<c:otherwise>
				<hashtag:link body="50" limit="50" page="${currentPage}" orderBy="${orderBy}" column="${column}" classes="btn btn-default" target="dashboard"/>
			</c:otherwise>
		</c:choose>
		<c:choose> 
			<c:when test="${limit == 100}">
				<hashtag:link body="100" limit="100" page="${currentPage}" orderBy="${orderBy}" column="${column}" classes="btn btn-default disabled" target="dashboard"/>
			</c:when>
			<c:otherwise>
				<hashtag:link body="100" limit="100" page="${currentPage}" orderBy="${orderBy}" column="${column}" classes="btn btn-default" target="dashboard"/>
			</c:otherwise>
		</c:choose>
	</div>
</div>