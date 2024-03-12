package app.controller;

import app.util.Util;
import app.model.UserAccount;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Controller
@Transactional
public class LoginController {

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("login")
    public String Login(Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        return "authen/login";
    }

    @RequestMapping("resetPassword")
    public String ResetPassword()
    {
        return "authen/resetPassword";
    }

    @RequestMapping(value="resetPassword", method=RequestMethod.POST)
    public String ResetPasswordPost(Model model) throws Exception
    {
        String email = request.getParameter("email");
        UserAccount user = getUser("email", email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setPasswordResetToken(token);
            em.persist(user);
            Util.sentMail("reset", email, token);
            model.addAttribute("success", true);
            return "authen/resetPassword";
        }
        else {
            model.addAttribute("error", true);
            return "authen/resetPassword";
        }
    }
    
    @RequestMapping("changePassword/{token}")
    public String ChangePassword(@PathVariable String token, Model model)
    {
        UserAccount user = getUser("passwordResetToken", token);
        if (user != null) {
            model.addAttribute("token", token);
            return "authen/changePassword";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="changePassword/{token}", method=RequestMethod.POST)
    public String ChangePasswordPost(@PathVariable String token, Model model)
    {
        UserAccount user = getUser("passwordResetToken", token);
        if (user != null) {
            String password = request.getParameter("password");
            user.setPassword(passwordEncoder.encode(password));
            user.setPasswordResetToken(null);
            em.persist(user);
            model.addAttribute("success", true);
            return "authen/changePassword";
        }
        else {
            model.addAttribute("error", true);
            return "authen/changePassword";
        }
    }
    
    public UserAccount getUser(String key, String value) {
        List<UserAccount> users = em.createQuery("SELECT u FROM UserAccount u where u. " + key + " = :value" , UserAccount.class).setParameter("value", value).getResultList(); //getSingleResult() maybe throw exception
        return (users.size() > 0 ? users.get(0) : null);
    }
}