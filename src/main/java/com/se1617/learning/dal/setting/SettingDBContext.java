/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.setting;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.dal.blog.PostDBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class SettingDBContext extends DBContext<Setting> {

    public ArrayList<Setting> listSettingNotPrarent(String type) {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "      ,[setting].[description]\n"
                + "      ,[setting].[parent]\n"
                + "  FROM [setting]\n"
                + "  WHERE UPPER([setting].[type]) = UPPER(?) AND [setting].[parent] IS NULL"
                + "  ORDER BY [setting].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("status"));
                setting.setDescription(rs.getString("description"));
                setting.setParent(rs.getInt("parent"));
                setting.setChilds(getChilds(type, setting.getId()));
                settings.add(setting);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> listSettingNotPrarent() {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "      ,[setting].[description]\n"
                + "      ,[setting].[parent]\n"
                + "  FROM [setting]\n"
                + "  WHERE [setting].[parent] IS NULL"
                + "  ORDER BY [setting].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("status"));
                setting.setDescription(rs.getString("description"));
                setting.setParent(rs.getInt("parent"));
                setting.setChilds(getChilds(setting.getId()));
                settings.add(setting);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> listSettingHavePrarent(String type) {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n"
                + "      ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "    LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + "  WHERE UPPER([st1].[type]) = UPPER(?) AND NOT [st1].[parent] IS NULL"
                + "  ORDER BY [st1].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));

                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }
                settings.add(st1);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> listSettingHavePrarent() {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n"
                + "      ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "    LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + "  WHERE NOT [st1].[parent] IS NULL"
                + "  ORDER BY [st1].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));

                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }
                settings.add(st1);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> getChilds(String type, int id) {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n"
                + "      ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "    LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + "  WHERE UPPER([st1].[type]) = UPPER(?) AND NOT [st1].[parent] IS NULL AND [st2].[id] = ?"
                + "  ORDER BY [st1].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            stm.setInt(2, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));

                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }
                settings.add(st1);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> getChilds(int id) {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n"
                + "      ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "    LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + "  WHERE NOT [st1].[parent] IS NULL AND [st2].[id] = ?"
                + "  ORDER BY [st1].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));

                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }
                settings.add(st1);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Setting> getByType(String type) {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "      ,[setting].[description]\n"
                + "      ,[setting].[parent]\n"
                + "  FROM [setting]\n"
                + "  WHERE UPPER([setting].[type]) = UPPER(?)"
                + "  ORDER BY [setting].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("status"));
                setting.setDescription(rs.getString("description"));
                setting.setParent(rs.getInt("parent"));
                settings.add(setting);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Setting> getByTypeActive(String type) {
        ArrayList<Setting> settings = new ArrayList<>();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "      ,[setting].[description]\n"
                + "      ,[setting].[parent]\n"
                + "  FROM [setting]\n"
                + "  WHERE [setting].[status] = 1 AND UPPER([setting].[type]) = UPPER(?)"
                + "  ORDER BY [setting].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("status"));
                setting.setDescription(rs.getString("description"));
                setting.setParent(rs.getInt("parent"));
                settings.add(setting);
            }
            return settings;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Setting findOne(String type, String value) {
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "      ,[setting].[description]\n"
                + "      ,[setting].[parent]\n"
                + "  FROM [setting]\n"
                + "  WHERE UPPER([setting].[type]) = UPPER(?) AND UPPER([setting].[value]) = UPPER(?)";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, type);
            stm.setString(2, value);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting setting = new Setting();
                setting.setId(rs.getInt("id"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("status"));
                setting.setDescription(rs.getString("description"));
                setting.setParent(rs.getInt("parent"));
                return setting;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Setting> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Setting get(int id) {
        String sql = "SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n"
                + "      ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "     LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + "  WHERE [st1].[id] = ? ";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));

                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }
                return st1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void update(Setting setting) {
        String sql = "UPDATE [setting]\n"
                + "   SET [type] = ?\n"
                + "      ,[value] = ?\n"
                + "      ,[order] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[parent] = ?\n"
                + " WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, setting.getType());
            stm.setString(2, setting.getValue());
            stm.setInt(3, setting.getOrder());
            stm.setBoolean(4, setting.isStatus());
            stm.setString(5, setting.getDescription());
            if (setting.getSetting() != null) {
                stm.setInt(6, setting.getSetting().getId());
            } else {
                stm.setNull(6, java.sql.Types.INTEGER);
            }
            stm.setInt(7, setting.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            String sql = "DELETE FROM [post]\n"
                    + "      WHERE [post].[categoryId] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [quizzes]\n"
                    + "      WHERE [quizzes].[levelid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [quizzes]\n"
                    + "      WHERE [quizzes].[typeid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [lesson]\n"
                    + "      WHERE [lesson].[typeid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [dimension]\n"
                    + "      WHERE [dimension].[typeid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [group_user]\n"
                    + "      WHERE [group_user].[groupid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [feature_group]\n"
                    + "      WHERE [feature_group].[groupid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [subject]\n"
                    + "      WHERE [subject].[categoryid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            sql = "DELETE FROM [setting]\n"
                    + "      WHERE [setting].[parent] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();

            sql = "DELETE FROM [setting]\n"
                    + "      WHERE [setting].[id] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Setting insert(Setting setting) {
        String sql = "INSERT INTO [setting]\n"
                + "           ([type]\n"
                + "           ,[value]\n"
                + "           ,[order]\n"
                + "           ,[status]\n";
        sql += "           ,[description]\n";
        if (setting.getSetting() != null) {
            sql += "           ,[parent]";
        }
        sql += ")\n";
        sql += "     VALUES(?,?,?,?,?";
        if (setting.getSetting() != null) {
            sql += ",?";
        }
        sql += ")";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, setting.getType());
            stm.setString(2, setting.getValue());
            stm.setInt(3, setting.getOrder());
            stm.setBoolean(4, setting.isStatus());
            stm.setString(5, setting.getDescription());
            if (setting.getSetting() != null) {
                stm.setInt(6, setting.getSetting().getId());
            }
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                setting.setId(id);
            }
            return setting;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public ResultPageable<Setting> listPageable(String search, String type, String status, Pageable pageable) {
        ArrayList<Setting> settings = new ArrayList<>();
        ResultPageable<Setting> resultPageable = new ResultPageable<>();
        boolean is_status = status != null && !status.trim().isEmpty() && status.equalsIgnoreCase("active");
        String sql_query_data = "SELECT * FROM (SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n";
        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";
        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("parent")) {
                    sql_query_data += " [st2].[value] " + val + ",";
                } else {
                    sql_query_data += " [st1].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [st1].[order] ASC";
        }
        sql_query_data += " ) as row_index\n";
        sql_query_data += " ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "     LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + " WHERE ([st1].[value] LIKE ? OR [st1].[type] LIKE ?) ";
        if (status != null) {
            sql_query_data += " AND [st1].[status] = ? ";
        }
        if (type != null) {
            sql_query_data += " AND [st1].[type] = ? ";
        }
        sql_query_data += " ) [setting]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([setting].[id]) as size FROM [setting]\n"
                + " WHERE ([setting].[value] LIKE ? OR [setting].[type] LIKE ?) ";

        if (status != null) {
            sql_count_all_data += " AND [setting].[status] = ? ";
        }
        if (type != null) {
            sql_count_all_data += " AND [setting].[type] = ? ";
        }

        System.out.println(sql_query_data);

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            if (status != null && type != null) {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setBoolean(3, is_status);
                stm.setString(4, type);
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
                stm.setInt(7, pageable.getPageIndex());
                stm.setInt(8, pageable.getPageSize());
            } else if (status != null || type != null) {

                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                if (status != null) {
                    stm.setBoolean(3, is_status);
                }
                if (type != null) {
                    stm.setString(3, type);
                }
                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
                stm.setInt(6, pageable.getPageIndex());
                stm.setInt(7, pageable.getPageSize());
            } else {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));
                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }

                settings.add(st1);
            }
            resultPageable.setList(settings);

            stm = connection.prepareCall(sql_count_all_data);
            if (status != null && type != null) {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setBoolean(3, is_status);
                stm.setString(4, type);

            } else if (status != null || type != null) {

                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                if (status != null) {
                    stm.setBoolean(3, is_status);
                }
                if (type != null) {
                    stm.setString(3, type);
                }

            } else {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");

            }
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }

            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Setting> listPageable(String search, Pageable pageable) {
        ArrayList<Setting> settings = new ArrayList<>();
        ResultPageable<Setting> resultPageable = new ResultPageable<>();
        String sql_query_data = "SELECT * FROM (SELECT [st1].[id]\n"
                + "      ,[st1].[type]\n"
                + "      ,[st1].[value]\n"
                + "      ,[st1].[order]\n"
                + "      ,[st1].[status]\n"
                + "      ,[st1].[description]\n"
                + "      ,[st1].[parent]\n";
        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";
        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("parent")) {
                    sql_query_data += " [st2].[value] " + val + ",";
                } else {
                    sql_query_data += " [st1].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [st1].[order] ASC";
        }
        sql_query_data += " ) as row_index\n";
        sql_query_data += " ,[st2].[id] as 'st2_id'\n"
                + "      ,[st2].[type] as 'st2_type'\n"
                + "      ,[st2].[value] as 'st2_value'\n"
                + "      ,[st2].[order] as 'st2_order'\n"
                + "      ,[st2].[status] as 'st2_status'\n"
                + "      ,[st2].[description] as 'st2_description'\n"
                + "      ,[st2].[parent] as 'st2_parent'\n"
                + "     FROM [setting] st1"
                + "     LEFT JOIN [setting] st2 on st2.id = st1.parent\n"
                + " WHERE ([st1].[value] LIKE ? OR [st1].[type] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            key = " [st1].[" + key + "] ";
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }
        sql_query_data += " ) [setting]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([setting].[id]) as size FROM [setting]\n"
                + " WHERE ([setting].[value] LIKE ? OR [setting].[type] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_all_data += " AND ( ";
            key = " [setting].[" + key + "] ";
            sql_count_all_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_count_all_data += ",?";
            }
            sql_count_all_data += ") ) ";
        }

        System.out.println(sql_query_data);

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("active");
                            stm.setBoolean(count + 3, isStatus);
                        } else {
                            stm.setString(count + 3, val.get(i));
                        }
                        count++;
                    }
                }
                stm.setInt(count - 1 + 4, pageable.getPageIndex());
                stm.setInt(count - 1 + 5, pageable.getPageSize());
                stm.setInt(count - 1 + 6, pageable.getPageIndex());
                stm.setInt(count - 1 + 7, pageable.getPageSize());
            } else {
                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Setting st1 = new Setting();
                st1.setId(rs.getInt("id"));
                st1.setType(rs.getString("type"));
                st1.setValue(rs.getString("value"));
                st1.setOrder(rs.getInt("order"));
                st1.setStatus(rs.getBoolean("status"));
                st1.setDescription(rs.getString("description"));
                st1.setParent(rs.getInt("parent"));
                if (rs.getString("st2_value") != null) {
                    Setting st2 = new Setting();
                    st2.setId(rs.getInt("st2_id"));
                    st2.setType(rs.getString("st2_type"));
                    st2.setValue(rs.getString("st2_value"));
                    st2.setOrder(rs.getInt("st2_order"));
                    st2.setStatus(rs.getBoolean("st2_status"));
                    st2.setDescription(rs.getString("st2_description"));
                    st2.setParent(rs.getInt("st2_parent"));
                    st1.setSetting(st2);
                }

                settings.add(st1);
            }
            resultPageable.setList(settings);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("active");
                            stm.setBoolean(count + 3, isStatus);
                        } else {
                            stm.setString(count + 3, val.get(i));
                        }
                        count++;
                    }
                }
            }
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }

            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
