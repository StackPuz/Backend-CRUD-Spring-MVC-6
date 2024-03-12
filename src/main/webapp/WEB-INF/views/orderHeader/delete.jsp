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
                    <f:form modelAttribute="orderHeader" method="post" action="${pageContext.request.contextPath}/orderHeaders/delete?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_header_id">Id</label>
                                <input readonly id="order_header_id" name="id" class="form-control form-control-sm" value="${orderHeader.id}" type="number" required />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="customer_name">Customer</label>
                                <input readonly id="customer_name" name="customer_name" class="form-control form-control-sm" value="${orderHeader.customer.name}" maxlength="50" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_header_order_date">Order Date</label>
                                <input readonly id="order_header_order_date" name="orderDate" class="form-control form-control-sm" value="${util.formatDate(orderHeader.orderDate)}" data-type="date" autocomplete="off" required />
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