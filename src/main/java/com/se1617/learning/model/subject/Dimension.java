/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.subject;

import com.se1617.learning.model.setting.Setting;
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
public class Dimension {
    private int id;
    private String name;
    private String description;
    private int subjectId;
    private Subject subject;
    private int typeId;
    private Setting type;
}
