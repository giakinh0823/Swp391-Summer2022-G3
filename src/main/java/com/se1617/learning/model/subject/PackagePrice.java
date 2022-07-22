/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.subject;

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
public class PackagePrice {
    private int id;
    private String name;
    private int duration;
    private double list_price;
    private double sale_price;
    private boolean status;
    private String description;
    private int subjectId;
    private Subject subject;
}
