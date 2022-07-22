/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.quizzes;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.dal.question.AnswerUserDBContext;
import com.se1617.learning.dal.question.QuestionDBContext;
import com.se1617.learning.dal.question.QuestionUserDBContext;
import com.se1617.learning.dal.question.UserChooseDBContext;
import com.se1617.learning.model.quizzes.QuizzesUser;
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
public class QuizzesUserDBContext extends DBContext<QuizzesUser> {

    @Override
    public ArrayList<QuizzesUser> list() {
        ArrayList<QuizzesUser> quizzes = new ArrayList<>();
        String sql = "SELECT [quizzesid]\n"
                + "      ,[userid]\n"
                + "      ,[lessonid]\n"
                + "      ,[levelid]\n"
                + "      ,[typeid]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[pass_rate]\n"
                + "      ,[description]\n"
                + "      ,[start_time]\n"
                + "      ,[end_time]\n"
                + "  FROM [quizzes_user]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuizzesUser quizzesUser = new QuizzesUser();
                quizzesUser.setUserId(rs.getInt("userid"));
                quizzesUser.setQuizzesId(rs.getInt("quizzesid"));
                quizzesUser.setLessonId(rs.getInt("lessonid"));
                quizzesUser.setLessonId(rs.getInt("levelid"));
                quizzesUser.setTypeId(rs.getInt("typeid"));
                quizzesUser.setName(rs.getString("name"));
                quizzesUser.setDuration(rs.getInt("duration"));
                quizzesUser.setPass_rate(rs.getInt("pass_rate"));
                quizzesUser.setDescription(rs.getString("pass_rate"));
                quizzesUser.setDescription(rs.getString("description"));
                quizzesUser.setStart_time(rs.getTimestamp("start_time"));
                quizzesUser.setEnd_time(rs.getTimestamp("end_time"));
                quizzes.add(quizzesUser);
            }
            return quizzes;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public QuizzesUser findByUserAndLessonAndQuiz(int userId, int lessonId, int quizzesId) {
        String sql = "SELECT [quizzesid]\n"
                + "      ,[userid]\n"
                + "      ,[lessonid]\n"
                + "      ,[levelid]\n"
                + "      ,[typeid]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[pass_rate]\n"
                + "      ,[description]\n"
                + "      ,[start_time]\n"
                + "      ,[end_time]\n"
                + "  FROM [quizzes_user]"
                + " WHERE userid=? AND lessonid=? AND quizzesid=?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuizzesUser quizzesUser = new QuizzesUser();
                quizzesUser.setUserId(rs.getInt("userid"));
                quizzesUser.setQuizzesId(rs.getInt("quizzesid"));
                quizzesUser.setLessonId(rs.getInt("lessonid"));
                quizzesUser.setLevelId(rs.getInt("levelid"));
                quizzesUser.setTypeId(rs.getInt("typeid"));
                quizzesUser.setName(rs.getString("name"));
                quizzesUser.setDuration(rs.getInt("duration"));
                quizzesUser.setPass_rate(rs.getInt("pass_rate"));
                quizzesUser.setDescription(rs.getString("pass_rate"));
                quizzesUser.setDescription(rs.getString("description"));
                quizzesUser.setStart_time(rs.getTimestamp("start_time"));
                quizzesUser.setEnd_time(rs.getTimestamp("end_time"));
                return quizzesUser;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public QuizzesUser get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public QuizzesUser insert(QuizzesUser model) {
        UserChooseDBContext userChooseDB = new UserChooseDBContext();
        userChooseDB.deleteByUserAndLessonAndQuizzes(model.getUserId(), model.getLessonId(), model.getQuizzesId());
        AnswerUserDBContext answerUserDBContext = new AnswerUserDBContext();
        answerUserDBContext.deleteByUserAndLessonAndQuizzes(model.getUserId(), model.getLessonId(), model.getQuizzesId());
        QuestionUserDBContext questionUserDBContext = new QuestionUserDBContext();
        questionUserDBContext.deleteByUserAndLessonAndQuizzes(model.getUserId(), model.getLessonId(), model.getQuizzesId());
        deleteByUserAndLessonAndQuizzes(model.getUserId(), model.getLessonId(), model.getQuizzesId());
        String sql = "INSERT INTO [quizzes_user]\n"
                + "           ([quizzesid]\n"
                + "           ,[userid]\n"
                + "           ,[lessonid]\n"
                + "           ,[levelid]\n"
                + "           ,[typeid]\n"
                + "           ,[name]\n"
                + "           ,[duration]\n"
                + "           ,[pass_rate]\n"
                + "           ,[description]\n"
                + "           ,[start_time]\n"
                + "           ,[end_time])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getQuizzesId());
            stm.setInt(2, model.getUserId());
            stm.setInt(3, model.getLessonId());
            stm.setInt(4, model.getLevelId());
            stm.setInt(5, model.getTypeId());
            stm.setString(6, model.getName());
            stm.setInt(7, model.getDuration());
            stm.setInt(8, model.getPass_rate());
            stm.setString(9, model.getDescription());
            stm.setTimestamp(10, model.getStart_time());
            stm.setTimestamp(11, model.getEnd_time());
            stm.executeUpdate();
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void deleteByUserAndLessonAndQuizzes(int userId, int lessonId, int quizzesId) {
        try {
            String sql = "DELETE FROM [quizzes_user]\n"
                    + "      WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(QuizzesUser model) {
        String sql = "UPDATE [quizzes_user]\n"
                + "         SET [levelid] = ?\n"
                + "           ,[typeid] = ?\n"
                + "           ,[name] = ?\n"
                + "           ,[duration] = ?\n"
                + "           ,[pass_rate] = ?\n"
                + "           ,[description]  = ?\n"
                + "           ,[start_time] = ?\n"
                + "           ,[end_time] = ?\n"
                + " WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getLevelId());
            stm.setInt(2, model.getTypeId());
            stm.setString(3, model.getName());
            stm.setInt(4, model.getDuration());
            stm.setInt(5, model.getPass_rate());
            stm.setString(6, model.getDescription());
            stm.setTimestamp(7, model.getStart_time());
            stm.setTimestamp(8, model.getEnd_time());
            stm.setInt(9, model.getUserId());
            stm.setInt(10, model.getLessonId());
            stm.setInt(11, model.getQuizzesId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
