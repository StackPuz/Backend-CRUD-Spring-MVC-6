<%@tag pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<jsp:useBean id="util" class="app.util.Util"></jsp:useBean>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.13/flatpickr.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/style.css?1709695159309" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/inputmask@5.0.7/dist/inputmask.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.13/flatpickr.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/script.js?1709696408872"></script>
</head>
<body class="small">
    <s:authorize access="isAuthenticated()">
        <div class="wrapper">
            <input id="sidebar_toggle" type="checkbox" />
            <nav id="sidebar">
                <a href="/" class="bg-light border-bottom">
                    <h4>StackPuz</h4>
                </a>
                <ul class="list-unstyled">
                    <li>
                        <a href="${pageContext.request.contextPath}/home" class="${fn:endsWith(pageContext.request.requestURI,'home.jsp') ? 'active bg-primary' : ''}">Home</a>
                    </li>
                    <c:forEach var="menu" items="${util.getMenu()}">
                        <li><a href="${pageContext.request.contextPath}/${menu.path}" class="${menu.active ? 'active bg-primary' : ''}">${menu.title}</a></li>
                    </c:forEach>
                </ul>
            </nav>
            <div id="body">
                <nav class="navbar bg-light border-bottom">
                    <label for="sidebar_toggle" class=" btn btn-primary btn-sm"><i class="fa fa-bars"></i></label>
                    <ul class="navbar-nav ml-auto">
                        <li id="searchbar_toggle_menu" class="d-none">
                            <a class="nav-link text-secondary" href="#"><label for="searchbar_toggle" class="d-lg-none"><i class="fa fa-search"></i></label></a>
                        </li>
                        <li class="dropdown">
                            <a class="nav-link text-secondary dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-user"></i> <span class="d-none d-lg-inline">
                                    <s:authentication property="principal.username" />
                                </span></a>
                            <div class="dropdown-menu dropdown-menu-right">
                                <a href="${pageContext.request.contextPath}/profile" class="dropdown-item"><i class="fa fa-user"></i> Profile</a>
                                <a href="${pageContext.request.contextPath}/logout" class="dropdown-item"><i class="fa fa-sign-out"></i> Logout</a>
                            </div>
                        </li>
                    </ul>
                </nav>
                <div class="content">
                    <jsp:doBody />
                </div>
            </div>
        </div>
    </s:authorize>
    <s:authorize access="!isAuthenticated()">
        <jsp:doBody />
    </s:authorize>
</body>
</html>