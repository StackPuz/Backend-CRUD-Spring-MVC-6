package app.controller;

import app.util.Util;
import app.model.*;
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
@RequestMapping("/brands")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class BrandController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public String Index(Model model) {
        if (Util.isInvalidSearch(Arrays.asList(new String[] { "id", "name" }), request.getParameter("sc"))) {
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
            search = Util.getParameterValue(types, request.getParameter("sw"), request.getParameter("sc"), operator);
        }
        String where = (search != null ? String.format("WHERE b.%s %s ?1", request.getParameter("sc"), operator) : "");
        Query countQuery = em.createQuery(String.format("SELECT COUNT(*) FROM Brand b %s", where));
        TypedQuery<Brand> selectQuery = em.createQuery(String.format("SELECT b FROM Brand b %s ORDER BY %s %s", where, sort, sortDirection), Brand.class);
        if (search != null) {
            countQuery.setParameter(1, search);
            selectQuery.setParameter(1, search);
        }
        int count = Integer.parseInt(countQuery.getSingleResult().toString());
        int last = (int)Math.ceil(count / (double)size);
        selectQuery.setFirstResult((page - 1) * size); 
        selectQuery.setMaxResults(size);
        List<Brand> brands = selectQuery.getResultList();
        Map<String, Integer> paging = new HashMap<String, Integer>();
        paging.put("current", page);
        paging.put("size", size);
        paging.put("last", last);
        model.addAttribute("paging", paging);
        model.addAttribute("brands", brands);
        return "brand/index";
    }

    @RequestMapping("/{id}")
    public String Detail(@PathVariable Integer id, Model model) {
        Brand brand = em.find(Brand.class, id);
        List<Product> brandProducts = em.createQuery("SELECT product FROM Brand brand join brand.products product WHERE brand.id = :id", Product.class).setParameter("id", id).getResultList();
        model.addAttribute("brandProducts", brandProducts);
        model.addAttribute("brand", brand);
        model.addAttribute("ref", Util.getRef("/brands"));
        return "brand/detail";
    }

    @RequestMapping("/create")
    public String Create(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("ref", Util.getRef("/brands"));
        return "brand/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String Create(@Valid @ModelAttribute("brand") Brand item, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            model.addAttribute("ref", Util.getRef("/brands"));
            return "brand/create";
        }
        em.persist(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/brands" : request.getParameter("ref"));
    }

    @RequestMapping("/edit/{id}")
    public String Edit(@PathVariable Integer id, Model model) {
        Brand brand = em.find(Brand.class, id);
        model.addAttribute("brand", brand);
        model.addAttribute("ref", Util.getRef("/brands"));
        return "brand/edit";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String Update(@ModelAttribute("brand") Brand brand, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            model.addAttribute("ref", Util.getRef("/brands"));
            return "brand/edit";
        }
        Brand item = em.find(Brand.class, brand.getId());
        item.setName(brand.getName());
        em.persist(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/brands" : request.getParameter("ref"));
    }

    @RequestMapping("/delete/{id}")
    public String Delete(@PathVariable Integer id, Model model) {
        Brand brand = em.find(Brand.class, id);
        model.addAttribute("brand", brand);
        model.addAttribute("ref", Util.getRef("/brands"));
        return "brand/delete";
    }
    
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String Delete(@ModelAttribute("brand") Brand brand) {
        Brand item = em.find(Brand.class, brand.getId());
        em.remove(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/brands" : request.getParameter("ref"));
    }  
}