/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.quizzes;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.quizzes.QuizzesDimension;
import com.se1617.learning.model.subject.Dimension;
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
public class QuizzesDimensionDBContext extends DBContext<QuizzesDimension> {

    public ArrayList<QuizzesDimension> findByQuizzesId(int quizzesId) {
        ArrayList<QuizzesDimension> quizzesDimensions = new ArrayList<>();
        String sql = "SELECT [setting_dimension_quizzes].[settingid]\n"
                + "      ,[setting_dimension_quizzes].[dimensionid]\n"
                + "      ,[setting_dimension_quizzes].[number_question]\n"
                + "	 ,[dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[typeid]\n"
                + "      ,[dimension].[subjectid]\n"
                + "  FROM [setting_dimension_quizzes]\n"
                + "  INNER JOIN [setting_quizzes] ON  [setting_quizzes].[id] = [setting_dimension_quizzes].[settingid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [setting_dimension_quizzes].[dimensionid]"
                + " WHERE [setting_dimension_quizzes].[settingid] = ?";
        try {
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, quizzesId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuizzesDimension quizzesDimension = new QuizzesDimension();
                quizzesDimension.setDimensionId(rs.getInt("dimensionid"));
                quizzesDimension.setNumberQuestion(rs.getInt("number_question"));
                quizzesDimension.setSettingQuizzesId(rs.getInt("settingid"));
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("dimensionid"));
                dimension.setDescription(rs.getString("description"));
                dimension.setName(rs.getString("name"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));
                quizzesDimension.setDimension(dimension);
                quizzesDimensions.add(quizzesDimension);
            }
            return quizzesDimensions;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<QuizzesDimension> list() {
        ArrayList<QuizzesDimension> quizzesDimensions = new ArrayList<>();
        String sql = "SELECT [setting_dimension_quizzes].[settingid]\n"
                + "      ,[setting_dimension_quizzes].[dimensionid]\n"
                + "      ,[setting_dimension_quizzes].[number_question]\n"
                + "	  ,[dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[typeid]\n"
                + "      ,[dimension].[subjectid]\n"
                + "  FROM [setting_dimension_quizzes]\n"
                + "  INNER JOIN [setting_quizzes] ON  [setting_quizzes].[id] = [setting_dimension_quizzes].[settingid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [setting_dimension_quizzes].[dimensionid]";
        try {
            PreparedStatement stm = connection.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuizzesDimension quizzesDimension = new QuizzesDimension();
                quizzesDimension.setDimensionId(rs.getInt("dimensionid"));
                quizzesDimension.setNumberQuestion(rs.getInt("number_question"));
                quizzesDimension.setSettingQuizzesId(rs.getInt("settingid"));
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("dimensionid"));
                dimension.setDescription("description");
                dimension.setName("name");
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));
                quizzesDimension.setDimension(dimension);
                quizzesDimensions.add(quizzesDimension);
            }
            return quizzesDimensions;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public QuizzesDimension get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void insertMany(ArrayList<QuizzesDimension> listModels) {
        if (listModels != null && !listModels.isEmpty()) {
            deleteBySettingId(listModels.get(0).getSettingQuizzesId());
        }
        String sql = "INSERT INTO [dbo].[setting_dimension_quizzes]\n"
                + "           ([settingid]\n"
                + "           ,[dimensionid]\n"
                + "           ,[number_question])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql);
            for (QuizzesDimension model : listModels) {
                stm.setInt(1, model.getSettingQuizzesId());
                stm.setInt(2, model.getDimensionId());
                stm.setInt(3, model.getNumberQuestion());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public QuizzesDimension insert(QuizzesDimension model) {
        String sql = "INSERT INTO [dbo].[setting_dimension_quizzes]\n"
                + "           ([settingid]\n"
                + "           ,[dimensionid]\n"
                + "           ,[number_question])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getSettingQuizzesId());
            stm.setInt(2, model.getDimensionId());
            stm.setInt(3, model.getNumberQuestion());
            stm.executeUpdate();
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(QuizzesDimension model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteBySettingId(int id) {
        String sql = "DELETE FROM [setting_dimension_quizzes]\n"
                + " WHERE [setting_dimension_quizzes].[settingid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
