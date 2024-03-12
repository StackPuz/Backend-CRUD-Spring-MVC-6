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
                    <f:form modelAttribute="brand" method="post" action="${pageContext.request.contextPath}/brands/delete?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="brand_id">Id</label>
                                <input readonly id="brand_id" name="id" class="form-control form-control-sm" value="${brand.id}" type="number" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="brand_name">Name</label>
                                <input readonly id="brand_name" name="name" class="form-control form-control-sm" value="${brand.name}" required maxlength="50" />
                            </div>
                            <div class="col-12">
                                <a class="btn btn-sm btn-secondary" href="${ref}">Cancel</a>
                                <button class="btn btn-sm btn-danger">Delete</button>
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