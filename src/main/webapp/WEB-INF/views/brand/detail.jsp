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
                    <f:form modelAttribute="brand" method="post">
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
                                <h6>Brand's products</h6>
                                <table class="table table-sm table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>Product Name</th>
                                            <th>Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="brandProduct" items="${brandProducts}">
                                            <tr>
                                                <td>${brandProduct.name}</td>
                                                <td class="text-right">${brandProduct.price}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-12">
                                <a class="btn btn-sm btn-secondary" href="${ref}">Back</a>
                                <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/brands/edit/${brand.id}?ref=${util.encodeURL(ref)}">Edit</a>
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