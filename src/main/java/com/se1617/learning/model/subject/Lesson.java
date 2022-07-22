/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.subject;

import com.se1617.learning.model.quizzes.Quizzes;
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
public class Lesson {
    private int id;
    private String name;
    private int order;
    private boolean status;
    private String video;
    private String content;
    private int typeid;
    private Setting type;
    private int subjectId;
    private Subject subject;
    private int topicId;
    private Lesson topic;
    private int quizzesid;
    private Quizzes quizzes;
    private ArrayList<PackagePrice> packagePrices;
    private ArrayList<Lesson> lessons;
    private boolean isLearning;

    public boolean checkPackagePrice(int priceId) {
        if (packagePrices != null) {
            for (PackagePrice packagePrice : packagePrices) {
                if (packagePrice.getId() == priceId) {
                    return true;
                }
            }
        }
        return false;
    }
}
