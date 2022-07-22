/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.question;

import com.se1617.learning.model.quizzes.Quizzes;
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
public class UserChoose {
    private int lessonId;
    private Lesson lesson;
    private int userId;
    private User user;
    private Quizzes quizzes;
    private int quizzesId;
    private int questionId;
    private Question question;
    private int answerId;
    private Answer answer;
    private Timestamp created_at;
}
