package org.example.model;

public class Student {
    private int id;
    private String name;
    
    public Student (int id, String name){
        this.name = name;
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getId(){
        return this.id;
    }
    
    @Override
    public String toString(){
        return this.name + " (" + this.id + ")";
    }
    
}
