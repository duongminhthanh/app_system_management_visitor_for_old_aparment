package com.example.app_system_management_visitors_for_old_aparment;

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

    @Override
    public String toString() {
        return "Feedback{" +
                "name='" + name + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
