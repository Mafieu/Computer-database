<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="body" required="true" type="java.lang.String" description="Body of the link"%>
<%@ attribute name="target" required="true" type="java.lang.String" description="Servlet to request"%>
<%@ attribute name="page" required="true" type="java.lang.Integer" description="Page to request"%>
<%@ attribute name="limit" required="true" type="java.lang.Integer" description="Number of computer per page"%>
<%@ attribute name="classes" type="java.lang.String" description="Classes to apply at the link"%>
<%@ attribute name="orderBy" required="true" type="java.lang.String" description="Order by ascending or descending"%>
<%@ attribute name="column" required="true" type="java.lang.String" description="Column to order on"%>
<%@ attribute name="search" required="true" type="java.lang.String" description="Pattern to search"%>

<a class="${classes}" href="${target}?page=${page}&limit=${limit}&order=${orderBy}&column=${column}&search=${search}">${body}</a>