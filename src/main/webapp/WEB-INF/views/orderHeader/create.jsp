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
                    <f:form modelAttribute="orderHeader" method="post" action="${pageContext.request.contextPath}/orderHeaders/create?ref=${util.encodeURL(ref)}">
                        <div class="row">
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_header_customer_id">Customer</label>
                                <select id="order_header_customer_id" name="customer.id" class="form-control form-control-sm" required>
                                    <c:forEach var="customer" items="${customers}">
                                        <option value="${customer.id}" ${orderHeader.customer.id==customer.id ? 'selected' : '' }>${customer.name}</option>
                                    </c:forEach>
                                </select>
                                <f:errors path="customer.id" cssClass="text-danger" />
                            </div>
                            <div class="form-group col-md-6 col-lg-4">
                                <label for="order_header_order_date">Order Date</label>
                                <input id="order_header_order_date" name="orderDate" class="form-control form-control-sm" value="${orderHeader.orderDate}" data-type="date" autocomplete="off" required />
                                <f:errors path="orderDate" cssClass="text-danger" />
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