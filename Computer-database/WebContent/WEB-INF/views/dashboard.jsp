<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<%@page import="com.excilys.malbert.persistence.model.Computer"%>
<%@page import="java.util.List"%>
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
	<%
	int size = (int) request.getAttribute("numberComputer");
	List<Computer> list = (List<Computer>) request.getAttribute("listComputer");
	int currentPage = (int) request.getAttribute("page");
	int limit = (int) request.getAttribute("limit");
	%>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <% out.print(size); %> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
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

        <form id="deleteForm" action="#" method="POST">
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
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <th>
                            Discontinued date
                        </th>
                        <th>
                            Company
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    <%
                    for(Computer computer : list){
                    %>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer?id=<% out.print(computer.getId()); %>" onclick=""><% out.print(computer.getName()); %></a>
                        </td>
                        <td><%
                        	if(computer.getIntroduced() != null) {
                            	out.print(computer.getIntroduced().toLocalDate());
                        	} %>
                        </td>
                        <td><% 
                        	if(computer.getDiscontinued() != null){
                            	out.print(computer.getDiscontinued().toLocalDate());
                            	} %>
                        </td>
                        <td><%
                        	if(computer.getCompany() != null) {
                            	out.print(computer.getCompany().getName());
                            	} %>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            	<li>
                    <a href="dashboard?page=<%
                    if(currentPage > 1) {
                    	out.print(currentPage - 1);
                    } else {
                		out.print(currentPage);
                    }
                    %>" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
              	<% 
              	int i = 0;
              	for(; i < size / limit; ++i) {
              	%>
              		<li><a href="dashboard?page=<% out.print(i+1); %>"><% out.print(i+1); %></a></li>
              	<%
              	}
              	if(size % limit != 0) {
              	    %>
              	    <li><a href="dashboard?page=<% out.print(i+1); %>"><% out.print(i+1); %></a></li>
              	    <%
              	}
              	%>
              	<li>
                	<a href="dashboard?page=<%
                	if(currentPage < size) {
                	out.print(currentPage + 1);
                	} else {
                	    out.print(currentPage);
                	}
                	%>" aria-label="Next">
                    	<span aria-hidden="true">&raquo;</span>
                	</a>
            	</li>
        	</ul>

        	<div class="btn-group btn-group-sm pull-right" role="group" >
            	<!-- <a class="btn btn-default" href="dashboard?limit=10">10</a> -->
            	<a class="btn btn-default" href="dashboard?limit=50">50</a>
            	<a class="btn btn-default" href="dashboard?limit=100">100</a>
        	</div>
		</div>
    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>