package org.launchcode.theBridge2.controllers;

import org.launchcode.theBridge2.data.UserRepository;
import org.launchcode.theBridge2.models.User;
import org.launchcode.theBridge2.models.dto.LoginFormDTO;
import org.launchcode.theBridge2.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";
    private static final String userAccountTypeKey ="accountType";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model){
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title","Register");
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFromDTO, Errors errors,
                                          HttpServletRequest request, Model model){

        if(errors.hasErrors()){
            model.addAttribute("title", "Register");
            return "register";
        }

        User existingUser =
                userRepository.findByLastNameAndFirstNameAndMiddleNameAndSuffix(registerFromDTO.getLastName(),registerFromDTO.getFirstName(),registerFromDTO.getMiddleName(),registerFromDTO.getSuffix());
        if(existingUser != null){
            errors.rejectValue("username", "username.alreadyExists", "A user " +
                    "with that username already exists");
            model.addAttribute("title", "Register");
            return  "register";
        }

        String password = registerFromDTO.getPassword();
        String verifyPassword = registerFromDTO.getVerifyPassword();

        if(!password.equals(verifyPassword)){
            errors.rejectValue("password","passwords.mismatch", "Passwords do " +
                    "not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        User newUser = new User(registerFromDTO.getFirstName(),
                registerFromDTO.getLastName(), registerFromDTO.getMiddleName(),
                registerFromDTO.getSuffix(), registerFromDTO.getInmateNumber(),
                registerFromDTO.getPassword(),registerFromDTO.getEmail(),
                registerFromDTO.getAccountType(), registerFromDTO.getUsername());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        //find the new users idNum and pass it to the relationshipReqForm
        List<User> users = (List<User>) userRepository.findAll();
        int newUserId =
                userRepository.findByLastNameAndFirstNameAndMiddleNameAndSuffix(registerFromDTO.getLastName(),registerFromDTO.getFirstName(),registerFromDTO.getMiddleName(),registerFromDTO.getSuffix()).getId();

        return "redirect:/theBridge/messenger/relationshipRequest?senderId="+newUserId;
    }



    @GetMapping("/login")
    public String displayLoginForm(Model model){
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log in");
        return "login";
    }


    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO ldto,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()){
            model.addAttribute("title", "Log In");
            return "login";
        }

        User theUser = userRepository.findByUsername(ldto.getUsername());

        if(theUser == null){
            errors.rejectValue("username", "user.invalid", "The given username" +
                    " does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = ldto.getPassword();
        if(!theUser.isMatchingPassword(password)){
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";

        }

        setUserInSession(request.getSession(), theUser);

        //TODO eventually instead of being sent to theBridgeDU or AU, after
        // registration a defult user will be routed to the relationships page
        // where they will be able to add a student to their account. Also while
        // at the moment the account type is being checked against a hard
        // value '1' this will change once the relationship to a table is
        // established. Then the account type will be checked with the pk
        // value of the account type

        if(theUser.getAccountType() == 1){
            return "redirect:/theBridge/nav2/";
        }
        return "redirect:/theBridge/";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/denied")
    public String displayDeniedAccess(Model model){


        return "denied";
    }



}
