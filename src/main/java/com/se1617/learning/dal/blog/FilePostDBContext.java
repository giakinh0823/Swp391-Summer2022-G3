/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.blog;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.blog.FilePost;
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
public class FilePostDBContext extends DBContext<FilePost> {

    public FilePost findByUUID(String uuid) {
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[postid]\n"
                + "  FROM [file_post]"
                + " WHERE [file] = ? ";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, uuid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FilePost image = new FilePost();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setPostId(rs.getInt("postid"));
                return image;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<FilePost> findByPostid(int id) {
        ArrayList<FilePost> images = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[postid]\n"
                + "  FROM [file_post]"
                + " WHERE [postid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FilePost image = new FilePost();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setPostId(rs.getInt("postid"));
                images.add(image);
            }
            return images;
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<FilePost> list() {
        ArrayList<FilePost> images = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[postid]\n"
                + "  FROM [file_post]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FilePost image = new FilePost();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setPostId(rs.getInt("postid"));
                images.add(image);
            }
            return images;
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FilePost get(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[postid]\n"
                + "  FROM [file_post]"
                + " WHERE id = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FilePost image = new FilePost();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setPostId(rs.getInt("postid"));
                return image;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FilePost insert(FilePost model) {
        String sql = "INSERT INTO [file_post]\n"
                + "           ([file]\n"
                + "           ,[postid])\n"
                + "     VALUES(?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getFile());
            if (model.getPostId() <= 0) {
                stm.setNull(2, java.sql.Types.INTEGER);
            } else {
                stm.setInt(2, model.getPostId());
            }
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
                return model;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(FilePost model) {
        String sql = "UPDATE [file_post]\n"
                + "       SET [file] = ?\n"
                + "           ,[postid] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getFile());
            if (model.getPostId() <= 0) {
                stm.setNull(2, java.sql.Types.INTEGER);
            } else {
                stm.setInt(2, model.getPostId());
            }
            stm.setInt(3, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "DELETE FROM [file_post]\n"
                    + "      WHERE [file_post].[file] = ?";
            connection.setAutoCommit(false);
            for (String image : images) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, image);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateManyFile(List<FilePost> listFiles) {
        String sql = "UPDATE [file_post]\n"
                + "       SET [file] = ?\n"
                + "           ,[postid] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (FilePost model : listFiles) {
                stm = connection.prepareStatement(sql);
                stm.setString(1, model.getFile());
                if (model.getPostId() <= 0) {
                    stm.setNull(2, java.sql.Types.INTEGER);
                } else {
                    stm.setInt(2, model.getPostId());
                }
                stm.setInt(3, model.getId());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FilePostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
