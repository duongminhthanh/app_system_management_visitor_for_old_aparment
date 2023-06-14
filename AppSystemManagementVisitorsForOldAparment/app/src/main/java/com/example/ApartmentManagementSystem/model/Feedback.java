package com.example.ApartmentManagementSystem.model;
public class Feedback {
    String name,feedback;
    public Feedback() {
        this.name=getName();
        this.feedback=getFeedback();
    }
    public Feedback(String name, String feedback) {
        this.name = name;
        this.feedback = feedback;
    }
    public String getName() {
        return name;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    @Override
    public String toString() {
        return "Feedback{" +
                "name='" + name + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
