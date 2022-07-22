/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.Media;
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
public class MediaDBContext extends DBContext<Media> {

    public Media findByUUID(String uuid) {
        ArrayList<Media> medias = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[type]\n"
                + "      ,[url]\n"
                + "      ,[questionid]\n"
                + "  FROM [media]"
                + " WHERE [url] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, uuid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Media media = new Media();
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setUrl(rs.getString("url"));
                media.setQuestionId(rs.getInt("questionid"));
                return media;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Media> findByQuestionId(int id) {
        ArrayList<Media> medias = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[type]\n"
                + "      ,[url]\n"
                + "      ,[questionid]\n"
                + "  FROM [media]"
                + " WHERE questionid = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Media media = new Media();
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setUrl(rs.getString("url"));
                media.setQuestionId(rs.getInt("questionid"));
                medias.add(media);
            }
            return medias;
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Media getByQuestionId(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[type]\n"
                + "      ,[url]\n"
                + "      ,[questionid]\n"
                + "  FROM [media]"
                + " WHERE questionid = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Media media = new Media();
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setUrl(rs.getString("url"));
                media.setQuestionId(rs.getInt("questionid"));
                return media;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Media> list() {
        ArrayList<Media> medias = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[type]\n"
                + "      ,[url]\n"
                + "      ,[questionid]\n"
                + "  FROM [media]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Media media = new Media();
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setUrl(rs.getString("url"));
                media.setQuestionId(rs.getInt("questionid"));
                medias.add(media);
            }
            return medias;
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Media get(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[type]\n"
                + "      ,[url]\n"
                + "      ,[questionid]\n"
                + "  FROM [media]"
                + " WHERE id = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Media media = new Media();
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setUrl(rs.getString("url"));
                media.setQuestionId(rs.getInt("questionid"));
                return media;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Media insert(Media model) {
        String sql = "INSERT INTO [media]\n"
                + "           ([type]\n"
                + "           ,[url]\n"
                + "           ,[questionid])\n"
                + "     VALUES (?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getType());
            stm.setString(2, model.getUrl());
            if (model.getQuestionId() <= 0) {
                stm.setNull(3, java.sql.Types.INTEGER);
            } else {
                stm.setInt(3, model.getQuestionId());
            }
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
                return model;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Media model) {
        String sql = "UPDATE [media]\n"
                + "        SET [type] = ?\n"
                + "           ,[url] = ?\n"
                + "           ,[questionid] = ?\n"
                + " WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getType());
            stm.setString(2, model.getUrl());
            if (model.getQuestionId() <= 0) {
                stm.setNull(3, java.sql.Types.INTEGER);
            } else {
                stm.setInt(3, model.getQuestionId());
            }
            stm.setInt(4, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteByImages(List<String> images) {
        try {
            String sql = "DELETE FROM [media]\n"
                    + "      WHERE [media].[url] = ?";
            connection.setAutoCommit(false);
            for (String image : images) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, image);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [media]\n"
                    + "      WHERE [media].[id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MediaDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
