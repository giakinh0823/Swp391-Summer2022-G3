/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.slider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author lanh0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Slider {
    private int id;
    private String title;
    private String image;
    private String backlink;
    private boolean status;
    private String notes;
   
}
