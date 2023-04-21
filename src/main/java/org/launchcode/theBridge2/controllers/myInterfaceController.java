package org.launchcode.theBridge2.controllers;

import org.launchcode.theBridge2.data.*;
import org.launchcode.theBridge2.models.*;
import org.launchcode.theBridge2.models.Class;
import org.launchcode.theBridge2.models.comparator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("myInterface")
public class myInterfaceController {

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

    public List<User> adminUsersWOacct(){
        List<User> newAdmins = new ArrayList<>();
        List <Integer> admins =new ArrayList<>();

        for(Administrator a: administratorRepository.findAll()){
            admins.add(a.getUser().getId());
        }

        for(User u: adminUsers()){
                if(!admins.contains(u.getId())){
                    newAdmins.add(u);
                }
        }
        return newAdmins;
    }


    public  HashMap<String, String> getAllReportNames(){
        HashMap<String, String> output = new HashMap<>();

        for (ReportName r: reportNameRepository.findAll()){

            output.put(r.getDisplayName(),
                    r.getRptName());
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



    public  HashMap<String, String> getAllFormNames(){
        HashMap<String, String> output = new HashMap<>();
        for (FormName f: formNameRepository.findAll()){
            output.put(f.getDisplayName(),f.getFormNm());
        }
        return output;
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
            System.out.println(nm);
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

    // myInterfaceController
    @GetMapping("")
    public String displayMyInterface(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if(user == null){
            return "redirect:/login" ;
        }
        else if(!user.isSiteAdmin()){
            return "redirect:/login" ;
        }

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "");
        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("addFormName")
    public String addFormName(Model model){
        model.addAttribute(new FormName());
        return "site/theBridgeAU/addFormName";
    }
    @RequestMapping("addFormName")
    public String addFormName(@ModelAttribute @Valid FormName newFormName, Errors errors, Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("title", "Add Course Genre");
        if(errors.hasErrors()){


            return "site/theBridgeAU/myInterface";

        }
        if (formNameRepository.findAll() != null) {
            List<FormName> result = (List) formNameRepository.findAll();
            for (FormName cg : result) {
                if (cg.getFormNm().toLowerCase().trim().equals(newFormName.getFormNm().toLowerCase().trim())) {
                    errors.rejectValue("Form Name", "name.duplicate",
                            "Form by this name " +
                                    "already exists.");


                    return "site/theBridgeAU/addFormName";
                }
            }
        }
        formNameRepository.save(newFormName);
        return "redirect:/myInterface/addFormName/";
    }


    //For creating adminPositions
    @GetMapping("addAdminPosition")
    public String addAdminPosition(Model model){
        model.addAttribute("title", "Add Administrator Position");
        model.addAttribute(new AdminPosition());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "adminPos");
        return "site/theBridgeAU/myInterface";
    }

    //For processing adminPosition form
    @RequestMapping("addAdminPosition")
    public String processAddAdminPositionForm(@ModelAttribute @Valid AdminPosition newPos, Errors errors, Model model ){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Administrator Position");
            model.addAttribute("rptType", "adminPos");
            return "site/theBridgeAU/myInterface";
        }


        if (apRepository.findAll() != null) {
            List<AdminPosition> result = (List) apRepository.findAll();
            for (AdminPosition a : result) {
                if (a.getPosition().toLowerCase().trim().equals(newPos.getPosition().toLowerCase().trim())) {

                    model.addAttribute("title", "Add Administrator Position");
                    errors.rejectValue("position", "position.duplicate", "Position " +
                            "already exists.");

                    model.addAttribute("rptType", "adminPos");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }

        apRepository.save(newPos);
        return "redirect:/myInterface/addAdminPosition/";
    }

    //For adding Course genres
    @GetMapping("addCourseGenre")
    public String displayAddCourseGenreForm(Model model){
        model.addAttribute("title", "Add Course Genre");
        model.addAttribute(new CourseGenre());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "addCourseGenre");
        return "site/theBridgeAU/myInterface";
    }

    //For processing CourseGenre form
    @RequestMapping("addCourseGenre")
    public String processCourseGenreForm(@ModelAttribute @Valid CourseGenre newCourseGenre, Errors errors, Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("title", "Add Course Genre");
        if(errors.hasErrors()){

            model.addAttribute("rptType", "addCourseGenre");
            return "site/theBridgeAU/myInterface";

        }
        if (cgRepository.findAll() != null) {
            List<CourseGenre> result = (List) cgRepository.findAll();
            for (CourseGenre cg : result) {
                if (cg.getGenre().toLowerCase().trim().equals(newCourseGenre.getGenre().toLowerCase().trim())) {
                    errors.rejectValue("genre", "genre.duplicate",
                            "Genre by this name " +
                                    "already exists.");

                    model.addAttribute("rptType", "addCourseGenre");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }
        cgRepository.save(newCourseGenre);
        return "redirect:/myInterface/addCourseGenre/";
    }

    //For adding Course Levels
    @GetMapping("addCourseLevel")
    public String displayAddCourseLevelForm(Model model){
        model.addAttribute("title", "Add Course Level");
        model.addAttribute(new CourseLevel());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "addCourseLevel");
        return "site/theBridgeAU/myInterface";
    }

    //For processing addCourseLevelForm()
    @RequestMapping("addCourseLevel")
    public String processAddCourseLevelForm(@ModelAttribute @Valid CourseLevel newCourseLevel, Errors errors, Model model){
        model.addAttribute("title", "Add Course Level");
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if (errors.hasErrors()){

            model.addAttribute("rptType", "addCourseLevel");
            return "site/theBridgeAU/myInterface";
        }
        if (courseLevelRepository.findAll() != null) {
            List<CourseLevel> result = (List) courseLevelRepository.findAll();
            for (CourseLevel cl : result) {
                if (cl.getLevel().toLowerCase().trim().equals(newCourseLevel.getLevel().toLowerCase().trim())) {
                    errors.rejectValue("level", "level.duplicate",
                            "Course Level by this name " +
                                    "already exists.");

                    model.addAttribute("rptType", "addCourseLevel");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }
        courseLevelRepository.save(newCourseLevel);
        return "redirect:/myInterface/addCourseLevel/";
    }

    //For adding Grade Levels
    @GetMapping("addGradeLevel")
    public String addGradeLevel(Model model){
        model.addAttribute("title", "Add Grade Level");
        model.addAttribute(new GradeLevel());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "addGradeLevel");
        return "site/theBridgeAU/myInterface";
    }

    //For processing addGradeLevel form
    @RequestMapping("addGradeLevel")
    public String processAddGradeLevelForm(@ModelAttribute @Valid GradeLevel newLevel,
                                           Errors errors, Model model ){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if(errors.hasErrors()){

            model.addAttribute("rptType", "addGradeLevel");
            return "site/theBridgeAU/myInterface";
        }


        if (gradeLevelRepository.findAll() != null) {
            List<GradeLevel> result = (List) gradeLevelRepository.findAll();
            for (GradeLevel a : result) {
                if (a.getLevel().toLowerCase().trim().equals(newLevel.getLevel().toLowerCase().trim())) {

                    model.addAttribute("title", "Add Grade Level");
                    errors.rejectValue("level", "level.duplicate",
                            "Level " +
                                    "already exists.");

                    model.addAttribute("rptType", "addGradeLevel");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }

        gradeLevelRepository.save(newLevel);
        return "redirect:/myInterface/addGradeLevel/";
    }

    //For displaying addRelationshipType Form
    @GetMapping("addRelationshipType")
    public String displayAddRelationshipTypeForm(Model model){
        model.addAttribute("title", "Add Relationship Type");
        model.addAttribute(new RelationshipType());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "addRelationshipType");
        return "site/theBridgeAU/myInterface";
    }

    //For processing addRelationshipType Form
    @RequestMapping("addRelationshipType")
    public String processAddRelationshipTypeForm(@ModelAttribute @Valid RelationshipType newRelType, Errors errors, Model model){
        model.addAttribute("title", "Add Relationship Type");
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if(errors.hasErrors()){

            model.addAttribute("rptType", "addRelationshipType");
            return "site/theBridgeAU/adminUserHome";
        }

        if (relRepository.findAll() != null) {
            List<RelationshipType> result = (List) relRepository.findAll();
            for (RelationshipType rs : result) {

                if (rs.getType().toLowerCase().trim().equals(newRelType.getType().toLowerCase().trim())) {
                    errors.rejectValue("type", "type.duplicate",
                            "Type by this name " +
                                    "already exists.");

                    model.addAttribute("rptType", "addRelationshipType");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }
        relRepository.save(newRelType);
        return "redirect:/myInterface/addRelationshipType";
    }

    //For creating schools
    @GetMapping("addSchool")
    public String displayAddSchoolForm(Model model){

        model.addAttribute("title", "Add School");
        model.addAttribute(new School());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "addSchool");
        return "site/theBridgeAU/myInterface";
    }

    //For processing displayAddSchoolForm
    @RequestMapping("addSchool")
    public String processAddSchoolForm(@ModelAttribute @Valid School newSchool,
                                       Errors errors, Model model) {
        model.addAttribute("title", "Add School");
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if (errors.hasErrors()){
            model.addAttribute("rptType", "addSchool");
            //model.addAttribute("message", errors.getAllErrors());
            return "site/theBridgeAU/myInterface";
        }
        if (schoolRepository.findAll() != null) {
            List<School> result = (List) schoolRepository.findAll();
            for (School s : result) {
                if (s.getSchoolName().toLowerCase().trim().equals(newSchool.getSchoolName().toLowerCase().trim())) {

                    errors.rejectValue("schoolName", "name.duplicate",
                            "School by this name " +
                                    "already exists.");

                    model.addAttribute("rptType", "addSchool");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }
        schoolRepository.save(newSchool);
        return "redirect:/myInterface/addSchool/";
    }

    //For adding School Year
    @GetMapping("addSchoolYear")
    public String addSchoolYear(Model model){
        model.addAttribute("title", "Add School Year");
        model.addAttribute(new SchoolYear());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "addSchoolYear");
        return "site/theBridgeAU/myInterface";
    }

    //For processing addSchoolYear form
    @RequestMapping("addSchoolYear")
    public String processAddSchoolYearForm(@ModelAttribute @Valid SchoolYear newYear,
                                           Errors errors, Model model ){
        model.addAttribute("title", "Add School Year");
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if(errors.hasErrors()){
            model.addAttribute("rptType", "addSchoolYear");
            return "site/theBridgeAU/myInterface";
        }


        if (gradeLevelRepository.findAll() != null) {
            List<SchoolYear> result = (List) schoolYearRepository.findAll();
            for (SchoolYear s : result) {
                if (s.getYear()== newYear.getYear()) {

                    model.addAttribute("title", "Add School Year");
                    errors.rejectValue("year", "year.duplicate",
                            "year " +
                                    "already exists.");

                    model.addAttribute("rptType", "addSchoolYear");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }
        schoolYearRepository.save(newYear);
        return "redirect:/myInterface/addSchoolYear/";
    }

    //For creating administrators
    @GetMapping("createAdmin")
    public String displayCreateAdminsForm(Model model){
        model.addAttribute("title", "Create An Administrator");
        model.addAttribute(new Administrator());
        model.addAttribute("adminPositions", apRepository.findAll());

        //if user does not have an admin acct set up already
        model.addAttribute("users",adminUsersWOacct());

        model.addAttribute("schools", getSortedSchools());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "createAdmin");
        return "site/theBridgeAU/myInterface";
    }

    //For processing createAdminsForm
    @RequestMapping("createAdmin")
    public String processCreateAdminsForm(@ModelAttribute @Valid Administrator newAdmins, Errors errors, @RequestParam Integer schoolId,
                                          @RequestParam Integer userId,
                                          @RequestParam Integer positionId,
                                          Model model){

        model.addAttribute("title", "All Admin Rpt");
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if (errors.hasErrors()){
            model.addAttribute("adminPositions", apRepository.findAll());
            model.addAttribute("schools", getSortedSchools());
            model.addAttribute("users",adminUsers());
            model.addAttribute("rptType", "createAdmin");
            return "site/theBridgeAU/myInterface";
        }

        List<Administrator> test = (List) administratorRepository.findAll();
        User u = userRepository.findById(userId).get();
        newAdmins.setUser(u);

        if(test != null){
            for(Administrator a: test){
                if(a.getUser().getId() ==
                        newAdmins.getUser().getId() && a.getDateOfBirth().equals(newAdmins.getDateOfBirth())){
                    errors.rejectValue("dateOfBirth","administrator.duplicate"
                            ,"Administrator with this name and birthday " +
                                    "already" +
                                    " exists.");
                    model.addAttribute("users",adminUsers());
                    model.addAttribute("adminPositions", apRepository.findAll());
                    model.addAttribute("schools", getSortedSchools());
                    model.addAttribute("rptType", "createAdmin");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }

        Integer getNAId=0;
        List<School> schoolList = (List<School>) schoolRepository.findAll();
        for(School s: schoolList){
            if(s.getSchoolName().toLowerCase().equals("n/a")){
                getNAId = s.getId();
            }
        }
        boolean hasSchool = getNAId == schoolId;

        //reset variable to be used again
        getNAId =0;

        List<AdminPosition> adminPositionList =
                (List<AdminPosition>) apRepository.findAll();
        for(AdminPosition a: adminPositionList){
            if(a.getPosition().toLowerCase().equals("n/a")){
                getNAId = a.getId();
            }
        }
        boolean hasPosition = getNAId == positionId;

        if (!hasSchool && hasPosition) {

            // if the school is selected and the position is n/a then you must
            // reject the value to get a position
            errors.rejectValue("adminPosition", "mismatch.data", "Schools was " +
                    "selected so position cannot be 'N/A'");
            model.addAttribute("adminPositions", apRepository.findAll());
            model.addAttribute("users",adminUsers());
            model.addAttribute("schools", getSortedSchools());
            model.addAttribute("rptType", "createAdmin");
            return "site/theBridgeAU/myInterface";

        } else  if(hasSchool  && !(hasPosition)){
            //if school is n/a and school is selected set position = n/a

            List<AdminPosition> aps = (List<AdminPosition>) apRepository.findAll();

            Integer idx=0;
            for(AdminPosition a : aps){
                if(a.getPosition().toLowerCase().equals("n/a")){
                    idx = a.getId();
                    break;
                }
            }

            School s = schoolRepository.findById(schoolId).get();
            newAdmins.setSchool(s);
            AdminPosition ap = apRepository.findById(idx).get();
            newAdmins.setAdminPosition(ap);
            //set date hired == N/A
            newAdmins.setDateHired("N/A");

        } else {

            //if both are n/a
            School s = schoolRepository.findById(schoolId).get();
            newAdmins.setSchool(s);
            AdminPosition p = apRepository.findById(positionId).get();
            newAdmins.setAdminPosition(p);
            //set date hired == N/A
            newAdmins.setDateHired("N/A");
        }


        administratorRepository.save(newAdmins);
        return "redirect:/theBridge/reports/admin?isMIReq=true";
    }

    @GetMapping("addAssignment")
    public String chooseClassForAssignmentForm(Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "chooseAClass");
        //this is a conditional for the createAssignmentForm that signals the
        // action path
        model.addAttribute("isMIReq", true);
        model.addAttribute("schools", getSortedSchoolsNoNA());
        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("createAssignment")
    public String displayCreateAssignmentForm(@RequestParam int classId,
                                              Model model){

        Class aClass = classRepository.findById(classId).get();
        model.addAttribute("info", aClass.getSemesterInfo());
        model.addAttribute(new ClassAssignment());
        model.addAttribute("aClassId", aClass.getId());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        //this is a conditional for the createAdminForms that signals the
        // action path
        model.addAttribute("isMIReq", true);
        model.addAttribute("rptType", "createAssignment");
        return "site/theBridgeAU/myInterface";

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
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys", getSortedFormNames1());
            model.addAttribute("frmKeys2", getSortedFormNames2());
            model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page

            //this is a conditional for the createAdminForms that signals the
            // action path
            model.addAttribute("isMIReq", true);
            model.addAttribute("rptType", "createAssignment");
            return "site/theBridgeAU/myInterface";
        }

        Class c = classRepository.findById(classId).get();
        newClassAssignment.setaClass(c);
        newClassAssignment.setExtraCredit(isExtraCredit);
        classAssignmentRepository.save(newClassAssignment);

        //todo return this to a place in the report controller and then create
        // a database item to represent this report then make the report have
        // hyperlinks that when clicked take you to the info about the
        // particular assignment

        return "redirect:/myInterface/createAssignment?classId="+newClassAssignment.getaClass().getId();//"site
    }


    //forms for editAssignment

    @GetMapping("getSchoolForEditClassAssignment")
    public String displayGetSchoolForEditClassAssignment(Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", true);
        //end of mandatory attributes for myInterface page

        model.addAttribute("schools",getSortedSchoolsNoNA());
        model.addAttribute("rptType", "getSchoolForEditClassAssignment");
        return "site/theBridgeAU/myInterface";
    }


    @GetMapping("getClassAssignment")
    public String displayGetClassAssignmentForEditAssignment(@RequestParam int schoolId, @RequestParam boolean isMIReq,Model model){


        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", isMIReq);
        //end of mandatory attributes for myInterface page

        School s = schoolRepository.findById(schoolId).get();
        model.addAttribute("school", s);
        model.addAttribute("classes",s.getClasses());
        model.addAttribute("rptType", "getAllClassesForEditClassAssignment");
        return "site/theBridgeAU/myInterface";
    }


    @GetMapping("allAssignmentsForPossibleEdit")
    public String displayAssignmentsForPossibleEdit(@RequestParam int classId,
                                                    @RequestParam boolean isMIReq, Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", isMIReq);
        //end of mandatory attributes for myInterface page

        model.addAttribute("class", classRepository.findById(classId).get());
        model.addAttribute("rptType", "allAssignmentsForPossibleEdit");
        return "site/theBridgeAU/myInterface";

    }

    @GetMapping("editAssignment")
    public String displayEditAssignmentForm(@RequestParam int assignmentId,
                                                    @RequestParam boolean isMIReq, Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", isMIReq);
        //end of mandatory attributes for myInterface page

        model.addAttribute("classAssignment",
                classAssignmentRepository.findById(assignmentId).get());
        model.addAttribute("rptType", "editAssignment");
        return "site/theBridgeAU/myInterface";

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

        return "redirect:/myInterface/getSchoolForEditClassAssignment";
    }

    //end of forms for editAssignment


    //the next five functions are for createClass

    //displayCreateClassForm
    @GetMapping("createClass")
    public String displayChooseSchoolForCreateClassForm(Model model){

        model.addAttribute("schools", getSortedSchoolsNoNA());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "getSchoolForCreateClass");
        return "site/theBridgeAU/myInterface";
    }

    public int[]  arrayOfIntegers(){

        //populate a list of integers for a drop box for maxStudents
        int [] num = new int[190];
        int idx=10;
        for(int i =0; i < num.length; i++){
            num[i]=idx;
            idx++;
        }

        return num;
    }

    public List<Administrator> getUnemployedAdmins(){

        List <Administrator> admin = sortedAdministrators();
        List<Administrator> newList = new ArrayList<>();
        Integer unEmployedId=0;


        //first find the id of N/A in adminPositon
        List<AdminPosition> aps = (List<AdminPosition>) apRepository.findAll();
        for(AdminPosition p: aps){
            if(p.getPosition().toLowerCase().equals("n/a")){
                unEmployedId=p.getId();
            }
        }

        for(int i = 0; i < admin.size(); i++){
            String test =
                    admin.get(i).getAdminPosition().getPosition().trim().toLowerCase();

            boolean isUnemployed =
                    admin.get(i).getAdminPosition().getId() == unEmployedId;

            if(( isUnemployed )  ){
                newList.add(admin.get(i));
            }
        }
        return newList;
    }

    @RequestMapping("createClass")
    public String displayCreateClassChForm(@RequestParam Integer schoolId,
                                           Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if(getUnemployedAdmins().isEmpty()){
            model.addAttribute("forSmallWindow",false);
            model.addAttribute("rptType", "invalid");
            model.addAttribute("invalid", "You must either enter new " +
                    "administrators for this class into the database using " +
                    "the 'Create Administrator' function, or update the " +
                    "positions of the current administrators that you wish " +
                    "to assign to this class by using the 'Update " +
                    "Administrator' form.");
            model.addAttribute("problem", "There are no unemployed " +
                    "administrators available.");

            return "site/theBridgeAU/myInterface";

        } else {
            model.addAttribute("instructors", getUnemployedAdmins());
        }
        Class c = new Class();
        School school = schoolRepository.findById(schoolId).get();
        c.setSchool(school);

        model.addAttribute("class" ,c);
        model.addAttribute("schoolName", c.getSchool().getSchoolName());
        model.addAttribute("numStudents", arrayOfIntegers());

        if(school.getCourses().isEmpty()){
            model.addAttribute("rptType", "invalid");
            model.addAttribute("forSmallWindow",false);
            model.addAttribute("problem", "No courses have been created for " +
                    "this school.");
            model.addAttribute("invalid",  "You must go to the " +
                    "createCourse form and create courses for this school in order for the " +
                    "createClass form to work properly.");

            return "site/theBridgeAU/myInterface";
        }
        model.addAttribute("courses", sortedCourses(school.getCourses()));
        model.addAttribute("schoolYears", schoolYearRepository.findAll());
        model.addAttribute("rptType", "createClass");
        return "site/theBridgeAU/myInterface";
    }

    //Process createClassForm
    @RequestMapping("createClass2")
    public String processCreateClassForm(@ModelAttribute @Valid Class newClass,
                                         @RequestParam Integer schoolId , Errors errors, Model model){

        if(errors.hasErrors()){

            model.addAttribute("numStudents", arrayOfIntegers());
            model.addAttribute("instructors", getUnemployedAdmins());
            model.addAttribute("courses", sortedCourses());
            model.addAttribute("schoolYears", schoolYearRepository.findAll());
            //mandatory attributes for myInterface page navPane
            model.addAttribute("reports", getAllReportNames());
            model.addAttribute("rptKeys",getSortedReportNames());
            model.addAttribute("forms", getAllFormNames());
            model.addAttribute("frmKeys", getSortedFormNames1());
model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
            //end of mandatory attributes for myInterface page
            model.addAttribute("rptType", "createClass");
            return "site/theBridgeAU/myInterface";

        }

        newClass.setSchool(schoolRepository.findById(schoolId).get());
        classRepository.save(newClass);

        //update the instructors status

        //get the instructorId to get the instructor obj
        Administrator a =
                administratorRepository.findById(newClass.getInstructor().getId()).get();
        //get and set the school obj
        School s = newClass.getSchool();
        a.setSchool(s);
        //set the adminPosId to Instructor
        List<AdminPosition> aps = (List<AdminPosition>) apRepository.findAll();
        for(AdminPosition ap: aps){
            if(ap.getPosition().toLowerCase().equals("instructor")){
                a.setAdminPosition(ap);
                break;
            }
        }
        //set date == today;
        a.setDateHired(GetTodaysDate.getDate());

        //end of administrator update
        administratorRepository.save(a);


        return "redirect:/myInterface/otherStaff?classId="+newClass.getId();
    }

    @GetMapping("chooseClass")
    public String displayGetClassToAssignOtherStaff(Model model){

        model.addAttribute("classes", classRepository.findAll());
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("rptType","addOtherStaff");
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

    @GetMapping("otherStaff")
    public String displayAssignOtherStaffForm(Model model,
                                              @RequestParam int classId){

        Optional<Class> result = classRepository.findById(classId);
        model.addAttribute("class", result.get());
        model.addAttribute("otherStaff", getUnemployedAdmins());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq",true);
        //end of mandatory attributes for myInterface page


        List<AdminPosition> allPositions =
                (List<AdminPosition>) apRepository.findAll();

        List<AdminPosition> positions = new ArrayList<>();

        for(AdminPosition a: allPositions){
            if(!(a.getPosition().toLowerCase().equals("instructor")) && !(a.getPosition().toLowerCase().equals("n/a"))&& !(a.getPosition().toLowerCase().equals("principal"))&& !(a.getPosition().toLowerCase().equals("counselor"))){
                positions.add(a);

            }
        }


        if(positions.size() <1){
            String message = "there are no available administrators to assign.";
            model.addAttribute("rptType", "invalid");
            model.addAttribute("invalid",message);
            model.addAttribute("forSmallWindow", false);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("positions", positions);
        model.addAttribute("courses", sortedCourses());
        model.addAttribute("rptType", "otherStaff");
        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("otherStaff")
    public String processAssignOtherStaffForm(@RequestParam int classId,
                                              @RequestParam List<Integer> otherStaff, @RequestParam List<Integer> positionId ){


        Optional<Class> aClass = classRepository.findById(classId);
        //test aclass to see if it exists

        Class editedClass = aClass.get();

        //find all by ids
        List<Administrator> staff =(List<Administrator>) administratorRepository.findAllById(otherStaff);

        editedClass.setOtherStaff(staff);

        classRepository.save(editedClass);

        //update status for each admin

        //get each staff member and pair it with new position
        HashMap <Administrator,AdminPosition> ap = new HashMap<>();
        for(int i=0; i < otherStaff.size();i++){
            Administrator admins =
                    administratorRepository.findById(otherStaff.get(i)).get();
            AdminPosition adminPosition =
                    apRepository.findById(positionId.get(i)).get();

            ap.put(admins, adminPosition );
        }

        for(Administrator a: ap.keySet()){
            a.setSchool(editedClass.getSchool());
            a.setAdminPosition(ap.get(a));
            a.setDateHired(GetTodaysDate.getDate());
            administratorRepository.save(a);
        }

        //end of admin status updates

        return "redirect:";
    }

    @GetMapping("createCourse")
    public String displayCreateCourseForm(Model model ){

        model.addAttribute("title", "Create a Course");
        model.addAttribute(new Course());
        model.addAttribute("courseGenres", cgRepository.findAll());
        model.addAttribute("courseLevels", courseLevelRepository.findAll());
        model.addAttribute("schools", getSortedSchoolsNoNA());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "createCourse");
        return "site/theBridgeAU/myInterface";
    }

    //processCreateCourseForm goes here
    @RequestMapping("createCourse")
    public String processCreateCourseForm(@ModelAttribute @Valid Course newCourse, Errors errors,@RequestParam(required = false) List<Integer> schoolList, Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if (errors.hasErrors() || schoolList == null){
            model.addAttribute("courseGenres", cgRepository.findAll());
            model.addAttribute("courseLevels", courseLevelRepository.findAll());
            model.addAttribute("schools", getSortedSchoolsNoNA());
            model.addAttribute("rptType", "createCourse");

            if(schoolList == null){
                errors.rejectValue("schools","schools.null", "No schools were " +
                        "choosen" +
                        ".");
            }

            return "site/theBridgeAU/myInterface";
        }

        if(courseRepository.findAll() != null){
            List <Course> result = (List<Course>) courseRepository.findAll();
            for(Course c: result){
                if(c.getCourseName().trim().toLowerCase().equals(newCourse.getCourseName().trim().toLowerCase())){
                    errors.rejectValue("courseName","name.duplicate", "Course" +
                            " with this name already exists.");

                    model.addAttribute("courseGenres", cgRepository.findAll());
                    model.addAttribute("courseLevels", courseLevelRepository.findAll());
                    model.addAttribute("schools", getSortedSchoolsNoNA());
                    model.addAttribute("rptType", "createCourse");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }

        List <School> newList =
                (List<School>) schoolRepository .findAllById(schoolList);


        newCourse.addSchools(newList);
        courseRepository.save(newCourse);
        return "redirect:/myInterface/createCourse";
    }

    //todo work on this and create a separate view for the schoolLinks Report,
    // it should just display school info
    @GetMapping("addCoursesToSchool")
    public String displaySchoolLinks(){

        return "";
    }

    //TODO Request mapping for addCourseToSchool goes here
    //url or post should have the schoolId in the string and should come from
    // the schoolLinks report
    @RequestMapping("addCoursesToSchool")
    public String displayAddCourseForm(@RequestParam int schoolId, Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "putSomethingHere");
        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("createRoster")
    public String displayGetSchoolForCreateRoster(Model model){

        model.addAttribute("schools", getSortedSchoolsNoNA());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "getSchoolForCreateRoster");
        return "site/theBridgeAU/myInterface";

    }

    @RequestMapping("createARoster")
    public String displayCreateRosterForm(@RequestParam int schoolId,
                                          Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        Optional<School> result = schoolRepository.findById(schoolId);

        if (result.isEmpty()){

            model.addAttribute("invalid","School Id does not exist");
            model.addAttribute("rptType", "invalid");
            model.addAttribute("forSmallWindow", false);
            return "site/theBridgeAU/myInterface";
        }

        if(result.get().getClasses().isEmpty()){

            model.addAttribute("invalid","There are no classes assigned to " +
                    "this school yet, please go to 'Create Class' in the Admin" +
                    " Forms section of the navigation pane" +
                    " to start this process.");
            model.addAttribute("rptType", "invalid");
            model.addAttribute("forSmallWindow", false);
            return "site/theBridgeAU/myInterface";
        }

        model.addAttribute("schools",  result.get());
        model.addAttribute("rptType", "getClassForCreateRoster");
        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("createRoster2")
    public String displayStudentsForCreateRoster(@RequestParam int classId,
                                                 Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        Optional<Class> result = classRepository.findById(classId);

        if (result.isEmpty()){

            model.addAttribute("invalid","Class Id does not exist");
            model.addAttribute("rptType", "invalid");
            model.addAttribute("forSmallWindow", false);
            return "site/theBridgeAU/myInterface";
        }
        List<Student> studentList = new ArrayList<>();
        int schoolId = result.get().getSchool().getId();
        //only choose students from the school you are creating a course for
        for(Student student: studentRepository.findAll()){
            if (student.getSchool().getId() == schoolId){
                studentList.add(student);
            }
        }

        //only allow students that are not already assigned to this class
        Class aClass = result.get();
        List<Student> newStudentList = aClass.getStudents();
        List <Student> finalStudentList = new ArrayList<>();

        if(newStudentList.isEmpty()){
            finalStudentList.addAll(studentList);
        } else {

            for(Student aS: studentList){
                if(!newStudentList.contains(aS)){
                    finalStudentList.add(aS);
                }
            }
        }

        model.addAttribute("class", result.get());
        model.addAttribute("students", finalStudentList);
        model.addAttribute("rptType", "createRoster");
        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("createRoster2")
    public String processCreateRosterForm(@RequestParam int classId,
                                          @RequestParam(required = false) List<Integer> studentIds, Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if(studentIds == null){
            model.addAttribute("invalid","No students were selected! Click on " +
                    "the 'Create Roster' link under 'Admin Forms' to start " +
                    "this process again." +
                    " ");
            model.addAttribute("rptType", "invalid");
            model.addAttribute("forSmallWindow", false);
            return "site/theBridgeAU/myInterface";
        }
        Class aClass = classRepository.findById(classId).get();
        List<Student> students = (List<Student>) studentRepository.findAllById(studentIds);


        aClass.setStudents(students);
        classRepository.save(aClass);

        return "redirect:/theBridge/reports/roster?classId="+aClass.getId()+
                "&isMIReq=true";

    }

    //For creating students
    @GetMapping("createStudent")
    public String displayCreateStudentForm(Model model){

        model.addAttribute("title","Create Student");
        model.addAttribute(new Student());
        List <School> schools = (List<School>)  schoolRepository.findAll();
        schools.sort(new SchoolNmComparator());
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("gradeLevels", gradeLevelRepository.findAll());
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "createStudent");
        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("createStudent")
    public String processCreateStudentForm(@ModelAttribute @Valid Student newStudent, Errors errors, @RequestParam int schoolId, Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        if(errors.hasErrors()){

            model.addAttribute("invalid", errors.getAllErrors());

            model.addAttribute("title", "Add Student");
            List <School> schools = (List<School>)  schoolRepository.findAll();
            schools.sort(new SchoolNmComparator());
            model.addAttribute("schools", getSortedSchoolsNoNA());
            model.addAttribute("gradeLevels", gradeLevelRepository.findAll());
            model.addAttribute("rptType", "createStudent");

            return "site/theBridgeAU/myInterface";
        }

        if (studentRepository.findAll() != null) {
            List<Student> result = (List) studentRepository.findAll();
            for (Student s : result) {
                String ln1 = s.getStudentsLastName().trim().toLowerCase();
                String ln2 = newStudent.getStudentsLastName().trim().toLowerCase();
                String fn1 = s.getStudentsFirstName().trim().toLowerCase();
                String fn2 =
                        newStudent.getStudentsFirstName().trim().toLowerCase();
                String mn1 = s.getStudentsMiddleName().trim().toLowerCase();
                String mn2 =
                        newStudent.getStudentsMiddleName().trim().toLowerCase();
                String d1 = s.getDateOfBirth().trim();
                String d2 = newStudent.getDateOfBirth().trim();

                if (ln1.equals(ln2) && fn1.equals(fn2) && mn1.equals(mn2) && d1.equals(d2)) {
                    errors.rejectValue("studentsLastName", "name.duplicate",
                            "Student with this name and birthday " +
                                    "already exists.");

                    model.addAttribute("title", "Add Student");
                    List <School> schools = (List<School>)  schoolRepository.findAll();
                    schools.sort(new SchoolNmComparator());
                    model.addAttribute("schools", getSortedSchoolsNoNA());
                    model.addAttribute("gradeLevels", gradeLevelRepository.findAll());
                    model.addAttribute("rptType", "createStudent");
                    return "site/theBridgeAU/myInterface";
                }
            }
        }
        School s = schoolRepository.findById(schoolId).get();
        newStudent.setSchool(s);
        //used input type = 'file' which only submits the filename so I add
        // the file path to the filename then save it to the database
        String picUrl = "/images/people/" + newStudent.getProfilePic();
        newStudent.setProfilePic(picUrl);
        studentRepository.save(newStudent);
        return "redirect:/theBridge/reports/students?isMIReq=true";
    }


    @GetMapping("getAdmin")
    public String getAdminForEditAdminForm(Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("schools", getSortedSchools());
        model.addAttribute("rptType", "getAdmin");
        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("editAdmin")
    public String displayEditAdminForm(@RequestParam int adminId, Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page

        Optional <Administrator> result =
                administratorRepository.findById(adminId);

        if(result.isEmpty()){

            model.addAttribute("rptType", "invalid");
            model.addAttribute("problem", "There was a problem with your " +
                    "request.");
            model.addAttribute("invalid","Administrator Id was invalid!");
            model.addAttribute("forSmallWindow", false);
            return "site/theBridgeAU/myInterface";
        }

        String adminName =  result.get().getDisplayName();
        model.addAttribute("title",
                "Edit Info for Administrator: " + adminName);
        model.addAttribute("administrator", result.get());
        model.addAttribute("adminPositions", apRepository.findAll());
        model.addAttribute("schools", getSortedSchools());
        model.addAttribute("rptType", "editAdmin");
        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("editAdmin")
    public String processEditAdminForm(@RequestParam Integer adminId,
                                       @RequestParam(required = false) String dateHired ,
                                       @RequestParam(required = false) String dateOfBirth,
                                       @RequestParam(required = false) String expertise, @RequestParam Integer positionId,
                                       @RequestParam Integer schoolId, @RequestParam(required = false) String firstName,
                                       @RequestParam(required = false) String lastName,
                                       @RequestParam(required = false) String middleName, @RequestParam(required = false) String suffix,
            @RequestParam int inmateNum,Model model){

        //get the admin to be edited
        Administrator adminToBeEdited =
                administratorRepository.findById(adminId).get();

        //get the school that the user provides
        School s = schoolRepository.findById(schoolId).get();

        /*compare the previous school to the new one to see if there was change
        if so then apply the change */
        if(adminToBeEdited.getSchool().getId() != s.getId()){
            adminToBeEdited.setSchool(s);
        }

        //get the admin position that the user provides
        AdminPosition ap = apRepository.findById(positionId).get();

        int naId = 0;
        if(adminToBeEdited.getSchool().getSchoolName().equals("N/A")){
            List<AdminPosition> list =
                    (List<AdminPosition>) apRepository.findAll();
            for(int i=0 ; i < list.size(); i++){
                if(list.get(i).getPosition().equals("N/A")){
                    naId = i;
                    break;
                }
            }
            /*if school is n/a the administrator is considered unemployed so
            the position must also be set to N/A and date hired set to null*/
            adminToBeEdited.setAdminPosition(list.get(naId));
            adminToBeEdited.setDateHired(null);

             /*compare the previous position to the new one to see if there was change
        if so then apply the change */
        } else if (adminToBeEdited.getAdminPosition().getId() != ap.getId()){
            adminToBeEdited.setAdminPosition(ap);
        }

        //apply other changes if they are there
        if(!dateOfBirth.isBlank()){
            adminToBeEdited.setDateOfBirth(dateOfBirth);
        }
        //checking to be sure that the naid is 0 because if it is not,
        // dateHired should have already been set to null
        if((!dateHired.isBlank()) && naId == 0){
            adminToBeEdited.setDateHired(dateHired);
        }
        if(firstName != null){
            adminToBeEdited.getUser().setFirstName(firstName);
        }
        if(lastName != null){
            adminToBeEdited.getUser().setLastName(lastName);
        }
        if(middleName != null){
            adminToBeEdited.getUser().setMiddleName(middleName);
        }

        if(suffix != null){
            adminToBeEdited.getUser().setSuffix(suffix);
        }
        if(inmateNum >=1 ){
            adminToBeEdited.getUser().setInmateNumber(inmateNum);
        }
        if(expertise != null){
            adminToBeEdited.setExpertise(expertise);
        }

        //save changes to the repository
        administratorRepository.save(adminToBeEdited);

        return "redirect:/theBridge/reports/admin/" + adminToBeEdited.getId()+
                "?isMIReq=true";

    }


    @GetMapping("getSchool")
    public String displayGetSchoolForGradingAssignments(Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "getSchoolForGradingAssignments");
        model.addAttribute("schools", getSortedSchoolsNoNA());
        model.addAttribute("isMIReq", true);

        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("getSchool")
    public String displayGetClassForGradingAssignments(@RequestParam int schoolId, Model model){



        School school =schoolRepository.findById(schoolId).get();
        List<Class> classes =school.getClasses();

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("isMIReq", true);

        //todo if classes is < 1 then return invalid w/message
        if(classes.size() < 1){
            model.addAttribute("rptType", "invalid");
            model.addAttribute("invalid", "There are no classes currently " +
                    "assigned to this school.");
            model.addAttribute("forSmallWindow", false);
        } else {

            model.addAttribute("rptType", "getClassForGradingAssignments");
            model.addAttribute("schools", school);
        }

        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("getClass")
    public String displayGetAssignmentForGradingAssignments(@RequestParam int classId,
                                                            Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType", "getAnAssignment");
        Class aClass = classRepository.findById(classId).get();
        model.addAttribute("class", aClass);
        model.addAttribute("isMIReq",true);
        return "site/theBridgeAU/myInterface";
    }

    @GetMapping("gradeAnAssignment")
    public String displayGradeAssignmentsForm(@RequestParam int assignmentId,
                                              Model model){
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        //end of mandatory attributes for myInterface page
        model.addAttribute("isMIReq", true);
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
        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("gradeAnAssignment")
    public String processGradeAssignmentForm(HttpServletRequest request,
                                             @ModelAttribute @Valid AssignmentStatus newAssignmentStatus, Errors errors,
                                             @RequestParam int assignmentId, @RequestParam int studentId, @RequestParam int statusId,
                                             Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", true);
        //end of mandatory attributes for myInterface page
        if(errors.hasErrors()){

            model.addAttribute("rptType", "gradeAnAssignment");
            model.addAttribute("assignment",
                    classAssignmentRepository.findById(assignmentId).get());
            model.addAttribute("statusList",statusRepository.findAll());
            model.addAttribute("message", errors.getAllErrors());
            return "site/theBridgeAU/myInterface";
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

                model.addAttribute("rptType", "gradeAnAssignment");
                model.addAttribute("assignment",
                        classAssignmentRepository.findById(assignmentId).get());
                model.addAttribute("statusList",statusRepository.findAll());

                return "site/theBridgeAU/myInterface";
            }
        }

        model.addAttribute("rptType", "");
        return "site/theBridgeAU/myInterface";
    }


    @GetMapping("appendRelationship")
    public String displayAppendRelationshipForm(Model model){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", true);
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType","appendRelationship");

        //get users without an assigned studentID
        model.addAttribute("users",
                userRepository.findByStudentIsNullOrderByFirstNameAsc());
        //get all students
         model.addAttribute("students",studentRepository.findAll());

        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("appendRelationship")
    public String processAppendRelationshipForm(@RequestParam int userId,
                                              @RequestParam int studentId,
                                              Model model ){

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", true);
        //end of mandatory attributes for myInterface page

        User u = userRepository.findById(userId).get();
        Student s = studentRepository.findById(studentId).get();

        u.setStudent(s);
        userRepository.save(u);

        String subject = "Bridge-link Update for " + u.getDisplayName() + " " +
                "and " + s.getFullName();
        String message = "The requested Bridge-link has been set up and you " +
                "can now have access to your child information by clicking on the " +
                "grades button on the navigation menu pane in parentview";

        return "redirect:/theBridge/messenger/siteMessage?messageType" +
                "=bridgeLinkUpdate" +
                "&message=" + message+"&subject="+subject+"&recipientId="+u.getId()+"&isMIReq=true";

    }


    @GetMapping("editRelationship")
    public String displayEditRelationshipForm(Model model){

        /*todo: add functionality for edit relationshipForm
         *  need to getUser ID, findByUserID , assign User object and Student object
         *  to the form using th:selected="${}"*/

        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", true);
        //end of mandatory attributes for myInterface page
        model.addAttribute("rptType","editRelationship");

        //get users with an assigned studentID
        model.addAttribute("users",
                userRepository.findByStudentIsNotNullOrderByFirstNameAsc());
        //get all students
       // model.addAttribute("students",studentRepository.findAll());

        return "site/theBridgeAU/myInterface";
    }

    @RequestMapping("editRelationship")
    public String processEditRelationshipForm(@RequestParam int userId,
                                              @RequestParam int studentId,
                                              Model model ){

        User u = userRepository.findById(userId).get();
        Student s = studentRepository.findById(studentId).get();
        //mandatory attributes for myInterface page navPane
        model.addAttribute("reports", getAllReportNames());
        model.addAttribute("rptKeys",getSortedReportNames());
        model.addAttribute("forms", getAllFormNames());
        model.addAttribute("frmKeys", getSortedFormNames1());
        model.addAttribute("frmKeys2", getSortedFormNames2());
        model.addAttribute("frmKeys3", getSortedFormNames3());
        model.addAttribute("isMIReq", true);
        //end of mandatory attributes for myInterface page

        Optional <Integer> result =
                Optional.of(userRepository.findById(userId).get().getStudent().getId());

            //todo check to see if rel has been est. already
            if(u.getStudent().getId() == s.getId()){
                String message = "User/Student Relationship already exists";
                model.addAttribute("users",userRepository.findByStudentIsNullOrderByFirstNameAsc());
                model.addAttribute("students",studentRepository.findAll());
                model.addAttribute("rptType","invalid");
                model.addAttribute("invalid", message);
                return "site/theBridgeAU/myInterface";

            }


        u.setStudent(s);
        userRepository.save(u);
        //todo send message to the parent informing them of newly established rel
        //do so using the messenger function and it should return to edit relForm
        model.addAttribute("rptType","editRelationship");
        return "site/theBridgeAU/myInterface";
    }

}
