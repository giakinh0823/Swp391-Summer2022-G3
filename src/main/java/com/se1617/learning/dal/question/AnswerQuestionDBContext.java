/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.AnswerQuestion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class AnswerQuestionDBContext extends DBContext<AnswerQuestion> {

    public ArrayList<AnswerQuestion> findByQuestionid(int questionId) {
        ArrayList<AnswerQuestion> answerQuestions = new ArrayList<>();
        try {
            String sql = "SELECT [answer].[id]\n"
                    + "      ,[answer].[text]\n"
                    + "      ,[answer].[media]\n"
                    + "      ,[answer].[subjectid]\n"
                    + "	  ,[question_answer].[is_correct]\n"
                    + "  FROM [answer]\n"
                    + "  INNER JOIN [question_answer] ON [question_answer].[answerid] = [answer].[id]\n"
                    + "  WHERE [question_answer].[questionid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, questionId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AnswerQuestion answerQuestion = new AnswerQuestion();
                answerQuestion.setId(rs.getInt("id"));
                answerQuestion.setText(rs.getString("text"));
                answerQuestion.setMedia(rs.getString("media"));
                answerQuestion.set_correct(rs.getBoolean("is_correct"));
                answerQuestions.add(answerQuestion);
            }
            return answerQuestions;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<AnswerQuestion> findAnswerId(int answerId) {
        ArrayList<AnswerQuestion> answerQuestions = new ArrayList<>();
        try {
            String sql = "SELECT [answer].[id]\n"
                    + "      ,[answer].[text]\n"
                    + "      ,[answer].[media]\n"
                    + "      ,[answer].[subjectid]\n"
                    + "	  ,[question_answer].[is_correct]\n"
                    + "	  ,[question_answer].[questionid]\n"
                    + "  FROM [answer]\n"
                    + "  INNER JOIN [question_answer] ON [question_answer].[answerid] = [answer].[id]\n"
                    + "  WHERE [question_answer].[answerid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, answerId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AnswerQuestion answerQuestion = new AnswerQuestion();
                answerQuestion.setId(rs.getInt("id"));
                answerQuestion.setText(rs.getString("text"));
                answerQuestion.setMedia(rs.getString("media"));
                answerQuestion.set_correct(rs.getBoolean("is_correct"));
                answerQuestion.setQuestionId(rs.getInt("questionid"));
                answerQuestions.add(answerQuestion);
            }
            return answerQuestions;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<AnswerQuestion> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AnswerQuestion get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insertMulti(ArrayList<AnswerQuestion> list) {
        String sql = "INSERT INTO [question_answer]\n"
                + "           ([answerid]\n"
                + "           ,[questionid]\n"
                + "           ,[is_correct])\n"
                + "     VALUES(?,?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (AnswerQuestion answerQuestion : list) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, answerQuestion.getAnswerId());
                stm.setInt(2, answerQuestion.getQuestionId());
                stm.setBoolean(3, answerQuestion.is_correct());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public AnswerQuestion insert(AnswerQuestion model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(AnswerQuestion model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void deleteByQuestionId(int id) {
        try {
            String sql = "DELETE FROM [question_answer]\n"
                    + "      WHERE [question_answer].[questionid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
