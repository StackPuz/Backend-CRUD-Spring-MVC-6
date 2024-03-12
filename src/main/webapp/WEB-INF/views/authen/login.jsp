<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<jsp:useBean id="util" class="app.util.Util"></jsp:useBean>

<t:layout>
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="center-container">
                        <div class="row justify-content-center">
                            <div class="card card-width">
                                <div class="card-header">
                                    <h3>Login</h3>
                                </div>
                                <div class="card-body">
                                    <i class="login fa fa-user-circle"></i>
                                    <f:form modelAttribute="userAccount" method="post" action="${pageContext.request.contextPath}/login">
                                        <div class="row">
                                            <div class="form-group col-12">
                                                <label for="user_account_name">User Name</label>
                                                <input id="user_account_name" name="name" class="form-control form-control-sm" required maxlength="50" />
                                            </div>
                                            <div class="form-group col-12">
                                                <label for="user_account_password">Password</label>
                                                <input id="user_account_password" name="password" class="form-control form-control-sm" type="password" required maxlength="100" />
                                            </div>
                                            <div class="col-12">
                                                <button class="btn btn-sm btn-secondary btn-block">Login</button>
                                                <a href="${pageContext.request.contextPath}/resetPassword">Forgot Password?</a>
                                            </div>
                                        </div>
                                    </f:form>
                                    <c:if test="${param.error != null}"><span class="text-danger">${SPRING_SECURITY_LAST_EXCEPTION.message}</span></c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            initPage(true)
        </script>
    </jsp:body>
</t:layout>