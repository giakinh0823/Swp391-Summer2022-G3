/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.user;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.user.Feature;
import com.se1617.learning.model.user.Group;
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
public class GroupDBContext extends DBContext<Group> {

    public ArrayList<Group> getByUserId(int userId) {
        FeatureDBContext featureDB = new FeatureDBContext();
        ArrayList<Group> groups = new ArrayList<>();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "  FROM [setting]\n"
                + "  INNER JOIN [group_user] ON [group_user].[groupid] = [setting].[id]\n"
                + "  WHERE [setting].[type] = 'USER_ROLE' AND [group_user].[userid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setType(rs.getString("type"));
                group.setValue(rs.getString("value"));
                group.setOrder(rs.getInt("order"));
                group.setStatus(rs.getBoolean("status"));
                ArrayList<Feature> features = featureDB.getByGroupId(group.getId());
                group.setFeatures(features);
                groups.add(group);
            }
            return groups;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Group> list() {
        FeatureDBContext featureDB = new FeatureDBContext();
        ArrayList<Group> groups = new ArrayList<>();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "  FROM [setting]\n"
                + "  WHERE [setting].[type] = 'USER_ROLE'"
                + " ORDER BY [setting].[order] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setType(rs.getString("type"));
                group.setValue(rs.getString("value"));
                group.setOrder(rs.getInt("order"));
                group.setStatus(rs.getBoolean("status"));
                ArrayList<Feature> features = featureDB.getByGroupId(group.getId());
                group.setFeatures(features);
                groups.add(group);
            }
            return groups;
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Group findOne(String value) {
        FeatureDBContext featureDB = new FeatureDBContext();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "  FROM [setting]\n"
                + "  WHERE [setting].[type] = 'USER_ROLE' AND UPPER([setting].[value]) = UPPER(?)";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, value);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setType(rs.getString("type"));
                group.setValue(rs.getString("value"));
                group.setOrder(rs.getInt("order"));
                group.setStatus(rs.getBoolean("status"));
                ArrayList<Feature> features = featureDB.getByGroupId(group.getId());
                group.setFeatures(features);
                return group;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Group get(int id) {
        FeatureDBContext featureDB = new FeatureDBContext();
        String sql = "SELECT [setting].[id]\n"
                + "      ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status]\n"
                + "  FROM [setting]\n"
                + "  WHERE [setting].[type] = 'USER_ROLE' AND [setting].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setType(rs.getString("type"));
                group.setValue(rs.getString("value"));
                group.setOrder(rs.getInt("order"));
                group.setStatus(rs.getBoolean("status"));
                ArrayList<Feature> features = featureDB.getByGroupId(group.getId());
                group.setFeatures(features);
                return group;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateFeatureGroup(int idGroup, List<Integer> features) {
        delete(idGroup);
        String sql = "INSERT INTO [feature_group]\n"
                + "           ([featureid]\n"
                + "           ,[groupid])\n"
                + "     VALUES(?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (Integer feature : features) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, feature);
                stm.setInt(2, idGroup);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateUserGroup(int idUser, List<Group> groups) {
        deleteGroupUser(idUser);
        String sql = "INSERT INTO [group_user]\n"
                + "           ([userid]\n"
                + "           ,[groupid])\n"
                + "     VALUES(?,?)";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (Group item : groups) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, idUser);
                stm.setInt(2, item.getId());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Group insert(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM [feature_group]\n"
                + " WHERE [feature_group].[groupid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteGroupUser(int id) {
        String sql = "DELETE FROM [group_user]\n"
                + " WHERE [group_user].[userid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
