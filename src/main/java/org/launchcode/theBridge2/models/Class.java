package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Entity
public class Class extends AbstractEntity{
    //@NotNull
    @ManyToOne
    private School school;

    //@Valid
    @NotNull(message = "Course cannot be null")
    @ManyToOne
    private Course course;

    @Min(value = 10, message = "Class must be at least 10 students")
    private int maxStudents;

    @NotNull(message = "Instructor 1 cannot be null")
    @ManyToOne
    private Administrator instructor; //link to administrators table

    @ManyToMany
    private List<Administrator> otherStaff = new ArrayList<>();

    @ManyToOne
    @NotNull(message = "School year cannot be null")
    private SchoolYear schoolYear;

    @NotNull(message="Must enter a semester")
    @NotBlank(message="Must enter a semester")
    @Size(min=1,max = 2, message = "Semester options are 'FW' or 'SS'")
    private String semester; //F; SP; S;

    @OneToMany(mappedBy = "aClass")
    private List <ClassAssignment> assignments;


    @ManyToMany
    private List<Student> students = new ArrayList<>();

    public Class() {}

    public Class(School school,Course course, SchoolYear schoolYear,
                 String semester, int maxStudents, Administrator instructor) {
        this.school = school;
        this.course = course;
        this.schoolYear = schoolYear;
        this.semester = semester;
        this.maxStudents = maxStudents;
        this.instructor = instructor;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Administrator getInstructor() {
        return instructor;
    }

    public void setInstructor(Administrator instructor) {
        this.instructor = instructor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Administrator> getOtherStaff() {
        return otherStaff;
    }

    public void setOtherStaff(List<Administrator> otherStaff) {
        this.otherStaff = otherStaff;
    }

    public List<ClassAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ClassAssignment> assignments) {
        this.assignments = assignments;
    }

    public String getSemesterInfo(){
        String nm =
                this.course.getDisplayName()+" - "+getSemester()+getSchoolYear().getYear();
        return nm;
    }

    public String getSemesterStr(){
        String nm = getSemester()+getSchoolYear().getYear();
        return nm;
    }

    public String getInstructorInfo(){
        String nm = "Instructor: "+ this.getInstructor().getDisplayName();
        return nm ;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students){
        for(Student s: students){
            if(!this.students.contains(s)){
                this.students.add(s);
            }
        }
    }
    public void editStudents(List<Student> students) {
        this.students = students;
    }
    public int numberOfStudents(){
        int count = 0;
        for(Student s: this.getStudents()){
            count++;
        }
        return count;
    }
    public String getTodaysDate(){
        Calendar c = Calendar.getInstance();
        String today =
                (c.get(c.YEAR)+ "-"+c.get(c.MONTH)+1+"-"+ c.get(c.DATE)) ;
        return today;
    }

    public int getTotalNumberOfAssignments(){
        int count = 0;
        for(ClassAssignment c : this.assignments){
            //todo: count only assignments that get past or equal to the due date
            count++;
        }
        return count;
    }


    public HashMap<String, HashMap<String,AssignmentStatus>> assignmentScores(){
        //k=studentName, v=HashMap<k=assignmentName,v=assignmentStatus
        HashMap<String,HashMap<String,AssignmentStatus>> data =
                new HashMap<>();

        //todo if there are no assignments yet
        if(this.getAssignments()==null){

        }
        //todo if there are no students assigned yet
        if(this.getStudents() == null){

        }

        //initialize the hash map with student names and assignment names
        for(Student s: this.getStudents()){
            HashMap<String, AssignmentStatus> innerData = new HashMap<>();
            for(ClassAssignment ca: this.getAssignments()){
                innerData.put(ca.getAssignmentName(),
                        new AssignmentStatus(ca,s,new Status("N/A","Assignment" +
                                " has not yet been graded","/images/statusImgs/stillWorking.png"),"","",0.0));

            }
            data.put(s.getFullName(),innerData);
        }


        for(ClassAssignment ca: this.getAssignments()){
            for(AssignmentStatus status: ca.getAssignmentStatuses()){
                String nmCheck = status.getStudent().getFullName();
                String assCheck =
                        status.getClassAssignment().getAssignmentName();
                for(String name:data.keySet()){
                    for(String ass: data.get(name).keySet()){
                        if(name.equals(nmCheck)&& ass.equals(assCheck)){
                            data.get(name).put(ass,status);
                        }
                    }
                }
            }
        }


        return data;
    }

    public HashMap<String,AssignmentStatus> aSetOfAssignmentScores(String studentsFullName){
        return assignmentScores().get(studentsFullName);
    }

    public HashMap<String, Double> studentAvgs(){
        //k=studentName v=classAvg
        HashMap<String, Double> cumulativeGrades = new HashMap<>();

        //initialize hashMap
        for(Student s: getStudents()){
            cumulativeGrades.put(s.getFullName(),0.0);
        }

        if(this.getAssignments() == null || this.getAssignments().isEmpty()){
            return cumulativeGrades;
        }
        //assignmentScores() = k=studentName, v=HashMap<k=assignmentName,v=assignmentStatus

        //sum assignment scores
        for (String studentNm: assignmentScores().keySet()){
            if(assignmentScores().get(studentNm) == null){
                cumulativeGrades.put(studentNm,
                        cumulativeGrades.get(studentNm)+0.0);
                break;
            }
            for(HashMap<String,AssignmentStatus> assignmentInfo:
                    assignmentScores().values()){
                for(AssignmentStatus as: assignmentInfo.values()){
                    double aValue = as.getStudentScore();
                    if(as.getStudent().getFullName().equals(studentNm)){
                        cumulativeGrades.put(studentNm,
                                cumulativeGrades.get(studentNm)+aValue);
                    }
                }
            }
        }
        //calculate avg
        for(String nm: cumulativeGrades.keySet()){
            double calculatedAvg =
                    cumulativeGrades.get(nm)/getTotalNumberOfAssignments();
            cumulativeGrades.put(nm, calculatedAvg);
        }

        return cumulativeGrades;
    }


    public  HashMap<String,HashMap<String, Double>>  allStudentsAssignmentScores(){

        HashMap<String,HashMap<String, Double>> studentScores = new HashMap<>();

        for(String s: assignmentScores().keySet()){
            HashMap<String, Double> sg = new HashMap<>();
            for(AssignmentStatus as: assignmentScores().get(s).values()){
                sg.put(as.getClassAssignment().getAssignmentName(),
                        as.getStudentScore());
            }
            studentScores.put(s,sg);
        }
        return studentScores;

    }

    public HashMap<String, Double> aStudentAssignmentScores(String studentsFullName){
        HashMap result = new HashMap();
        result.put("N/A",0.0);
        if(allStudentsAssignmentScores().get(studentsFullName).isEmpty()){
            return  result;
        }
        return allStudentsAssignmentScores().get(studentsFullName);
    }


    public Double getAStudentsClassAverage(String studentsFullName){
        /**this method works fine. If you want to plug in a name as an arg, you
         // must account for the way that the student.getFullName() method puts
         // together the full name, spaces included. If you use the method with
         // any other features of the app, make sure that you are using the
         // program to get the full name to plug in as a parameter opposed to
         // doing it manually!!!*/
        if(studentAvgs().get(studentsFullName) == null){
            return 0.0;
        }
        Double avg = studentAvgs().get(studentsFullName);
        avg = Math.floor(avg);
        return  avg;
    }

}
