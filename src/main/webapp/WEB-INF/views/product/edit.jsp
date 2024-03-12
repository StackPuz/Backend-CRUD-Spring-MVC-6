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
                    <f:form modelAttribute="product" method="post" action="${pageContext.request.contextPath}/products/edit?ref=${util.encodeURL(ref)}" enctype="multipart/form-data">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_id">Id</label>
                                <input readonly id="product_id" name="id" class="form-control form-control-sm" value="${product.id}" type="number" required />
                                <f:errors path="id" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_name">Name</label>
                                <input id="product_name" name="name" class="form-control form-control-sm" value="${product.name}" required maxlength="50" />
                                <f:errors path="name" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_price">Price</label>
                                <input id="product_price" name="price" class="form-control form-control-sm" value="${product.price}" type="number" step="0.1" required />
                                <f:errors path="price" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_brand_id">Brand</label>
                                <select id="product_brand_id" name="brand.id" class="form-control form-control-sm" required>
                                    <c:forEach var="brand" items="${brands}">
                                        <option value="${brand.id}" ${product.brand.id==brand.id ? 'selected' : '' }>${brand.name}</option>
                                    </c:forEach>
                                </select>
                                <f:errors path="brand.id" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_image">Image</label>
                                <input type="file" id="product_image" name="imageFile" class="form-control form-control-sm" maxlength="50" />
                                <a href="${pageContext.request.contextPath}/uploads/products/${product.image}" target="_blank" title="${product.image}"><img class="img-item" src="${pageContext.request.contextPath}/uploads/products/${product.image}" /></a>
                                <f:errors path="image" cssClass="text-danger" />
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