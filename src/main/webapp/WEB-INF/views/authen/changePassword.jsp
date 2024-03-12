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
                                    <h3>Change Password</h3>
                                </div>
                                <div class="card-body">
                                    <i class="login fa fa-user-circle"></i>
                                    <f:form modelAttribute="userAccount" method="post" action="${pageContext.request.contextPath}/changePassword/${token}" onsubmit="return validateForm()">
                                        <div class="row">
                                            <div class="form-group col-12">
                                                <label for="user_account_password">Password</label>
                                                <input id="user_account_password" name="password" class="form-control form-control-sm" type="password" required maxlength="100" />
                                            </div>
                                            <div class="form-group col-12">
                                                <label for="user_account_password2">Confirm password</label>
                                                <input data-match="user_account_password" id="user_account_password2" name="password2" class="form-control form-control-sm" type="password" required maxlength="100" />
                                            </div>
                                            <div class="col-12">
                                                <button class="btn btn-sm btn-secondary btn-block">Change Password</button>
                                            </div>
                                        </div>
                                    </f:form>
                                    <c:if test="${success}"><span class="text-success">Change password done</span></c:if>
                                    <c:if test="${error}"><span class="text-danger">Token not found!</span></c:if>
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