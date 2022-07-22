/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.subject;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.subject.FileLesson;
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
public class FileLessonDBContext extends DBContext<FileLesson> {

    public FileLesson findByUUID(String uuid) {
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[lessonid]\n"
                + "  FROM [file_lesson]"
                + " WHERE [file] = ? ";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, uuid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileLesson image = new FileLesson();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setLessonId(rs.getInt("lessonid"));
                return image;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<FileLesson> findByLessonid(int id) {
        ArrayList<FileLesson> images = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[lessonid]\n"
                + "  FROM [file_lesson]"
                + " WHERE [lessonid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileLesson image = new FileLesson();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setLessonId(rs.getInt("lessonid"));
                images.add(image);
            }
            return images;
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<FileLesson> list() {
        ArrayList<FileLesson> images = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[lessonid]\n"
                + "  FROM [file_lesson]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileLesson image = new FileLesson();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setLessonId(rs.getInt("lessonid"));
                images.add(image);
            }
            return images;
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FileLesson get(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[file]\n"
                + "      ,[lessonid]\n"
                + "  FROM [file_lesson]"
                + " WHERE id = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                FileLesson image = new FileLesson();
                image.setId(rs.getInt("id"));
                image.setFile(rs.getString("file"));
                image.setLessonId(rs.getInt("lessonid"));
                return image;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FileLesson insert(FileLesson model) {
        String sql = "INSERT INTO [file_lesson]\n"
                + "           ([file]\n"
                + "           ,[lessonid])\n"
                + "     VALUES(?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getFile());
            if (model.getLesson()==null || model.getLessonId() <= 0) {
                stm.setNull(2, java.sql.Types.INTEGER);
            } else {
                stm.setInt(2, model.getLessonId());
            }
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
                return model;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(FileLesson model) {
        String sql = "UPDATE [file_lesson]\n"
                + "       SET [file] = ?\n"
                + "           ,[lessonid] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getFile());
            if (model.getLessonId() <= 0) {
                stm.setNull(2, java.sql.Types.INTEGER);
            } else {
                stm.setInt(2, model.getLessonId());
            }
            stm.setInt(3, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "DELETE FROM [file_lesson]\n"
                    + "      WHERE [file_lesson].[file] = ?";
            connection.setAutoCommit(false);
            for (String image : images) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setString(1, image);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateManyFile(List<FileLesson> listFiles) {
        String sql = "UPDATE [file_lesson]\n"
                + "       SET [file] = ?\n"
                + "           ,[lessonid] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (FileLesson model : listFiles) {
                stm = connection.prepareStatement(sql);
                stm.setString(1, model.getFile());
                if (model.getLessonId() <= 0) {
                    stm.setNull(2, java.sql.Types.INTEGER);
                } else {
                    stm.setInt(2, model.getLessonId());
                }
                stm.setInt(3, model.getId());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FileLessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
