/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.AnswerUser;
import com.se1617.learning.model.question.Question;
import com.se1617.learning.model.question.QuestionUser;
import com.se1617.learning.model.question.UserChoose;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.Subject;
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
public class QuestionUserDBContext extends DBContext<QuestionUser> {

    @Override
    public ArrayList<QuestionUser> list() {
        ArrayList<QuestionUser> questions = new ArrayList<>();
        UserChooseDBContext userChooseDB = new UserChooseDBContext();
        AnswerUserDBContext answerUserDB = new AnswerUserDBContext();
        String sql = "SELECT [userid]\n"
                + "      ,[quizzesid]\n"
                + "      ,[lessonid]\n"
                + "      ,[questionid]\n"
                + "      ,[content]\n"
                + "      ,[content_html]\n"
                + "      ,[explain]\n"
                + "      ,[media]\n"
                + "      ,[is_multi]\n"
                + "      ,[dimensionid]\n"
                + "      ,[levelid]\n"
                + "  FROM [question_user]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuestionUser question = new QuestionUser();
                question.setQuestionId(rs.getInt("questionid"));
                question.setContent(rs.getString("content"));
                question.set_multi(rs.getBoolean("is_multi"));
                question.setDimensionId(rs.getInt("dimensionid"));
                question.setUserId(rs.getInt("userid"));
                question.setLevelId(rs.getInt("levelid"));
                question.setLessonId(rs.getInt("lessonid"));
                question.setContentHtml(rs.getString("content_html"));
                question.setExplain(rs.getString("explain"));
                question.setQuizzesId(rs.getInt("quizzesid"));
                question.setMedia(rs.getString("media"));
                ArrayList<AnswerUser> answerUsers = answerUserDB
                        .findByUserAndLessonAndQuizzesAndQuestion(question.getUserId(),
                                question.getLessonId(), question.getQuizzesId(), question.getQuestionId());
                ArrayList<UserChoose> userChooses = userChooseDB.findByUserAndLessonAndQuizAndQuestion(question.getUserId(),
                        question.getLessonId(), question.getQuizzesId(), question.getQuestionId());
                question.setAnswers(answerUsers);
                question.setUserChooses(userChooses);
                questions.add(question);
            }
            return questions;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<QuestionUser> findByUserAndLessonAndQuizz(int userId, int lessonId, int quizzid) {
        ArrayList<QuestionUser> questions = new ArrayList<>();
        UserChooseDBContext userChooseDB = new UserChooseDBContext();
        AnswerUserDBContext answerUserDB = new AnswerUserDBContext();
        String sql = "SELECT [userid]\n"
                + "      ,[quizzesid]\n"
                + "      ,[lessonid]\n"
                + "      ,[questionid]\n"
                + "      ,[content]\n"
                + "      ,[content_html]\n"
                + "      ,[explain]\n"
                + "      ,[media]\n"
                + "      ,[is_multi]\n"
                + "      ,[dimensionid]\n"
                + "      ,[levelid]\n"
                + "  FROM [question_user]"
                + " WHERE [userid] = ? AND [lessonid]=? AND [quizzesid]=? ";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuestionUser question = new QuestionUser();
                question.setQuestionId(rs.getInt("questionid"));
                question.setContent(rs.getString("content"));
                question.set_multi(rs.getBoolean("is_multi"));
                question.setDimensionId(rs.getInt("dimensionid"));
                question.setUserId(rs.getInt("userid"));
                question.setLevelId(rs.getInt("levelid"));
                question.setLessonId(rs.getInt("lessonid"));
                question.setContentHtml(rs.getString("content_html"));
                question.setExplain(rs.getString("explain"));
                question.setQuizzesId(rs.getInt("quizzesid"));
                question.setMedia(rs.getString("media"));
                ArrayList<AnswerUser> answerUsers = answerUserDB
                        .findByUserAndLessonAndQuizzesAndQuestion(question.getUserId(),
                                question.getLessonId(), question.getQuizzesId(), question.getQuestionId());
                ArrayList<UserChoose> userChooses = userChooseDB.findByUserAndLessonAndQuizAndQuestion(question.getUserId(),
                        question.getLessonId(), question.getQuizzesId(), question.getQuestionId());
                question.setAnswers(answerUsers);
                question.setUserChooses(userChooses);
                questions.add(question);
            }
            return questions;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public QuestionUser get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public QuestionUser insert(QuestionUser model) {
        String sql = "INSERT INTO [question_user]\n"
                + "           ([userid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[lessonid]\n"
                + "           ,[questionid]\n"
                + "           ,[content]\n"
                + "           ,[content_html]\n"
                + "           ,[explain]\n"
                + "           ,[is_multi]\n"
                + "           ,[dimensionid]\n"
                + "           ,[levelid]"
                + "           ,[media])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getUserId());
            stm.setInt(2, model.getQuizzesId());
            stm.setInt(3, model.getLessonId());
            stm.setInt(4, model.getQuestionId());

            stm.setString(5, model.getContent());
            stm.setString(6, model.getContentHtml());
            stm.setString(7, model.getExplain());
            stm.setBoolean(8, model.is_multi());
            stm.setInt(9, model.getDimensionId());
            stm.setInt(10, model.getLevelId());
            stm.setString(11, model.getMedia());
            stm.executeUpdate();
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public ArrayList<QuestionUser> insertMany(ArrayList<QuestionUser> list) {
        AnswerUserDBContext answerDBContext = new AnswerUserDBContext();
        if (list != null && !list.isEmpty()) {
            answerDBContext.deleteByUserAndLessonAndQuizzes(list.get(0).getUserId(), list.get(0).getLessonId(), list.get(0).getQuizzesId());
            deleteByUserAndLessonAndQuizzes(list.get(0).getUserId(), list.get(0).getLessonId(), list.get(0).getQuizzesId());
        }
        String sql = "INSERT INTO [question_user]\n"
                + "           ([userid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[lessonid]\n"
                + "           ,[questionid]\n"
                + "           ,[content]\n"
                + "           ,[content_html]\n"
                + "           ,[explain]\n"
                + "           ,[is_multi]\n"
                + "           ,[dimensionid]\n"
                + "           ,[levelid]"
                + "           ,[media])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (QuestionUser model : list) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, model.getUserId());
                stm.setInt(2, model.getQuizzesId());
                stm.setInt(3, model.getLessonId());
                stm.setInt(4, model.getQuestionId());

                stm.setString(5, model.getContent());
                stm.setString(6, model.getContentHtml());
                stm.setString(7, model.getExplain());
                stm.setBoolean(8, model.is_multi());
                stm.setInt(9, model.getDimensionId());
                stm.setInt(10, model.getLevelId());
                stm.setString(11, model.getMedia());
                stm.executeUpdate();
            }
            connection.commit();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void deleteByUserAndLessonAndQuizzes(int userId, int lessonId, int quizzesId) {
        try {
            String sql = "DELETE FROM [question_user]\n"
                    + "      WHERE [userid] = ? AND [lessonid] = ? AND [quizzesid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, lessonId);
            stm.setInt(3, quizzesId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionUserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(QuestionUser model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
