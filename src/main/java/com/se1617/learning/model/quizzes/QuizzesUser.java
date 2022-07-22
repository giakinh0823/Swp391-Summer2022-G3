/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.quizzes;

import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Lesson;
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
public class QuizzesUser {
    private int lessonId;
    private Lesson lesson;
    private int userId;
    private User user;
    private Quizzes quizzes;
    private int quizzesId;
    private String description;
    private int typeId;
    private Setting type;
    private int levelId;
    private Setting level;
     private String name;
    private int duration;
    private int pass_rate;
    private Timestamp start_time;
    private Timestamp end_time;
}
