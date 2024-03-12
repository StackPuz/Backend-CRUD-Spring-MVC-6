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
                    <f:form modelAttribute="product" method="post" action="${pageContext.request.contextPath}/products/delete?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_id">Id</label>
                                <input readonly id="product_id" name="id" class="form-control form-control-sm" value="${product.id}" type="number" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_name">Name</label>
                                <input readonly id="product_name" name="name" class="form-control form-control-sm" value="${product.name}" required maxlength="50" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_price">Price</label>
                                <input readonly id="product_price" name="price" class="form-control form-control-sm" value="${product.price}" type="number" step="0.1" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="brand_name">Brand</label>
                                <input readonly id="brand_name" name="brand_name" class="form-control form-control-sm" value="${product.brand.name}" maxlength="50" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="user_account_name">Create User</label>
                                <input readonly id="user_account_name" name="user_account_name" class="form-control form-control-sm" value="${product.userAccount.name}" maxlength="50" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4"><label>Image</label>
                                <div><a href="${pageContext.request.contextPath}/uploads/products/${product.image}" target="_blank" title="${product.image}"><img class="img-item" src="${pageContext.request.contextPath}/uploads/products/${product.image}" /></a></div>
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