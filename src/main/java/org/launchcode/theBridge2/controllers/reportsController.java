package org.launchcode.theBridge2.controllers;

import org.launchcode.theBridge2.data.*;
import org.launchcode.theBridge2.models.*;
import org.launchcode.theBridge2.models.Class;
import org.launchcode.theBridge2.models.comparator.FormNameComparator;
import org.launchcode.theBridge2.models.comparator.ReportNameComparator;
import org.launchcode.theBridge2.models.comparator.SchoolNmComparator;
import org.launchcode.theBridge2.models.comparator.StudentNmComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//TODO: Get rid of all comparators and use JPA QRYS

@Controller
@RequestMapping("theBridge/reports")
public class reportsController {


    @Autowired
    public AdministratorRepository administratorRepository;
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public SchoolRepository schoolRepository;
    @Autowired
    public CourseRepository courseRepository;
    @Autowired
    public ClassRepository classRepository;
    @Autowired
    public ReportNameRepository reportNameRepository;
    @Autowired
    public FormNameRepository formNameRepository;
    @Autowired
    public  ClassAssignmentRepository classAssignmentRepository;
    @Autowired
    public AuthenticationController authenticationController;
    @Autowired
    public UserRepository userRepository;

      /*TODO make up a variety of comparators and then make up a form that allows
        a user to sort or group a report based on this list of categories*/

    public List<School> getSortedSchools(){
        List <School> schools = (List<School>)  schoolRepository.findAll();
        schools.sort(new SchoolNmComparator());
        return schools;
    }

    public List<School> getSortedSchoolsNoNA(){
        List<School> schoolList = new ArrayList<>();

        for(School s : getSortedSchools()){
            if(!s.getSchoolName().toLowerCase().equals("n/a")){
                schoolList.add(s);
            }
        }
        return schoolList;
    }

    public List<Student> getSortedStudents(){
        List <Student> students = (List<Student>) studentRepository.findAll();
        students.sort(new StudentNmComparator());
        return students;
    }

    public  HashMap<String, String> getReportNames(){
        HashMap<String, String> output = new HashMap<>();
        //todo write code to sort this method and formName method
        for (ReportName r: reportNameRepository.findAll()){
            if(r.getNavPg2Order()>0){
                output.put(r.getDisplayName(),r.getRptName());
            }
        }
        return output;
    }

    public  HashMap<String, String> getFormNames(){
        HashMap<String, String> output = new HashMap<>();

        for (FormName f: formNameRepository.findAll()){
            if(f.getNavPg2Order()>0){
                output.put(f.getDisplayName(),f.getFormNm());
            }
        }
        return output;
    }

    public  HashMap<String, String> getAllReportNames(){
        HashMap<String, String> output = new HashMap<>();

        for (ReportName r: reportNameRepository.findAll()){
            output.put(r.getDisplayName(),r.getRptName());
        }
        return output;
    }

    public List<String> getSortedReportNames(){
        HashMap<String, String> data = getAllReportNames();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {
            result.add(nm);
        }
        result.sort(new ReportNameComparator());
        return result;
    }

    public  HashMap<String, String> getReportNamesForGB(){
        HashMap<String, String> output = new HashMap<>();

        for (ReportName r: reportNameRepository.findAll()){
            if(r.getNavPg2Order()>0){
                output.put(r.getDisplayName(),r.getRptName());
            }
        }
        return output;
    }
    public List<String> getSortedReportNamesForGB(){
        HashMap<String, String> data = getReportNamesForGB();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {
            result.add(nm);
        }
        result.sort(new ReportNameComparator());

        return result;
    }


    public  HashMap<String, String> getAllFormNames(){
        HashMap<String, String> output = new HashMap<>();
        for (FormName f: formNameRepository.findAll()){
            output.put(f.getDisplayName(),f.getFormNm());
        }
        return output;
    }


    public  HashMap<String, String> getFormNamesForGB(){
        HashMap<String, String> output = new HashMap<>();

        for (FormName f: formNameRepository.findAll()){
            if(f.getNavPg2Order()>0){
                output.put(f.getDisplayName(),f.getFormNm());
            }
        }
        return output;
    }

    public List<String> getSortedFormNamesForGB(){
        HashMap<String, String> data = getFormNamesForGB();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {

            result.add(nm);
        }
        result.sort(new FormNameComparator());
        return result;
    }

    public List<String> getSortedFormNames1(){
        HashMap<String, String> data = getAllFormNames();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {
            Integer anInt = Integer.parseInt(nm.substring(0,2).trim());
            if(anInt < 6) {
                result.add(nm);
            }
        }
        result.sort(new FormNameComparator());
        return result;
    }

    public List<String> getSortedFormNames2(){
        HashMap<String, String> data = getAllFormNames();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {
            Integer anInt = Integer.parseInt(nm.substring(0,2).trim());
            if(anInt > 5 && anInt <15) {
                result.add(nm);
            }
        }
        result.sort(new ReportNameComparator());
        return result;
    }

    public List<String> getSortedFormNames3(){
        HashMap<String, String> data = getAllFormNames();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {
            Integer anInt = Integer.parseInt(nm.substring(0,2).trim());
            if(anInt >=15) {
                result.add(nm);
            }
        }
        result.sort(new ReportNameComparator());
        return result;
    }

    //todo work on this method if you plan to use it
    @GetMapping
    public String displayReportsMain(Model model) {
        //todo: filter this list of reports to just show the reports that
        // default users should have access to
        List<ReportName> reports =
                (List<ReportName>) reportNameRepository.findAll();
        model.addAttribute("reports", reports);
        return "site/reports/reportsMain";
    }


    //All users report
    @GetMapping("allUsers")
    public String displayAllUsersReport(Model model){
        model.addAttribute("rptType", "allUsersRpt");
        model.addAttribute("adminUsers",
                userRepository.findByAccountTypeOrderByLastNameAsc(1));
        model.addAttribute("defaultUsers",
                userRepository.findByAccountTypeOrderByLastNameAsc(0));
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("isMIReq",true);
        return "site/theBridgeAU/myInterface";


    }


    //All administrators by school report
    @GetMapping(value = "admin")
    public String displayAllAdministratorsReport (@RequestParam boolean isMIReq,
                                                  Model model){

        model.addAttribute("rptType", "allAdminsRpt");
        model.addAttribute("schools", getSortedSchools());

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq",true);
            return "site/theBridgeAU/myInterface";
        }
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq",false);
        return "site/theBridgeAU/adminUserHome";
    }


    //an administrator by administrator.id
    @GetMapping("admin/{adminId}")
    public String displayAllAdministratorsReport (@PathVariable int adminId,
                                                  @RequestParam boolean isMIReq,
                                                  Model model){

        Administrator a = administratorRepository.findById(adminId).get();
        model.addAttribute("admins", a);
        model.addAttribute("rptType", "anAdminRpt");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
            model.addAttribute("isMIReq",true);
            //end of mandatory attributes for myInterface page
            return "site/theBridgeAU/myInterface";
        }
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq",false);
        return "site/theBridgeAU/adminUserHome";
    }

    //All administrators unemployed report
    @GetMapping(value = "adminsUnemployed")
    public String displayUnemployedAdministratorsReport (@RequestParam boolean isMIReq, Model model){

        model.addAttribute("rptType", "adminsUnemployed");
        //this is to get every school that has admins attached to them or not
        List<School> allSchools = (List<School>) getSortedSchools();
        //this is to get the admins that are not attached to any school
        List<School> filtered = new ArrayList<>();
        for (School s: allSchools){
            if(s.getSchoolName().equals("N/A")){
                filtered.add(s);
            }
        }
        model.addAttribute("schools", filtered);

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            model.addAttribute("isMIReq",true);
            //end of mandatory attributes for myInterface page
            return "site/theBridgeAU/myInterface";
        }


        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq",false);
        return "site/theBridgeAU/adminUserHome";
    }
    //All Students Report
    @GetMapping("students")
    public String displayAllStudentsReport(@RequestParam(required = false) boolean isMIReq,
                                           Model model){

        model.addAttribute("rptType", "allStudentsRpt");
        model.addAttribute("students", getSortedStudents());

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    //getStudent the connected to a user and pass it to AStudentsReport handler
    @GetMapping("getStudentAssociatedWithUser")
    public String getAndPassStudentToTheStudentReportHandler(HttpServletRequest request, Model model){
        boolean isGradesButtonReq = true;
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Student s = user.getStudent();

        /*todo start here determine what to if a user does not have a child
           assigned to  them */
        //if the user does not s == null and user's accountType = 1 then
        //redirect them to all students report with a message that says the
        // reason for this
        if(s == null && user.getAccountType() == 1){
            return "redirect:/theBridge/reports/students/";
        } else if (s == null && user.getAccountType() == 0){
            model.addAttribute("rptType", "invalid");
            model.addAttribute("invalid",
                    "No student is assigned to your account."+
                            "\nSend a message to the site manager Cory Pride, " +
                            "if you think that you received this message in " +
                            "error.");
            model.addAttribute("forSmallWindow",true);

            return "site/theBridgeDU/theBridgeDU";
        }
        // if the user s==null and the user's accountType = 0 then user ther
        // invalid fragment to give them the opp to inform the site manager of
        // their childs info so they will updated in the system


        return "redirect:/theBridge/reports/students/"+s.getId()+
                "?isGradesButtonReq="+isGradesButtonReq;
    }

    //A Student Report
    @GetMapping("students/{studentId}")
    public String displayAStudentsReport(@PathVariable int studentId,
                                         @RequestParam(required = false) boolean isGradesButtonReq,
                                         @RequestParam(required = false) boolean isMIReq,
                                                     Model model){


        model.addAttribute("isGradesButtonReq",isGradesButtonReq);
        model.addAttribute("isMIReq", isMIReq);
        Optional<Student> result = studentRepository.findById(studentId);

        if (result.isEmpty()){
            model.addAttribute("reports", getReportNames());
            model.addAttribute("forms", getFormNames());
            model.addAttribute("frmKeys",getSortedFormNamesForGB());
            model.addAttribute("rptKeys",getSortedReportNamesForGB());
            model.addAttribute("rptType", "invalid");
            model.addAttribute("problem", "There was a problem with your http " +
                    "request.");
            model.addAttribute("invalid", "The student Id provided was " +
                    "invalid!");

            if(isMIReq){
                //mandatory attributes for myInterface page navPane
                model.addAttribute("reports", getAllReportNames());
                model.addAttribute("rptKeys",getSortedReportNames());
                model.addAttribute("forms", getAllFormNames());
                model.addAttribute("frmKeys", getSortedFormNames1());
                model.addAttribute("frmKeys2", getSortedFormNames2());
                model.addAttribute("frmKeys3", getSortedFormNames3());
                //end of mandatory attributes for myInterface page

                return "site/theBridgeAU/myInterface";
            }
            return "site/theBridgeAU/adminUserHome";
        }


        model.addAttribute("rptType", "aStudentRpt");
        model.addAttribute("student", result.get());


        //extra stuff to be added at a later date
        model.addAttribute("announcements",
                "No announcements for " + GetTodaysDate.getDate() +
                ".");

        if(isGradesButtonReq){
          model.addAttribute("isGradesButtonReq",true);
          return "site/theBridgeDU/theBridgeDU";
        } else if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            return "site/theBridgeAU/myInterface";
        }
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        return "site/theBridgeAU/adminUserHome";
    }

    //All Students (By School)
    @GetMapping("allStudentsBySchool")
    public String displayAllStudentsBySchoolReport(@RequestParam boolean isMIReq,
                                                   Model model){

        model.addAttribute("rptType", "allStudentsBySchoolRpt");
        model.addAttribute("schools", getSortedSchoolsNoNA());
        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);

        return "site/theBridgeAU/adminUserHome";
    }

    //a school report
    @GetMapping("school")
    public String displayASchool(@RequestParam int schoolId,
                                 @RequestParam boolean isMIReq, Model model){


        School s = schoolRepository.findById(schoolId).get();
        model.addAttribute("school",s);
        model.addAttribute("administrators",s.getAdministrators());
        model.addAttribute("students",s.getStudents());
        model.addAttribute("classes",s.getClasses());
        model.addAttribute("rptType", "aSchoolRpt");


        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";

        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("schoolLinks")
    public String displaySchoolLinks(@RequestParam boolean isMIReq, Model model){

        model.addAttribute("rptType", "allSchoolLinksRpt");
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType", "allSchoolLinksRpt");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";

        }

        //mandatory attributes for adminNav page navPane
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        //end of mandatory attributes for adminNav page navPane
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }


    //todo add a comparator to sort courses
    @GetMapping("courseList")
    public String displayAllCourses(@RequestParam boolean isMIReq,
                                    Model model){

        model.addAttribute("courses", courseRepository.findAll() );
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType", "courseList");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";

        }

        //mandatory attributes for adminNav page navPane
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        //end of mandatory attributes for adminNav page navPane
        model.addAttribute("isMIReq", false);

        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("classes")
    public String displayAllClasses(@RequestParam boolean isMIReq, Model model){


        model.addAttribute("classes", classRepository.findAll());
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType", "classes");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);

        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("aClass")
    public String displaySpecificClass(@RequestParam boolean isMIReq,
                                       @RequestParam int classId,
                                       Model model){

        model.addAttribute("class", classRepository.findById(classId).get());
        model.addAttribute("rptType", "aClass");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);

        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("classAssignments")
    public String displayAllClassAssignments(@RequestParam int classId,
                                             Model model){

        model.addAttribute("classes", classRepository.findById(classId).get());
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("rptType", "classAssignments");
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("assignments")
    public String displayAllAssignments(@RequestParam boolean isMIReq, Model model){


        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType", "assignments");
        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);

        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("assignment")
    public String displayAnAssignment(@RequestParam boolean isMIReq,
                                      @RequestParam int assignmentId,
                                      Model model){
        ClassAssignment assignment =
                classAssignmentRepository.findById(assignmentId).get();
        model.addAttribute("assignment", assignment);
        model.addAttribute("rptType", "assignment");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";

    }

    @GetMapping("studentAssignments")
    public String displayStudentAssignments(@RequestParam int classId,
                                            @RequestParam int studentId,
                                            @RequestParam(required = false) boolean isGradesButtonReq,
                                            @RequestParam boolean isMIReq,
                                            Model model){
        Class c = classRepository.findById(classId).get();
        model.addAttribute("class", c);
        Student s = studentRepository.findById(studentId).get();
        model.addAttribute("student", s);
        model.addAttribute("assignmentScores",
                c.aSetOfAssignmentScores(s.getFullName()));
        model.addAttribute("rptType", "displayAssignmentsAndScores");
        model.addAttribute("isGradesButtonReq",isGradesButtonReq);
        model.addAttribute("isMIReq", isMIReq);

        if(isGradesButtonReq){
            model.addAttribute("reports", getReportNames());
            model.addAttribute("forms", getFormNames());
            model.addAttribute("frmKeys",getSortedFormNamesForGB());
            model.addAttribute("rptKeys",getSortedReportNamesForGB());
            model.addAttribute("studentAssignmentsDU",true);
            return "site/theBridgeDU/theBridgeDU";
        } else if (isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            return "site/theBridgeAU/myInterface";
        }
        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        return "site/theBridgeAU/adminUserHome";
    }
    @GetMapping("roster")
    public String displayARoster(@RequestParam int classId,
                                 @RequestParam boolean isMIReq,
                                 Model model){

        Class aClass = classRepository.findById(classId).get();

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType", "aRoster");
        model.addAttribute("class", aClass);
        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            return "site/theBridgeAU/myInterface";
        }
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("rosters")
    public String displayGetSchoolForRosters(@RequestParam boolean isMIReq,
                                             Model model){
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType", "getSchoolForAllRosters");

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("schoolRosters")
    public String displayAllRosters(@RequestParam boolean isMIReq,
                                    @RequestParam int schoolId, Model model){

        School s = schoolRepository.findById(schoolId).get();
        List<Class> classes = s.getClasses();
        model.addAttribute("rptType", "allRosters");
        model.addAttribute("schoolName", s.getSchoolName());
        model.addAttribute("classes",classes);

        if(isMIReq){
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("isMIReq", true);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("classGrades")
    private String displayClassGrades(@RequestParam int classId, Model model){


        model.addAttribute("reports", getReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys",getSortedFormNamesForGB());
        model.addAttribute("rptKeys",getSortedReportNamesForGB());
        model.addAttribute("rptType", "displayAllClassGrades");
        model.addAttribute("class", classRepository.findById(classId).get());
        Class c = classRepository.findById(classId).get();
        model.addAttribute("assignmentScores", c.assignmentScores());
        model.addAttribute("studentAvgs", c.studentAvgs());
        model.addAttribute("allStudentsAssignmentScores",
                c.allStudentsAssignmentScores());
        model.addAttribute("getAStudentsClassAverage",
                c.getAStudentsClassAverage("Gloria  Bowden"));
        return "site/theBridgeAU/adminUserHome";
    }


}
