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
                    <f:form modelAttribute="orderDetail" method="post" action="${pageContext.request.contextPath}/orderDetails/create?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_order_id">Order Id</label>
                                <input id="order_detail_order_id" name="id.orderId" class="form-control form-control-sm" value="${orderDetail.id.orderId}" type="number" required />
                                <f:errors path="id.orderId" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_no">No</label>
                                <input id="order_detail_no" name="id.no" class="form-control form-control-sm" value="${orderDetail.id.no}" type="number" required />
                                <f:errors path="id.no" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_product_id">Product</label>
                                <select id="order_detail_product_id" name="product.id" class="form-control form-control-sm" required>
                                    <c:forEach var="product" items="${products}">
                                        <option value="${product.id}" ${orderDetail.product.id==product.id ? 'selected' : '' }>${product.name}</option>
                                    </c:forEach>
                                </select>
                                <f:errors path="product.id" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_qty">Qty</label>
                                <input id="order_detail_qty" name="qty" class="form-control form-control-sm" value="${orderDetail.qty}" type="number" required />
                                <f:errors path="qty" cssClass="text-danger" />
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