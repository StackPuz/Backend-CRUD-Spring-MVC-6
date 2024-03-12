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
                    <f:form modelAttribute="userAccount" method="post">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="user_account_id">Id</label>
                                <input readonly id="user_account_id" name="id" class="form-control form-control-sm" value="${userAccount.id}" type="number" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="user_account_name">Name</label>
                                <input readonly id="user_account_name" name="name" class="form-control form-control-sm" value="${userAccount.name}" required maxlength="50" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="user_account_email">Email</label>
                                <input readonly id="user_account_email" name="email" class="form-control form-control-sm" value="${userAccount.email}" type="email" required maxlength="50" />
                            </div>
                            <div class="form-check col-md-6 col-lg-4">
                                <input readonly id="user_account_active" name="active" class="form-check-input" type="checkbox" value="1" ${userAccount.active ? 'checked' : '' } />
                                <label class="form-check-label" for="user_account_active">Active</label>
                            </div>
                            <div class="col-12">
                                <h6>Roles</h6>
                                <table class="table table-sm table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>Role Name</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="userAccountUserRole" items="${userAccountUserRoles}">
                                            <tr>
                                                <td>${userAccountUserRole.role.name}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-12">
                                <a class="btn btn-sm btn-secondary" href="${ref}">Back</a>
                                <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/userAccounts/edit/${userAccount.id}?ref=${util.encodeURL(ref)}">Edit</a>
                            </div>
                        </div>
                    </f:form>
                </div>
            </div>
        </div>
        <script>
            initPage(true)
        </script>
    </jsp:body>
</t:layout>