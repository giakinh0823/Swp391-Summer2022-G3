/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.subject;

import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.user.User;
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
public class Subject {
    private int id;
    private String name;
    private String description;
    private String image;
    private boolean status;
    private boolean featured;
    private int categoryId;
    private Setting category;
    private int userId;
    private User user;
    private Timestamp created_at;
    private Timestamp updated_at;
    private ArrayList<PackagePrice> packegePrices;
    private ArrayList<Lesson> lessons;

    public PackagePrice getLowPrice() {
        if (packegePrices != null && !packegePrices.isEmpty()) {
            PackagePrice packagePrice = packegePrices.get(0);
            for (PackagePrice item : packegePrices) {
                if (item.getSale_price() < packagePrice.getSale_price()) {
                    packagePrice = item;
                }
            }
            return packagePrice;
        }
        return null;
    }
    
    public int getTotalLesson(){
        if(lessons!=null){
            int count = 0;
            for (Lesson lesson : lessons) {
                if(lesson.getLessons()!=null){
                     count += lesson.getLessons().size();
                }
            }
            return count;
        }
        return 0;
    }
}
