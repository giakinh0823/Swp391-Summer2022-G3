/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.FileQuestion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class FileQuestionDBContext extends DBContext<FileQuestion> {

    public FileQuestion findByUUID(String uuid) {
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[questionid]\n"
                + "  FROM [file_question]"
                + " WHERE [file] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, uuid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileQuestion fileQuestion = new FileQuestion();
                fileQuestion.setId(rs.getInt("id"));
                fileQuestion.setFile(rs.getString("file"));
                fileQuestion.setQuestionId(rs.getInt("questionid"));
                return fileQuestion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<FileQuestion> findByQuestionid(int id) {
        ArrayList<FileQuestion> fileQuestions = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[questionid]\n"
                + "  FROM [file_question]"
                + " WHERE [questionid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileQuestion fileQuestion = new FileQuestion();
                fileQuestion.setId(rs.getInt("id"));
                fileQuestion.setFile(rs.getString("file"));
                fileQuestion.setQuestionId(rs.getInt("questionid"));
                fileQuestions.add(fileQuestion);
            }
            return fileQuestions;
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<FileQuestion> list() {
        ArrayList<FileQuestion> fileQuestions = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[questionid]\n"
                + "  FROM [file_question]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileQuestion fileQuestion = new FileQuestion();
                fileQuestion.setId(rs.getInt("id"));
                fileQuestion.setFile(rs.getString("file"));
                fileQuestion.setQuestionId(rs.getInt("questionid"));
                fileQuestions.add(fileQuestion);
            }
            return fileQuestions;
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FileQuestion get(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[questionid]\n"
                + "  FROM [file_question]"
                + " WHERE id = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileQuestion fileQuestion = new FileQuestion();
                fileQuestion.setId(rs.getInt("id"));
                fileQuestion.setFile(rs.getString("file"));
                fileQuestion.setQuestionId(rs.getInt("questionid"));
                return fileQuestion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FileQuestion insert(FileQuestion model) {
        String sql = "INSERT INTO [file_question]\n"
                + "           ([file]\n"
                + "           ,[questionid])\n"
                + "     VALUES(?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getFile());
            if (model.getQuestionId() <= 0) {
                stm.setNull(2, java.sql.Types.INTEGER);
            } else {
                stm.setInt(2, model.getQuestionId());
            }
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
                return model;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(FileQuestion model) {
        String sql = "UPDATE [file_question]\n"
                + "         SET [file] = ?\n"
                + "           ,[questionid] = ?\n"
                + "    WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getFile());
            if (model.getQuestionId() <= 0) {
                stm.setNull(2, java.sql.Types.INTEGER);
            } else {
                stm.setInt(2, model.getQuestionId());
            }
            stm.setInt(2, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteByImages(List<String> images) {
        try {
            String sql = "DELETE FROM [file_question]\n"
                    + "      WHERE [file_question].[file] = ?";
            connection.setAutoCommit(false);
            for (String image : images) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, image);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateManyFile(List<FileQuestion> listFiles) {
        String sql = "UPDATE [file_question]\n"
                + "       SET [file] = ?\n"
                + "           ,[questionid] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (FileQuestion model : listFiles) {
                stm = connection.prepareStatement(sql);
                stm.setString(1, model.getFile());
                if (model.getQuestionId() <= 0) {
                    stm.setNull(2, java.sql.Types.INTEGER);
                } else {
                    stm.setInt(2, model.getQuestionId());
                }
                stm.setInt(3, model.getId());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileQuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
