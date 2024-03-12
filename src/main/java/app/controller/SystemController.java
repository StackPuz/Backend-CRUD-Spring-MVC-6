package app.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;
import app.model.UserAccount;
import app.util.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Controller
@Transactional
public class SystemController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/home")
    public String Home() {
        return "home";
    }

    @RequestMapping("/profile")
    public String Profile(Model model) {
        UserAccount userAccount = em.find(UserAccount.class, Util.getUser(em).getId());
        model.addAttribute("userAccount", userAccount);
        return "profile";
    }
    
    @RequestMapping(value="/updateProfile", method=RequestMethod.POST)
    public String UpdateProfile(@ModelAttribute("userAccount") UserAccount userAccount) { 
        UserAccount item = em.find(UserAccount.class, Util.getUser(em).getId());
        item.setName(userAccount.getName());
        item.setEmail(userAccount.getEmail());
        if (userAccount.getPassword() != null && !userAccount.getPassword().isEmpty()) {
            item.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        }
        em.persist(item);
        request.getSession().setAttribute("username", userAccount.getName());
        return "redirect:/home";
    }

    @ResponseBody
    @RequestMapping("/stack")
    public String Stack() {
        return "Spring MVC 6 + MySQL";
    }
}