/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.setting;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giaki
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    private int id;
    private String type;
    private String value;
    private int order;
    private boolean status;
    private String description;
    private int parent;
    private Setting setting;
    ArrayList<Setting> childs;
}
