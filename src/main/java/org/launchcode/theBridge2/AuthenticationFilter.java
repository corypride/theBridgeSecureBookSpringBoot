package org.launchcode.theBridge2;

import org.launchcode.theBridge2.controllers.AuthenticationController;
import org.launchcode.theBridge2.data.UserRepository;
import org.launchcode.theBridge2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("/login",
            "/register","/logout", "/css", "/bootstrap-4.3.1", "/js", "/images");


    private static final List<String> aUList = Arrays.asList("/theBridge/nav2");


    private static boolean isAUListed(String path){
        for (String pathRoot: aUList){
            if(path.startsWith(pathRoot)){
                return true;
            }
        }
        return false;
    }


    private static boolean isWhiteListed (String path){
        for (String pathRoot: whitelist){
            if(path.startsWith(pathRoot)){
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {


        //don't require sign-in for whitelisted pages
        if(isWhiteListed(request.getRequestURI())){
            //returning true indicates that the request may proceed
            return true;
        }


        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);


        //The user is logged in
        if(user != null){

            //get user's account type
            int accountType = user.getAccountType();
            //0 = defaultUser;  1=adminUser;
            //check to see if the requested page is of AdminType and proceed
            // accordingly

            if((isAUListed(request.getRequestURI()))&& accountType == 0){
                    System.out.println("Should  be denied");
                response.sendRedirect("/denied");
                //response.sendRedirect("/login");
                return false;
            }
            return true;
        }

        //The user is not logged in
        response.sendRedirect("/login");
        return false;
    }

}


