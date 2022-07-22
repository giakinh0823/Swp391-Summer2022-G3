/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.AnswerUser;
import com.se1617.learning.model.question.QuestionUser;
import com.se1617.learning.model.question.UserChoose;
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
public class UserChooseDBContext extends DBContext<UserChoose> {

    @Override
    public ArrayList<UserChoose> list() {
        ArrayList<UserChoose> userChooses = new ArrayList<>();
        String sql = "SELECT [userid]\n"
                + "      ,[quizzesid]\n"
                + "      ,[lessonid]\n"
                + "      ,[questionid]\n"
                + "      ,[answerid]\n"
                + "      ,[created_at]\n"
                + "  FROM [user_choose]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                UserChoose userChoose = new UserChoose();
                userChoose.setQuestionId(rs.getInt("questionid"));
                userChoose.setUserId(rs.getInt("userid"));
                userChoose.setLessonId(rs.getInt("lessonid"));
                userChoose.setQuizzesId(rs.getInt("quizzesid"));
                userChoose.setAnswerId(rs.getInt("answerid"));
                userChoose.setCreated_at(rs.getTimestamp("created_at"));
                userChooses.add(userChoose);
            }
            return userChooses;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<UserChoose> findByUserAndLessonAndQuizAndQuestion(int userId, int lessonId, int quizzId, int questionId) {
        ArrayList<UserChoose> userChooses = new ArrayList<>();
        String sql = "SELECT [userid]\n"
                + "      ,[quizzesid]\n"
                + "      ,[lessonid]\n"
                + "      ,[questionid]\n"
                + "      ,[answerid]\n"
                + "      ,[created_at]\n"
                + "  FROM [user_choose]"
                + " WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ? AND [questionid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzId);
            stm.setInt(4, questionId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                UserChoose userChoose = new UserChoose();
                userChoose.setQuestionId(rs.getInt("questionid"));
                userChoose.setUserId(rs.getInt("userid"));
                userChoose.setLessonId(rs.getInt("lessonid"));
                userChoose.setQuizzesId(rs.getInt("quizzesid"));
                userChoose.setAnswerId(rs.getInt("answerid"));
                userChoose.setCreated_at(rs.getTimestamp("created_at"));
                userChooses.add(userChoose);
            }
            return userChooses;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public UserChoose get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UserChoose insert(UserChoose model) {
        String sql = "INSERT INTO [user_choose]\n"
                + "           ([userid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[lessonid]\n"
                + "           ,[questionid]\n"
                + "           ,[answerid]\n"
                + "           ,[created_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getUserId());
            stm.setInt(2, model.getQuizzesId());
            stm.setInt(3, model.getLessonId());
            stm.setInt(4, model.getQuestionId());
            stm.setInt(5, model.getAnswerId());
            stm.setTimestamp(6, model.getCreated_at());
            stm.executeUpdate();
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void insertMany(ArrayList<UserChoose> list) {
        if (list != null && !list.isEmpty()) {
            deleteByUserAndLessonAndQuizzes(list.get(0).getUserId(), list.get(0).getLessonId(), list.get(0).getQuizzesId());
        }
        String sql = "INSERT INTO [user_choose]\n"
                + "           ([userid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[lessonid]\n"
                + "           ,[questionid]\n"
                + "           ,[answerid]\n"
                + "           ,[created_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (UserChoose model : list) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, model.getUserId());
                stm.setInt(2, model.getQuizzesId());
                stm.setInt(3, model.getLessonId());
                stm.setInt(4, model.getQuestionId());
                stm.setInt(5, model.getAnswerId());
                stm.setTimestamp(6, model.getCreated_at());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteByUserAndLessonAndQuizzes(int userId, int lessonId, int quizzesId) {
        try {
            String sql = "DELETE FROM [user_choose]\n"
                    + "      WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByUserAndLessonAndQuizzesAndQuestion(int userId, int lessonId, int quizzesId, int questionId) {
        try {
            String sql = "DELETE FROM [user_choose]\n"
                    + "      WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ? AND [questionid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            stm.setInt(4, questionId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserChooseDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(UserChoose model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
