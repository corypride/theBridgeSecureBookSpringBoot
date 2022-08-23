package org.launchcode.theBridge2.controllers;
import org.launchcode.theBridge2.data.*;
import org.launchcode.theBridge2.models.*;
import org.launchcode.theBridge2.models.Class;
import org.launchcode.theBridge2.models.comparator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.Calendar;


/*TODO use this controller to handle all of the admin bridgeStuff*/

@Controller
@RequestMapping("theBridge/nav2")
public class TheBridgeAUController {

    @Autowired
    public AdministratorRepository administratorRepository;
    @Autowired
    public AdminPositionRepository apRepository;
    @Autowired
    public SchoolRepository schoolRepository;
    @Autowired
    public CourseRepository courseRepository;
    @Autowired
    public CourseGenreRepository cgRepository;
    @Autowired
    public CourseLevelRepository courseLevelRepository;
    @Autowired
    public RelationshipTypeRepository relRepository;
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public StudentStatusRepository ssRepository;
    @Autowired
    public StatusRepository statusRepository;
    @Autowired
    public GradeLevelRepository gradeLevelRepository;
    @Autowired
    public SchoolYearRepository schoolYearRepository;
    @Autowired
    public ClassRepository classRepository;
    @Autowired
    public ReportNameRepository reportNameRepository;
    @Autowired
    public FormNameRepository formNameRepository;
    @Autowired
    public ClassAssignmentRepository classAssignmentRepository;
    @Autowired
    public AssignmentStatusRepository asRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public AuthenticationController authenticationController;



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

    public List<Course> sortedCourses(){
        List <Course> courses = (List<Course>) courseRepository.findAll();
        courses.sort(new CourseComparator());

        return courses;
    }

    public List<Course> sortedCourses(List<Course> courseList){
        List <Course> courses = courseList;
        courses.sort(new CourseComparator());
        return courses;
    }
    public List<Administrator> sortedAdministrators(){
        List <Administrator> admins =
                (List<Administrator>) administratorRepository.findAll();
        admins.sort(new AdministratorLastNmComparator());

        return admins;
    }

    public List<User> adminUsers(){
        List<User> adminUsers = new ArrayList<>();
        for(User u: userRepository.findAll()){
            if(u.getAccountType() == 1){
                adminUsers.add(u);
            }
        }
        return adminUsers;
    }



    public  HashMap<String, String> getReportNames(){
        HashMap<String, String> output = new HashMap<>();

        for (ReportName r: reportNameRepository.findAll()){
            if(r.getNavPg2Order()>0){
                output.put(r.getDisplayName(),r.getRptName());
            }
        }
        return output;
    }
    public List<String> getSortedReportNames(){
        HashMap<String, String> data = getReportNames();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {
            result.add(nm);
        }
        result.sort(new ReportNameComparator());

        return result;
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

    public List<String> getSortedFormNames1(){
        HashMap<String, String> data = getFormNames();
        List<String> result = new ArrayList<>();
        for (String nm: data.keySet()) {

                result.add(nm);
        }
        result.sort(new FormNameComparator());
        return result;
    }


    //display all nav and reports
    @GetMapping("")
    public String displayAdminUserHome(Model model){

        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "");
        return "site/theBridgeAU/adminUserHome";
    }



    

    //For adding Status
    @GetMapping("addStatus")
    public String displayAddStatusForm(Model model){

        model.addAttribute("title", "Add Status");
        model.addAttribute(new Status());
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "addStatus");
        return "site/theBridgeAU/adminUserHome";
    }

    //For processing addStatusForm
    @RequestMapping("addStatus")
    public String processStatusForm(@ModelAttribute @Valid Status newStatus, Errors errors, Model model){
        model.addAttribute("title", "Add Status");
        if (errors.hasErrors()){
            model.addAttribute("reports", getReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("rptType", "addStatus");
            return "site/theBridgeAU/adminUserHome";

        }
        if (statusRepository.findAll() != null) {
            List<Status> result = (List) statusRepository.findAll();
            for (Status s : result) {
                if (s.getStatusName().toLowerCase().trim().equals(newStatus.getStatusName().toLowerCase().trim()) &&
                s.getDescription().toLowerCase().trim().equals(newStatus.getDescription().toLowerCase().trim())) {
                    errors.rejectValue("statusName", "status.duplicate",
                            "Status by this name and description" +
                                    "already exists.");
                    model.addAttribute("reports", getReportNames());
                    model.addAttribute("rptKeys",getSortedReportNames());
                    model.addAttribute("forms", getFormNames());
                    model.addAttribute("frmKeys", getSortedFormNames1());
                    model.addAttribute("rptType", "addStatus");
                    return "site/theBridgeAU/adminUserHome";
                }
            }
        }
        statusRepository.save(newStatus);
        return "redirect:/theBridge/nav2/addStatus/";
    }

    @GetMapping("addAssignment")
    public String chooseClassForAssignmentForm(Model model){

        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "chooseAClass");
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("createAssignment")
    public String displayCreateAssignmentForm(@RequestParam int classId,
                                              Model model){

        Class aClass = classRepository.findById(classId).get();
        model.addAttribute("info", aClass.getSemesterInfo());
        model.addAttribute(new ClassAssignment());
        model.addAttribute("aClassId", aClass.getId());
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("isMIReq", false);
        model.addAttribute("rptType", "createAssignment");
        return "site/theBridgeAU/adminUserHome";

    }

    @RequestMapping("createAssignment")
    public String processCreateAssignmentForm(@ModelAttribute @Valid
    ClassAssignment newClassAssignment, Errors errors,
                                              @RequestParam Integer classId,
                                              @RequestParam(required = false) boolean isExtraCredit,   Model model){

        if(errors.hasErrors()){
            Class aClass = classRepository.findById(classId).get();
            model.addAttribute("info",
                    aClass.getCourse().getDisplayName()+" - "+aClass.getSemesterInfo());
            model.addAttribute("aClassId", aClass.getId());
            model.addAttribute("reports", getReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("isMIReq", false);
            model.addAttribute("rptType", "createAssignment");
            return "site/theBridgeAU/adminUserHome";
        }

        Class c = classRepository.findById(classId).get();
        newClassAssignment.setaClass(c);
        newClassAssignment.setExtraCredit(isExtraCredit);
        classAssignmentRepository.save(newClassAssignment);

        //todo return this to a place in the report controller and then create
        // a database item to represent this report then make the report have
        // hyperlinks that when clicked take you to the info about the
        // particular assignment

//
        return "redirect:/theBridge/nav2/createAssignment?classId="+newClassAssignment.getaClass().getId();//"site

    }

    //forms for editAssignment

    @GetMapping("getSchoolForEditClassAssignment")
    public String displayGetSchoolForEditClassAssignment(Model model){

        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("isMIReq", false);
        model.addAttribute("schools",getSortedSchoolsNoNA());
        model.addAttribute("rptType", "getSchoolForEditClassAssignment");
        return "site/theBridgeAU/adminUserHome";
    }


    @GetMapping("getClassAssignment")
    public String displayGetClassAssignmentForEditAssignment(@RequestParam int schoolId, @RequestParam boolean isMIReq,Model model){


        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("isMIReq", false);

        School s = schoolRepository.findById(schoolId).get();
        model.addAttribute("school", s);
        model.addAttribute("classes",s.getClasses());
        model.addAttribute("rptType", "getAllClassesForEditClassAssignment");
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("allAssignmentsForPossibleEdit")
    public String displayAssignmentsForPossibleEdit(@RequestParam int classId,
                                                    @RequestParam boolean isMIReq, Model model){
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("isMIReq", false);


        model.addAttribute("class", classRepository.findById(classId).get());
        model.addAttribute("rptType", "allAssignmentsForPossibleEdit");
        return "site/theBridgeAU/adminUserHome";

    }


    @GetMapping("editAssignment")
    public String displayEditAssignmentForm(@RequestParam int assignmentId,
                                            @RequestParam boolean isMIReq, Model model){
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("isMIReq", false);

        model.addAttribute("classAssignment",
                classAssignmentRepository.findById(assignmentId).get());
        model.addAttribute("rptType", "editAssignment");
        return "site/theBridgeAU/adminUserHome";
    }

    @RequestMapping("editAssignment")
    public String processEditAssignmentForm( @RequestParam int assignmentId,
                                             @RequestParam(required = false) String assignmentName,
                                             @RequestParam(required = false) String description,
                                             @RequestParam(required = false) double maxPoints,
                                             @RequestParam(required = false) boolean isExtraCredit,
                                             @RequestParam(required = false) String dueDate,
                                             Model model){

        ClassAssignment ca = classAssignmentRepository.findById(assignmentId).get();
        if(!assignmentName.isBlank()){
            ca.setAssignmentName(assignmentName);
        }
        if(!description.isBlank()){
            ca.setDescription(description);
        }
        if(maxPoints >0){
            ca.setMaxPoints(maxPoints);
        }
        if(!dueDate.isBlank()){
            ca.setDueDate(dueDate);
        }
        ca.setExtraCredit(isExtraCredit);
        classAssignmentRepository.save(ca);

        return "redirect:/theBridge/nav2/getSchoolForEditClassAssignment";
    }

    //end of forms for editAssignment


    @GetMapping("getSchool")
    public String displayGetSchoolForGradingAssignments(Model model){
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "getSchoolForGradingAssignments");
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    @RequestMapping("getSchool")
    public String displayGetClassForGradingAssignments(@RequestParam int schoolId, Model model){

        School school =schoolRepository.findById(schoolId).get();
        List<Class> classes =school.getClasses();

        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "getClassForGradingAssignments");

        if(classes.size() < 1){
            model.addAttribute("rptType", "invalid");
            model.addAttribute("invalid", "There are no classes currently " +
                    "assigned to this school.");
            model.addAttribute("forSmallWindow", false);
        } else {

            model.addAttribute("schools", school);
            model.addAttribute("isMIReq", false);
        }
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("getClass")
    public String displayGetAssignmentForGradingAssignments(@RequestParam int classId,
                                                            Model model){
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "getAnAssignment");
        Class aClass = classRepository.findById(classId).get();
        model.addAttribute("class", aClass);
        model.addAttribute("isMIReq", false);
        return "site/theBridgeAU/adminUserHome";
    }

    @GetMapping("gradeAnAssignment")
    public String displayGradeAssignmentsForm(@RequestParam int assignmentId,
                                              Model model){
        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("isMIReq", false);
        ClassAssignment ca =
                classAssignmentRepository.findById(assignmentId).get();
        if(ca.getaClass().getStudents().size() < 1){
            model.addAttribute("rptType","invalid");
            model.addAttribute("forSmallWindow", false);
            model.addAttribute("invalid", "User is not correctly logged onto " +
                    "the site. Please log off and log back on!");
        } else {

            model.addAttribute("rptType", "gradeAnAssignment");
            model.addAttribute(new AssignmentStatus());
            model.addAttribute("assignment", ca);
            model.addAttribute("statusList", statusRepository.findAll());
        }
        return "site/theBridgeAU/adminUserHome";
    }

    @RequestMapping("gradeAnAssignment")
    public String processGradeAssignmentForm(@ModelAttribute @Valid AssignmentStatus newAssignmentStatus, Errors errors,
                                             @RequestParam int assignmentId, @RequestParam int studentId, @RequestParam int statusId,
                                             Model model){
        model.addAttribute("isMIReq", false);
        if(errors.hasErrors()){
            model.addAttribute("reports", getReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("rptType", "gradeAnAssignment");
            model.addAttribute("assignment",
                    classAssignmentRepository.findById(assignmentId).get());
            model.addAttribute("statusList",statusRepository.findAll());
            model.addAttribute("message", errors.getAllErrors());
            return "site/theBridgeAU/adminUserHome";
        }
        //todo start here, write code to process these params
        ClassAssignment classAssignment =
                classAssignmentRepository.findById(assignmentId).get();
        Student student = studentRepository.findById(studentId).get();
        Status aStatus = statusRepository.findById(statusId).get();
        newAssignmentStatus.setClassAssignment(classAssignment);
        newAssignmentStatus.setStudent(student);
        newAssignmentStatus.setStatus(aStatus);

        List<AssignmentStatus> testList =
                (List<AssignmentStatus>) asRepository.findAll();
        for(AssignmentStatus s: testList){
            boolean condition1 =
                    (newAssignmentStatus.getStudent().getId() == s.getStudent().getId());
            boolean condition2 =
                    (newAssignmentStatus.getClassAssignment().getId() == s.getClassAssignment().getId());
            if(condition1 && condition2){
               model.addAttribute("anError",
                        "This assignment has already been graded for "+newAssignmentStatus.getStudent().getFullName() +
                                ". If you want to change something " +
                                "pertaining to this assignment, go to the " +
                                "editAssignmentGrade form");

                model.addAttribute("reports", getReportNames());
                model.addAttribute("rptKeys",getSortedReportNames());
                model.addAttribute("forms", getFormNames());
                model.addAttribute("frmKeys", getSortedFormNames1());
                model.addAttribute("rptType", "gradeAnAssignment");
                model.addAttribute("assignment",
                        classAssignmentRepository.findById(assignmentId).get());
                model.addAttribute("statusList",statusRepository.findAll());

                return "site/theBridgeAU/adminUserHome";
            }
        }

        asRepository.save(newAssignmentStatus);

        model.addAttribute("reports", getReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("rptType", "");
        return "site/theBridgeAU/adminUserHome";
    }
}
