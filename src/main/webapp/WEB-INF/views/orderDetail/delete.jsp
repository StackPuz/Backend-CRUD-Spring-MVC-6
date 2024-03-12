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
                    <f:form modelAttribute="orderDetail" method="post" action="${pageContext.request.contextPath}/orderDetails/delete?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_order_id">Order Id</label>
                                <input readonly id="order_detail_order_id" name="id.orderId" class="form-control form-control-sm" value="${orderDetail.id.orderId}" type="number" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_no">No</label>
                                <input readonly id="order_detail_no" name="id.no" class="form-control form-control-sm" value="${orderDetail.id.no}" type="number" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="product_name">Product</label>
                                <input readonly id="product_name" name="product_name" class="form-control form-control-sm" value="${orderDetail.product.name}" maxlength="50" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_detail_qty">Qty</label>
                                <input readonly id="order_detail_qty" name="qty" class="form-control form-control-sm" value="${orderDetail.qty}" type="number" required />
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