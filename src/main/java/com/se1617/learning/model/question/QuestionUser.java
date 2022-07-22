/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.question;

import com.se1617.learning.model.quizzes.Quizzes;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.Subject;
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
public class QuestionUser {

    private int questionId;
    private Question question;
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
    private Quizzes quizzes;
    private int quizzesId;
    private ArrayList<AnswerUser> answers;
    private String media;
    private ArrayList<UserChoose> userChooses;

    public boolean checkUserChoose(int userChooseId) {
        if (userChooses != null) {
            for (UserChoose userChoose : userChooses) {
                if (userChoose.getAnswerId() == userChooseId) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCorrect(int userChooseId) {
        if (answers != null) {
            for (AnswerUser answer : answers) {
                if (answer.getAnswerId() == userChooseId && answer.is_correct()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCorrect() {
        int count = 0;
        if (answers != null && userChooses != null) {
            for (UserChoose userChoose : userChooses) {
                for (AnswerUser answer : answers) {
                    if (answer.getAnswerId() == userChoose.getAnswerId() && answer.is_correct()) {
                        count++;
                    }
                }
            }
        }
        return userChooses!=null && !userChooses.isEmpty() && count == userChooses.size() ;
    }
}
