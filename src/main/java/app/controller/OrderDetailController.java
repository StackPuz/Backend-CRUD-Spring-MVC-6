package app.controller;

import app.util.Util;
import app.model.*;
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
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@Transactional
@RequestMapping("/orderDetails")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class OrderDetailController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/create")
    public String Create(Model model) {
        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        model.addAttribute("products", products);
        model.addAttribute("orderDetail", new OrderDetail());
        model.addAttribute("ref", Util.getRef("/orderDetails"));
        return "orderDetail/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String Create(@Valid @ModelAttribute("orderDetail") OrderDetail item, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
            model.addAttribute("products", products);
            model.addAttribute("ref", Util.getRef("/orderDetails"));
            return "orderDetail/create";
        }
        em.persist(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/orderDetails" : request.getParameter("ref"));
    }

    @RequestMapping("/edit/{orderId}/{no}")
    public String Edit(@PathVariable Integer orderId, @PathVariable Short no, Model model) {
        OrderDetail orderDetail = em.find(OrderDetail.class, new OrderDetailPK(orderId, no));
        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        model.addAttribute("products", products);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("ref", Util.getRef("/orderDetails"));
        return "orderDetail/edit";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String Update(@ModelAttribute("orderDetail") OrderDetail orderDetail, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
            model.addAttribute("products", products);
            model.addAttribute("ref", Util.getRef("/orderDetails"));
            return "orderDetail/edit";
        }
        OrderDetail item = em.find(OrderDetail.class, orderDetail.getId());
        item.setProduct(orderDetail.getProduct());
        item.setQty(orderDetail.getQty());
        em.persist(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/orderDetails" : request.getParameter("ref"));
    }

    @RequestMapping("/delete/{orderId}/{no}")
    public String Delete(@PathVariable Integer orderId, @PathVariable Short no, Model model) {
        OrderDetail orderDetail = em.find(OrderDetail.class, new OrderDetailPK(orderId, no));
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("ref", Util.getRef("/orderDetails"));
        return "orderDetail/delete";
    }
    
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String Delete(@ModelAttribute("orderDetail") OrderDetail orderDetail) {
        OrderDetail item = em.find(OrderDetail.class, orderDetail.getId());
        em.remove(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/orderDetails" : request.getParameter("ref"));
    }  
}