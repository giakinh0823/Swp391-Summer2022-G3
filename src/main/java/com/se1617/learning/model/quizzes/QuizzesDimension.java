/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.quizzes;

import com.se1617.learning.model.subject.Dimension;
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
public class QuizzesDimension{
    private int dimensionId;
    private int settingQuizzesId;
    private int numberQuestion;
    private Dimension dimension;
}
