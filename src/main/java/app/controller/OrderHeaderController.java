package app.controller;

import app.util.Util;
import app.model.*;
import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@Transactional
@RequestMapping("/orderHeaders")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class OrderHeaderController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public String Index(Model model) {
        if (Util.isInvalidSearch(Arrays.asList(new String[] { "id", "customer.name", "orderDate" }), request.getParameter("sc"))) {
            throw new AccessDeniedException("");
        }
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int size = request.getParameter("size") != null ? Integer.parseInt(request.getParameter("size")) : 10;
        String sort = request.getParameter("sort") != null ? request.getParameter("sort") : "id";
        String sortDirection = request.getParameter("sort") != null ? (request.getParameter("desc") != null ? "desc" : "asc") : "asc";
        String operator = Util.getOperator(request.getParameter("so"));
        Object search = null;
        if (request.getParameter("sw") != null) {
            Map<String, Class<?>> types = new HashMap<String, Class<?>>();
            types.put("id", Integer.class);
            types.put("orderDate", Date.class);
            search = Util.getParameterValue(types, request.getParameter("sw"), request.getParameter("sc"), operator);
        }
        String where = (search != null ? String.format("WHERE o.%s %s ?1", request.getParameter("sc"), operator) : "");
        Query countQuery = em.createQuery(String.format("SELECT COUNT(*) FROM OrderHeader o %s", where));
        TypedQuery<OrderHeader> selectQuery = em.createQuery(String.format("SELECT o FROM OrderHeader o %s ORDER BY %s %s", where, sort, sortDirection), OrderHeader.class);
        if (search != null) {
            countQuery.setParameter(1, search);
            selectQuery.setParameter(1, search);
        }
        int count = Integer.parseInt(countQuery.getSingleResult().toString());
        int last = (int)Math.ceil(count / (double)size);
        selectQuery.setFirstResult((page - 1) * size); 
        selectQuery.setMaxResults(size);
        List<OrderHeader> orderHeaders = selectQuery.getResultList();
        Map<String, Integer> paging = new HashMap<String, Integer>();
        paging.put("current", page);
        paging.put("size", size);
        paging.put("last", last);
        model.addAttribute("paging", paging);
        model.addAttribute("orderHeaders", orderHeaders);
        return "orderHeader/index";
    }

    @RequestMapping("/{id}")
    public String Detail(@PathVariable Integer id, Model model) {
        OrderHeader orderHeader = em.find(OrderHeader.class, id);
        List<OrderDetail> orderHeaderOrderDetails = em.createQuery("SELECT orderDetail FROM OrderHeader orderHeader join orderHeader.orderDetails orderDetail WHERE orderHeader.id = :id", OrderDetail.class).setParameter("id", id).getResultList();
        model.addAttribute("orderHeaderOrderDetails", orderHeaderOrderDetails);
        model.addAttribute("orderHeader", orderHeader);
        model.addAttribute("ref", Util.getRef("/orderHeaders"));
        return "orderHeader/detail";
    }

    @RequestMapping("/create")
    public String Create(Model model) {
        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        model.addAttribute("customers", customers);
        model.addAttribute("orderHeader", new OrderHeader());
        model.addAttribute("ref", Util.getRef("/orderHeaders"));
        return "orderHeader/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String Create(@Valid @ModelAttribute("orderHeader") OrderHeader item, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            model.addAttribute("customers", customers);
            model.addAttribute("ref", Util.getRef("/orderHeaders"));
            return "orderHeader/create";
        }
        em.persist(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/orderHeaders" : request.getParameter("ref"));
    }

    @RequestMapping("/edit/{id}")
    public String Edit(@PathVariable Integer id, Model model) {
        OrderHeader orderHeader = em.find(OrderHeader.class, id);
        List<OrderDetail> orderHeaderOrderDetails = em.createQuery("SELECT orderDetail FROM OrderHeader orderHeader join orderHeader.orderDetails orderDetail WHERE orderHeader.id = :id", OrderDetail.class).setParameter("id", id).getResultList();
        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        model.addAttribute("orderHeaderOrderDetails", orderHeaderOrderDetails);
        model.addAttribute("customers", customers);
        model.addAttribute("orderHeader", orderHeader);
        model.addAttribute("ref", Util.getRef("/orderHeaders"));
        return "orderHeader/edit";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String Update(@ModelAttribute("orderHeader") OrderHeader orderHeader, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            List<OrderDetail> orderHeaderOrderDetails = em.createQuery("SELECT orderDetail FROM OrderHeader orderHeader join orderHeader.orderDetails orderDetail WHERE orderHeader.id = :id", OrderDetail.class).setParameter("id", orderHeader.getId()).getResultList();
            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            model.addAttribute("orderHeaderOrderDetails", orderHeaderOrderDetails);
            model.addAttribute("customers", customers);
            model.addAttribute("ref", Util.getRef("/orderHeaders"));
            return "orderHeader/edit";
        }
        OrderHeader item = em.find(OrderHeader.class, orderHeader.getId());
        item.setCustomer(orderHeader.getCustomer());
        item.setOrderDate(orderHeader.getOrderDate());
        em.persist(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/orderHeaders" : request.getParameter("ref"));
    }

    @RequestMapping("/delete/{id}")
    public String Delete(@PathVariable Integer id, Model model) {
        OrderHeader orderHeader = em.find(OrderHeader.class, id);
        List<OrderDetail> orderHeaderOrderDetails = em.createQuery("SELECT orderDetail FROM OrderHeader orderHeader join orderHeader.orderDetails orderDetail WHERE orderHeader.id = :id", OrderDetail.class).setParameter("id", id).getResultList();
        model.addAttribute("orderHeaderOrderDetails", orderHeaderOrderDetails);
        model.addAttribute("orderHeader", orderHeader);
        model.addAttribute("ref", Util.getRef("/orderHeaders"));
        return "orderHeader/delete";
    }
    
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String Delete(@ModelAttribute("orderHeader") OrderHeader orderHeader) {
        OrderHeader item = em.find(OrderHeader.class, orderHeader.getId());
        em.remove(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/orderHeaders" : request.getParameter("ref"));
    }  
}