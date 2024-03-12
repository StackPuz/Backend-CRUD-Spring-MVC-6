package app.controller;

import app.util.Util;
import app.model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@Transactional
@RequestMapping("/userAccounts")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserAccountController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public String Index(Model model) {
        if (Util.isInvalidSearch(Arrays.asList(new String[] { "id", "name", "email", "active" }), request.getParameter("sc"))) {
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
            types.put("active", Boolean.class);
            search = Util.getParameterValue(types, request.getParameter("sw"), request.getParameter("sc"), operator);
        }
        String where = (search != null ? String.format("WHERE u.%s %s ?1", request.getParameter("sc"), operator) : "");
        Query countQuery = em.createQuery(String.format("SELECT COUNT(*) FROM UserAccount u %s", where));
        TypedQuery<UserAccount> selectQuery = em.createQuery(String.format("SELECT u FROM UserAccount u %s ORDER BY %s %s", where, sort, sortDirection), UserAccount.class);
        if (search != null) {
            countQuery.setParameter(1, search);
            selectQuery.setParameter(1, search);
        }
        int count = Integer.parseInt(countQuery.getSingleResult().toString());
        int last = (int)Math.ceil(count / (double)size);
        selectQuery.setFirstResult((page - 1) * size); 
        selectQuery.setMaxResults(size);
        List<UserAccount> userAccounts = selectQuery.getResultList();
        Map<String, Integer> paging = new HashMap<String, Integer>();
        paging.put("current", page);
        paging.put("size", size);
        paging.put("last", last);
        model.addAttribute("paging", paging);
        model.addAttribute("userAccounts", userAccounts);
        return "userAccount/index";
    }

    @RequestMapping("/{id}")
    public String Detail(@PathVariable Integer id, Model model) {
        UserAccount userAccount = em.find(UserAccount.class, id);
        List<UserRole> userAccountUserRoles = em.createQuery("SELECT userRole FROM UserAccount userAccount join userAccount.userRoles userRole WHERE userAccount.id = :id", UserRole.class).setParameter("id", id).getResultList();
        model.addAttribute("userAccountUserRoles", userAccountUserRoles);
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("ref", Util.getRef("/userAccounts"));
        return "userAccount/detail";
    }

    @RequestMapping("/create")
    public String Create(Model model) {
        List<Role> roles = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        model.addAttribute("roles", roles);
        model.addAttribute("userAccount", new UserAccount());
        model.addAttribute("ref", Util.getRef("/userAccounts"));
        return "userAccount/create";
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String Create(@Valid @ModelAttribute("userAccount") UserAccount item, BindingResult result, Model model) throws Exception { 
        if (result.hasErrors()) {
            List<Role> roles = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
            model.addAttribute("roles", roles);
            model.addAttribute("ref", Util.getRef("/userAccounts"));
            return "userAccount/create";
        }
        if (item.getActive() == null) {
            item.setActive(false);
        }
        String token = UUID.randomUUID().toString();
        item.setPasswordResetToken(token);
        item.setPassword(passwordEncoder.encode(UUID.randomUUID().toString().substring(0, 10)));
        em.persist(item);
        String[] roles = request.getParameterValues("roleId");
        if (roles != null) {
            for (String role:roles) {
                UserRole userRole = new UserRole();
                UserRolePK userRolePK = new UserRolePK();
                userRolePK.setUserId(item.getId());
                userRolePK.setRoleId(Integer.valueOf(role));
                userRole.setId(userRolePK);
                em.persist(userRole);
            }
        }
        Util.sentMail("welcome", item.getEmail(), token, item.getName());
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/userAccounts" : request.getParameter("ref"));
    }

    @RequestMapping("/edit/{id}")
    public String Edit(@PathVariable Integer id, Model model) {
        UserAccount userAccount = em.find(UserAccount.class, id);
        List<UserRole> userAccountUserRoles = em.createQuery("SELECT userRole FROM UserAccount userAccount join userAccount.userRoles userRole WHERE userAccount.id = :id", UserRole.class).setParameter("id", id).getResultList();
        List<Object> userAccountUserRolesList = new ArrayList<Object>();
        for (UserRole item:userAccountUserRoles) {
            userAccountUserRolesList.add(item.getId().getRoleId());
        }
        List<Role> roles = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        model.addAttribute("userAccountUserRoles", userAccountUserRoles);
        model.addAttribute("userAccountUserRolesList", userAccountUserRolesList);
        model.addAttribute("roles", roles);
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("ref", Util.getRef("/userAccounts"));
        return "userAccount/edit";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String Update(@ModelAttribute("userAccount") UserAccount userAccount, BindingResult result, Model model) { 
        if (result.hasErrors()) {
            List<UserRole> userAccountUserRoles = em.createQuery("SELECT userRole FROM UserAccount userAccount join userAccount.userRoles userRole WHERE userAccount.id = :id", UserRole.class).setParameter("id", userAccount.getId()).getResultList();
            List<Object> userAccountUserRolesList = new ArrayList<Object>();
            for (UserRole item:userAccountUserRoles) {
                userAccountUserRolesList.add(item.getId().getRoleId());
            }
            List<Role> roles = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
            model.addAttribute("userAccountUserRoles", userAccountUserRoles);
            model.addAttribute("userAccountUserRolesList", userAccountUserRolesList);
            model.addAttribute("roles", roles);
            model.addAttribute("ref", Util.getRef("/userAccounts"));
            return "userAccount/edit";
        }
        UserAccount item = em.find(UserAccount.class, userAccount.getId());
        item.setName(userAccount.getName());
        item.setEmail(userAccount.getEmail());
        if (userAccount.getPassword() != null && !userAccount.getPassword().isEmpty()) {
            item.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        }
        item.setActive(userAccount.getActive());
        if (item.getActive() == null) {
            item.setActive(false);
        }
        em.persist(item);
        em.createQuery("DELETE FROM UserRole userRole WHERE userRole.id.userId = :userId").setParameter("userId", userAccount.getId()).executeUpdate();
        String[] roles = request.getParameterValues("roleId");
        if (roles != null) {
            for (String role:roles) {
                UserRole userRole = new UserRole();
                UserRolePK userRolePK = new UserRolePK();
                userRolePK.setUserId(userAccount.getId());
                userRolePK.setRoleId(Integer.valueOf(role));
                userRole.setId(userRolePK);
                em.persist(userRole);
            }
        }
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/userAccounts" : request.getParameter("ref"));
    }

    @RequestMapping("/delete/{id}")
    public String Delete(@PathVariable Integer id, Model model) {
        UserAccount userAccount = em.find(UserAccount.class, id);
        List<UserRole> userAccountUserRoles = em.createQuery("SELECT userRole FROM UserAccount userAccount join userAccount.userRoles userRole WHERE userAccount.id = :id", UserRole.class).setParameter("id", id).getResultList();
        model.addAttribute("userAccountUserRoles", userAccountUserRoles);
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("ref", Util.getRef("/userAccounts"));
        return "userAccount/delete";
    }
    
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String Delete(@ModelAttribute("userAccount") UserAccount userAccount) {
        UserAccount item = em.find(UserAccount.class, userAccount.getId());
        em.remove(item);
        return "redirect:" + (request.getParameter("ref").startsWith(request.getContextPath()) ? "/userAccounts" : request.getParameter("ref"));
    }  
}