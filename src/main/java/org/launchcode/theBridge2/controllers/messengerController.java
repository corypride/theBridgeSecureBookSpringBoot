package org.launchcode.theBridge2.controllers;

import org.launchcode.theBridge2.data.GradeLevelRepository;
import org.launchcode.theBridge2.data.MailboxRepository;
import org.launchcode.theBridge2.data.SchoolRepository;
import org.launchcode.theBridge2.data.UserRepository;
import org.launchcode.theBridge2.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("theBridge/messenger/")
public class messengerController {

    @Autowired
    public MailboxRepository mailboxRepository;
    @Autowired
    public AuthenticationController authenticationController;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SchoolRepository schoolRepository;
    @Autowired
    public GradeLevelRepository gradeLevelRepository;

    @PostMapping("test")
    @ResponseBody
    public String testCompose(@RequestParam() String dateSent ,
                              @RequestParam String sender,
                              @RequestParam String recipient,
                              @RequestParam String subject,
                              @RequestParam String message){

        String html = "<html>" +
                "<body>" +
                dateSent + "<br/>" +
                sender + "<br/>" +
                recipient + "<br/>" +
                subject + "<br/>" +
                message + "<br/>" +
                "</body>" +
                "</html>";
        return html;

    }

    /**/
    @GetMapping(value="")
    public String displayMessenger(Model model){
        return "site/messenger/messenger";
    }


    @GetMapping("compose")
    public String displayMessageComposer(HttpServletRequest request, Model model) throws IOException {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("mailbox",new Mailbox());
        List<User> recipients = new ArrayList<>();
        for(User u: userRepository.findAll()){
            if(!(u.getId() == user.getId())){
                recipients.add(u);
            }
        }
        model.addAttribute("recipients",recipients);
        model.addAttribute("sender",user);
        return "site/messenger/compose";
    }

    //handler for composed message
    @PostMapping("compose")
    public String composeMessage(@ModelAttribute @Valid Mailbox message,
                                 Errors err, @RequestParam int senderId,
                                 @RequestParam int recipientId,
                                 @RequestParam boolean isNew,
                                 Model model){
        if (err.hasErrors()){
            //todo: handle any errors that may occur
//            List<User> recipients = new ArrayList<>();
//            for(User u: userRepository.findAll()){
//                if(!(u.getId() == user.getId())){
//                    recipients.add(u);
//                }
//            }
           // model.addAttribute("recipients",recipients);
        return "site/messenger/compose";
        }
        User sender = userRepository.findById(senderId).get();
        User recipient = userRepository.findById(recipientId).get();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setNew(isNew);

        mailboxRepository.save(message);
        return "redirect:/theBridge/messenger/sentMessages/";
    }

    @GetMapping("inboxDelete")
    public String deleteInboxMessage(@RequestParam int messageId, Model model){
        mailboxRepository.deleteById(messageId);
        return "redirect:/theBridge/messenger/inbox";
    }

    @GetMapping("sentMessagesDelete")
    public String deleteSentMessage(@RequestParam int messageId, Model model){
        mailboxRepository.deleteById(messageId);
        return "redirect:/theBridge/messenger/sentMessages";
    }



    @GetMapping("inbox")
    public String displayInbox(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<Mailbox> inbox = new ArrayList<>();
        List<Mailbox> sentMessages = new ArrayList<>();


        for(Mailbox message: mailboxRepository.findAll()){
             if (message.getRecipient().getId() == user.getId()){
                inbox.add(message);
            }
        }

        model.addAttribute("messages", inbox);
//        return "site/messenger/inboxReport";
        return "site/messenger/messenger";
    }

    @GetMapping("sentMessages")
    public String displaySentMessages(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<Mailbox> sentMessages = new ArrayList<>();

        for(Mailbox message: mailboxRepository.findAll()){
            if(message.getSender().getId() == user.getId()){
                sentMessages.add(message);
            }
        }
        model.addAttribute("sentMessages", sentMessages);
        return "site/messenger/messenger";
    }

    @GetMapping("/relationshipRequest")
    public String displayRelationshipRequest(@RequestParam int senderId,
                                             Model model){

        model.addAttribute("schools",schoolRepository.findAll());
        model.addAttribute("gradeLevels", gradeLevelRepository.findAll());
        model.addAttribute("senderId", senderId);
        return "relationshipReq";
    }

    @PostMapping("/relationshipRequest")
    public String processRelationshipRequest(@RequestParam int senderId,
                                             @RequestParam String lastName,
                                             @RequestParam String firstName,
                                             @RequestParam(required = false) String middleName, @RequestParam String suffix,
                                             @RequestParam String dob,
                                             @RequestParam int schoolId,  @RequestParam int gradeLevelId,
                                             @RequestParam(required = false) String bridgeStudentID,
                                             Model model){

        User user = userRepository.findById(senderId).get();
        School school = schoolRepository.findById(schoolId).get();
        GradeLevel gradeLevel = gradeLevelRepository.findById(gradeLevelId).get();
        User siteAdmin = new User();

        for(User u: userRepository.findAll()){
            if(u.isSiteAdmin()){
                siteAdmin = u;
            }
        }

        String today= GetTodaysDate.getDate();
        String subject =
                "Relationship Request for: "+ user.getDisplayName() + " UserId:" +
                        " " +
                        " "+ user.getId();
        if(!bridgeStudentID.isBlank()){
            subject += " BridgeStudentId: " + bridgeStudentID;
        } else{
            subject += " BridgeStudentId: N/A";
        }

        String message = "User, "+user.getDisplayName2() +
                "is requesting that a Bridge-link" +
                " be setup for the child "+ firstName +" " + (middleName.isBlank()?"":middleName)+ " "+ lastName + " "+ suffix +
                ". The child's information is as follows:  Full Name: "+ firstName +", " + (middleName.isBlank()?"":middleName+" ")+ lastName + " "+ suffix+", "
                + "Date of Birth: "+ dob + ", School: "+ school.getSchoolName() + ", Grade Level: "+ gradeLevel.getLevel()+". " +
                " ---- Thank you for your time.";

        Mailbox m = new Mailbox(today,user, siteAdmin,
                subject,message);

        mailboxRepository.save(m);

        return "redirect:/login";
    }


    @GetMapping("siteMessage")
    public String displayStudentSiteManagerMessage (HttpServletRequest request
            , @RequestParam String messageType,@RequestParam(required = false) String subject,
                                                    @RequestParam(required =
                                                            false) String message,
                                                    @RequestParam(required =
                                                            false) Integer recipientId,
                                      @RequestParam boolean isMIReq, Model model
                                                    ){
        HttpSession session = request.getSession();
        Optional<User> sender =
                Optional.ofNullable(authenticationController.getUserFromSession(session));


        User recipient = new User();
        for(User u: userRepository.findAll()){
            if(u.isSiteAdmin()){
                recipient = u;
            }
        }

        String today= GetTodaysDate.getDate();
        String aSubject = subject;
        String aMessage = message;


        if(messageType.equals("registerStudent")){
            aSubject = "Account Error"  ;
            aMessage =
                    "No students are registered to "+sender.get().getUsername() +
                    "'s account. Take look at it at your earliest convenience";
        } else if (messageType.equals("rosterError")){
            aSubject = "No students assigned to "+ subject;
            aMessage = sender.get().getUsername() + " would like for you to check " +
                    "the "+ subject +
            " roster to see if there is an error. There are no students " +
                    "assigned to the class.";
        } else if (messageType.equals("bridgeLinkUpdate")){
            recipient = userRepository.findById(recipientId).get();
        }

        Mailbox m = new Mailbox(today,sender.get(), recipient,
                aSubject,aMessage);
        mailboxRepository.save(m);

        if(messageType.equals("bridgeLinkUpdate")){
            return "redirect:/myInterface/appendRelationship";
        }
        else if(isMIReq){
            return "redirect:/myInterface/";
        } else if (messageType.equals("registerStudent")){
            return "redirect:/theBridge/nav/";
        }
        return "redirect:/theBridge/nav2/";
    }

}
