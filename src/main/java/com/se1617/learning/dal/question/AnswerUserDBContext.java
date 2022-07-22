/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.AnswerUser;
import com.se1617.learning.model.question.QuestionUser;
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
public class AnswerUserDBContext extends DBContext<AnswerUser> {

    @Override
    public ArrayList<AnswerUser> list() {
        ArrayList<AnswerUser> answerUsers = new ArrayList<>();
        String sql = "SELECT [userid]\n"
                + "      ,[quizzesid]\n"
                + "      ,[lessonid]\n"
                + "      ,[questionid]\n"
                + "      ,[answerid]\n"
                + "      ,[text]\n"
                + "      ,[media]\n"
                + "      ,[is_correct]\n"
                + "  FROM [swp_learning].[dbo].[answer_user]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AnswerUser answerUser = new AnswerUser();
                answerUser.setQuestionId(rs.getInt("questionid"));
                answerUser.setUserId(rs.getInt("userid"));
                answerUser.setLessonId(rs.getInt("lessonid"));
                answerUser.setQuestionId(rs.getInt("quizzesid"));
                answerUser.setAnswerId(rs.getInt("answerid"));
                answerUser.setText(rs.getString("text"));
                answerUser.setMedia(rs.getString("media"));
                answerUser.set_correct(rs.getBoolean("is_correct"));
                answerUsers.add(answerUser);
            }
            return answerUsers;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<AnswerUser> findByUserAndLessonAndQuizzesAndQuestion(int userId, int lessonId, int quizzId, int questionId) {
        ArrayList<AnswerUser> answerUsers = new ArrayList<>();
        String sql = "SELECT [userid]\n"
                + "      ,[quizzesid]\n"
                + "      ,[lessonid]\n"
                + "      ,[questionid]\n"
                + "      ,[answerid]\n"
                + "      ,[text]\n"
                + "      ,[media]\n"
                + "      ,[is_correct]\n"
                + "  FROM [answer_user]"
                + " WHERE userid = ? AND lessonid = ? AND quizzesid = ? AND questionid = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzId);
            stm.setInt(4, questionId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AnswerUser answerUser = new AnswerUser();
                answerUser.setQuestionId(rs.getInt("questionid"));
                answerUser.setUserId(rs.getInt("userid"));
                answerUser.setLessonId(rs.getInt("lessonid"));
                answerUser.setQuestionId(rs.getInt("quizzesid"));
                answerUser.setAnswerId(rs.getInt("answerid"));
                answerUser.setText(rs.getString("text"));
                answerUser.setMedia(rs.getString("media"));
                answerUser.set_correct(rs.getBoolean("is_correct"));
                answerUsers.add(answerUser);
            }
            return answerUsers;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public AnswerUser get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AnswerUser insert(AnswerUser model) {
        String sql = "INSERT INTO [dbo].[answer_user]\n"
                + "           ([userid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[lessonid]\n"
                + "           ,[questionid]\n"
                + "           ,[answerid]\n"
                + "           ,[text]\n"
                + "           ,[media]\n"
                + "           ,[is_correct])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getUserId());
            stm.setInt(2, model.getQuizzesId());
            stm.setInt(3, model.getLessonId());
            stm.setInt(4, model.getQuestionId());
            stm.setInt(5, model.getAnswerId());
            stm.setString(6, model.getText());
            stm.setString(7, model.getMedia());
            stm.setBoolean(8, model.is_correct());
            stm.executeUpdate();
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public ArrayList<QuestionUser> insertMany(ArrayList<QuestionUser> list) {
        String sql = "INSERT INTO [dbo].[answer_user]\n"
                + "           ([userid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[lessonid]\n"
                + "           ,[questionid]\n"
                + "           ,[answerid]\n"
                + "           ,[text]\n"
                + "           ,[media]\n"
                + "           ,[is_correct])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (QuestionUser questionUser : list) {
                deleteByUserAndLessonAndQuizzesAndQuestion(questionUser.getUserId(), questionUser.getLessonId(), questionUser.getQuizzesId(), questionUser.getQuestionId());
                for (AnswerUser model : questionUser.getAnswers()) {
                    stm = connection.prepareStatement(sql);
                    stm.setInt(1, model.getUserId());
                    stm.setInt(2, model.getQuizzesId());
                    stm.setInt(3, model.getLessonId());
                    stm.setInt(4, model.getQuestionId());
                    stm.setInt(5, model.getAnswerId());
                    stm.setString(6, model.getText());
                    stm.setString(7, model.getMedia());
                    stm.setBoolean(8, model.is_correct());
                    stm.executeUpdate();
                }
            }
            connection.commit();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void deleteByUserAndLessonAndQuizzesAndQuestion(int userId, int lessonId, int quizzesId, int questionId) {
        try {
            String sql = "DELETE FROM [answer_user]\n"
                    + "      WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ? AND [questionid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            stm.setInt(4, questionId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public void deleteByUserAndLessonAndQuizzes(int userId, int lessonId, int quizzesId) {
        try {
            String sql = "DELETE FROM [answer_user]\n"
                    + "      WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(AnswerUser model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
