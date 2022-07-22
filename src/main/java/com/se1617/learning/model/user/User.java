/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.user;

import com.se1617.learning.model.registration.Registration;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giaki
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String first_name;
    private String last_name;
    private boolean gender;
    private String avatar;
    private boolean is_super;
    private boolean is_staff;
    private boolean enable;
    private boolean is_active;
    private Timestamp created_at;
    private ArrayList<Group> groups;
    private ArrayList<Registration> registers;
    
    public boolean checkRegistration(int courseId) {
        if (registers != null) {
            for (Registration registration : registers) {
                if(registration.getSubjectId()==courseId && registration.isStatus()==true){
                    return true;
                }
            }
        }
        return false;
    }
    
     public boolean checkRegistrationPending(int courseId) {
        if (registers != null) {
            for (Registration registration : registers) {
                if(registration.getSubjectId()==courseId){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasGroupActive() {
        for (Group group : groups) {
            if (group.isStatus()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkGroup(String... names) {
        if (groups != null) {
            for (Group group : groups) {
                for (String name : names) {
                    if (group.getValue().equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkFeature(String... features) {
        int count = 0;
        if (groups != null) {
            for (Group group : groups) {
                for (Feature item : group.getFeatures()) {
                    for (String feature : features) {
                        if (item.getFeature().equalsIgnoreCase(feature)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count == features.length;
    }
}
