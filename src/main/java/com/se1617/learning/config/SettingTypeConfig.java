/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config;

import com.se1617.learning.model.setting.Type;
import java.util.ArrayList;

/**
 *
 * @author giaki
 */
public class SettingTypeConfig {

    public static final ArrayList<Type> types = new ArrayList<>();

    private static SettingTypeConfig instance = new SettingTypeConfig();

    private SettingTypeConfig() {
        types.add(new Type(1, "User role", "USER_ROLE"));
        types.add(new Type(2, "Category subject", "CATEGORY_SUBJECT"));
        types.add(new Type(3, "Type dimension", "TYPE_DIMENSION"));
        types.add(new Type(4, "Level question", "LEVEL_QUESTION"));
        types.add(new Type(5, "Level quizzes", "LEVEL_QUIZZES"));
        types.add(new Type(6, "Type quizzes", "TYPE_QUIZZES"));
        types.add(new Type(7, "Category post", "CATEGORY_POST"));
        types.add(new Type(8, "Type lesson", "TYPE_LESSON"));
    }

    public static SettingTypeConfig getInstance() {
        return instance;
    }
}
