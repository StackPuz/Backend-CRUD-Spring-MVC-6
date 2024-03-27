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
                    <f:form modelAttribute="brand" method="post" action="${pageContext.request.contextPath}/brands/edit?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <input type="hidden" id="brand_id" name="id" value="${brand.id}" />
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="brand_name">Name</label>
                                <input id="brand_name" name="name" class="form-control form-control-sm" value="${brand.name}" required maxlength="50" />
                                <f:errors path="name" cssClass="text-danger" />
                            </div>
                            <div class="col-12">
                                <h6>Brand's products</h6>
                                <table class="table table-sm table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>Product Name</th>
                                            <th>Price</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="brandProduct" items="${brandProducts}">
                                            <tr>
                                                <td>${brandProduct.name}</td>
                                                <td class="text-right">${brandProduct.price}</td>
                                                <td class="text-center">
                                                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/products/${brandProduct.id}" title="View"><i class="fa fa-eye"></i></a>
                                                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/products/edit/${brandProduct.id}" title="Edit"><i class="fa fa-pencil"></i></a>
                                                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/products/delete/${brandProduct.id}" title="Delete"><i class="fa fa-times"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/products/create?product_brand_id=${brand.id}">Add</a>
                                <hr />
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