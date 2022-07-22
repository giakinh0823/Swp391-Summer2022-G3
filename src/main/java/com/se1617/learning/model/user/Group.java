/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.user;

import com.se1617.learning.model.setting.Setting;
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
public class Group extends Setting {

    ArrayList<Feature> features;

    public boolean checkFeature(int idFeature) {
        for (Feature feature : features) {
            if (feature.getId() == idFeature) {
                return true;
            }
        }
        return false;
    }
}
