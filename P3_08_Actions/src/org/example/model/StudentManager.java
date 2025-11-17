package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    
    private List<Student> students;
    
    public StudentManager(){
        students = new ArrayList<>();
        students.add(new Student(12345, "Aliya"));
        students.add(new Student(23451, "Auntumn"));
        students.add(new Student(34512, "Leo"));
        students.add(new Student(45123, "Zac"));
        students.add(new Student(51234, "Liann"));
        students.add(new Student(54321, "Olly"));
        students.add(new Student(43215, "Paysley"));
        students.add(new Student(32154, "Nova"));
    }
    
    public List<Student> searchStudents(String searchString) {
        List<Student> matches = new ArrayList<>();
        
        if (searchString == null) {
            return matches;
        }
        
        for (Student s : students){
            if (s.getName().contains(searchString))
                matches.add(s);
        }
        return matches;
    }
    
    
    
}
