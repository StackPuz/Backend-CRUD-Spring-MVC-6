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
                    <div class="col-12"><input id="searchbar_toggle" type="checkbox" />
                        <div id="searchbar" class="form-row mb-4">
                            <div class="form-group col-lg-2">
                                <select id="search_col" onchange="searchChange()" class="form-control form-control-sm">
                                    <option value="id" data-type="number" ${param.sc=='id' ? 'selected' : '' }>Order Header Id</option>
                                    <option value="customer.name" ${param.sc=='customer.name' ? 'selected' : '' }>Customer Name</option>
                                    <option value="orderDate" data-type="date" ${param.sc=='orderDate' ? 'selected' : '' }>Order Header Order Date</option>
                                </select>
                            </div>
                            <div class="form-group col-lg-2">
                                <select id="search_oper" class="form-control form-control-sm">
                                    <option value="c" ${param.so=='c' ? 'selected' : '' }>Contains</option>
                                    <option value="e" ${param.so=='e' ? 'selected' : '' }>Equals</option>
                                    <option value="g" ${param.so=='g' ? 'selected' : '' }>&gt;</option>
                                    <option value="ge" ${param.so=='ge' ? 'selected' : '' }>&gt;&#x3D;</option>
                                    <option value="l" ${param.so=='l' ? 'selected' : '' }>&lt;</option>
                                    <option value="le" ${param.so=='le' ? 'selected' : '' }>&lt;&#x3D;</option>
                                </select>
                            </div>
                            <div class="form-group col-lg-2">
                                <input id="search_word" autocomplete="off" onkeyup="search(event)" value="${param.sw}" class="form-control form-control-sm" />
                            </div>
                            <div class="col">
                                <button class="btn btn-primary btn-sm" onclick="search()">Search</button>
                                <button class="btn btn-secondary btn-sm" onclick="clearSearch()">Clear</button>
                            </div>
                        </div>
                        <table class="table table-sm table-striped table-hover">
                            <thead>
                                <tr>
                                    <th class="${util.getSortClass('id','asc')}"><a href="${util.getLink('sort',paging,'id','asc')}">Id</a></th>
                                    <th class="${util.getSortClass('customer.name')} d-none d-md-table-cell"><a href="${util.getLink('sort',paging,'customer.name')}">Customer</a></th>
                                    <th class="${util.getSortClass('orderDate')}"><a href="${util.getLink('sort',paging,'orderDate')}">Order Date</a></th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="orderHeader" items="${orderHeaders}">
                                    <tr>
                                        <td class="text-center">${orderHeader.id}</td>
                                        <td class="d-none d-md-table-cell">${orderHeader.customer.name}</td>
                                        <td class="text-center">${util.formatDate(orderHeader.orderDate)}</td>
                                        <td class="text-center">
                                            <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/orderHeaders/${orderHeader.id}" title="View"><i class="fa fa-eye"></i></a>
                                            <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/orderHeaders/delete/${orderHeader.id}" title="Delete"><i class="fa fa-times"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="row mb-1">
                            <div class="col-md-3 col-6">
                                <label>Show
                                    <select id="page_size" onchange="location = this.value">
                                        <option value="${util.getLink('size',paging,10)}" ${param.size=='10' ? 'selected' : '' }>10</option>
                                        <option value="${util.getLink('size',paging,20)}" ${param.size=='20' ? 'selected' : '' }>20</option>
                                        <option value="${util.getLink('size',paging,30)}" ${param.size=='30' ? 'selected' : '' }>30</option>
                                    </select>
                                    entries
                                </label>
                            </div>
                            <div class="col-md-9 col-6">
                                <div class="float-right d-none d-md-block">
                                    <ul class="pagination flex-wrap">
                                        <li class="page-item${paging.current <= 1 ? ' disabled' : ''}"><a class="page-link" href="${util.getLink('page',paging,paging.current-1)}">Prev</a></li>
                                        <c:forEach begin="1" end="${paging.last}" varStatus="page">
                                        <li class="page-item${paging.current == page.index ? ' active' : ''}"><a class="page-link" href="${util.getLink('page',paging,page.index)}">${page.index}</a></li>
                                        </c:forEach>
                                        <li class="page-item${paging.current >= paging.last ? ' disabled' : ''}"><a class="page-link" href="${util.getLink('page',paging,paging.current+1)}">Next</a></li>
                                    </ul>
                                </div>
                                <div class="float-right d-block d-md-none">
                                    <label> Page
                                        <select id="page_index" onchange="location = this.value">
                                            <c:forEach begin="1" end="${paging.last}" varStatus="page">
                                            <option value="${util.getLink('page',paging,page.index)}" ${paging.current==page.index ? 'selected' : '' }>${page.index}</option>
                                            </c:forEach>
                                        </select>
                                    </label> of <span>${paging.last}</span>
                                    <div class="btn-group">
                                        <a class="btn btn-primary btn-sm${paging.current <= 1 ? ' disabled' : ''}" href="${util.getLink('page',paging,paging.current-1)}"><i class="fa fa-chevron-left"></i></a>
                                        <a class="btn btn-primary btn-sm${paging.current >= paging.last ? ' disabled' : ''}" href="${util.getLink('page',paging,paging.current+1)}"><i class="fa fa-chevron-right"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/orderHeaders/create">Create</a>
                    </div>
                    <style>
                        #searchbar_toggle_menu { display: inline-flex!important }
                    </style>
                </div>
            </div>
        </div>
        <script>
            initPage()
        </script>
    </jsp:body>
</t:layout>