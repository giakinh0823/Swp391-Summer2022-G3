/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.question;

import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.model.user.User;
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
public class Question {

    private int id;
    private String content;
    private String contentHtml;
    private boolean status;
    private boolean is_multi;
    private String explain;
    private int dimensionId;
    private Dimension dimension;
    private int subjectId;
    private Subject subject;
    private int lessonId;
    private Lesson lesson;
    private int levelId;
    private Setting level;
    private int userId;
    private User user;
    private ArrayList<AnswerQuestion> answers;
    private Media media;
}
