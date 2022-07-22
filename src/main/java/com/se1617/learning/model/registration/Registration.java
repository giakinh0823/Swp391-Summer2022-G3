/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.registration;

import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.model.user.User;
import java.sql.Timestamp;
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
public class Registration {
    private int id;
    private boolean status;
    private double total_cost;
    private Timestamp valid_from;
    private Timestamp valid_to;
    private int subjectId;
    private Subject subject;
    private int userId;
    private User user;
    private int customerId;
    private Customer customer;
    private Timestamp created_at;
    private int update_by;
    private User updateBy;
    private int priceId;
    private PackagePrice price;
}
