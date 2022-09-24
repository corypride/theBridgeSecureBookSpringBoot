package org.launchcode.theBridge2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*TODO user this form to handle all of the bridgeStuff*/
@Controller
@RequestMapping("theBridge/nav/")
public class TheBridgeDUController {


    /*TODO finish formatting this form on the mathWhiz1.html, use the form in
       the html to collect answers to the math game, and to collect info for the game config.*/


    @RequestMapping(value = "", method ={RequestMethod.GET, RequestMethod.POST})
    public String displayDefaultUserPage(HttpServletRequest request){
        return "site/theBridgeDU/theBridgeDU";
    }



    @GetMapping("libraryLinks")
    public String displayLibraryLinksIframe(Model model){
        model.addAttribute("isGradesButtonReq", false);
        model.addAttribute("studentAssignmentsDU", false);
        model.addAttribute("rptType","linksPg");
        return "site/theBridgeDU/theBridgeDU";
        //return "site/libraryLinks";
    }

    @GetMapping("mathWhiz")
    public String displayMathWhizLinksIframe(){

        return "site/theBridgeDU/mathWhiz1";
    }


    @GetMapping("grades")
    public String displayGradesIframe(){

        return "site/theBridgeDU/grades";
    }



}
