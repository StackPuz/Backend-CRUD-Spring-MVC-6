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
                    <f:form modelAttribute="userAccount" method="post" action="${pageContext.request.contextPath}/userAccounts/create?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="user_account_name">Name</label>
                                <input id="user_account_name" name="name" class="form-control form-control-sm" value="${userAccount.name}" required maxlength="50" />
                                <f:errors path="name" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="user_account_email">Email</label>
                                <input id="user_account_email" name="email" class="form-control form-control-sm" value="${userAccount.email}" type="email" required maxlength="50" />
                                <f:errors path="email" cssClass="text-danger" />
                            </div>
                            <div class="form-check col-md-6 col-lg-4">
                                <input id="user_account_active" name="active" class="form-check-input" type="checkbox" value="1" ${userAccount.active ? 'checked' : '' } />
                                <label class="form-check-label" for="user_account_active">Active</label>
                                <f:errors path="active" cssClass="text-danger" />
                            </div>
                            <div class="col-12">
                                <h6>
                                </h6><label>Roles</label>
                                <c:forEach var="role" items="${roles}">
                                    <div class="form-check">
                                        <input id="user_role_role_id${role.id}" name="roleId" class="form-check-input" type="checkbox" value="${role.id}" />
                                        <label class="form-check-label" for="user_role_role_id${role.id}">${role.name}</label>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="col-12">
                                <a class="btn btn-sm btn-secondary" href="${ref}">Cancel</a>
                                <button class="btn btn-sm btn-primary">Submit</button>
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