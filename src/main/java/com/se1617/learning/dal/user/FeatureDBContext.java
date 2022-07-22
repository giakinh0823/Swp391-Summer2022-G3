/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.user;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.user.Feature;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class FeatureDBContext extends DBContext<Feature> {

    public ArrayList<Feature> getByGroupId(int groupId) {
        ArrayList<Feature> features = new ArrayList<>();
        String sql = "SELECT [feature].[id]\n"
                + "      ,[feature].[name]\n"
                + "      ,[feature].[feature]\n"
                + "  FROM [feature]\n"
                + "  INNER JOIN [feature_group] on [feature_group].[featureid] = [feature].[id]\n"
                + "  WHERE [feature_group].[groupid] =  ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("id"));
                feature.setName(rs.getString("name"));
                feature.setFeature(rs.getString("feature"));
                features.add(feature);
            }
            return features;
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Feature> findMany(String field, String search, Pageable pageable) {
        ResultPageable<Feature> resultPageable = new ResultPageable<>();
        ArrayList<Feature> features = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [feature].[id]\n"
                + "      ,[feature].[name]\n"
                + "      ,[feature].[feature]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [feature].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [feature]\n"
                + " WHERE [feature].[" + field + "] LIKE ?) [feature]\n"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_search_data = "SELECT COUNT([feature].[id]) as size FROM [feature]\n"
                + " WHERE [feature].[" + field + "] LIKE ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setInt(2, pageable.getPageIndex());
            stm.setInt(3, pageable.getPageSize());
            stm.setInt(4, pageable.getPageIndex());
            stm.setInt(5, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("id"));
                feature.setName(rs.getString("name"));
                feature.setFeature(rs.getString("feature"));
                features.add(feature);
            }
            resultPageable.setList(features);
            stm = connection.prepareCall(sql_count_search_data);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Feature> listPageable(Pageable pageable) {
        ResultPageable<Feature> resultPageable = new ResultPageable<>();
        ArrayList<Feature> features = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [feature].[id]\n"
                + "      ,[feature].[name]\n"
                + "      ,[feature].[feature]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [feature].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [feature]) [feature]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([feature].[id]) as size FROM [feature]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setInt(1, pageable.getPageIndex());
            stm.setInt(2, pageable.getPageSize());
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("id"));
                feature.setName(rs.getString("name"));
                feature.setFeature(rs.getString("feature"));
                features.add(feature);
            }
            resultPageable.setList(features);

            stm = connection.prepareCall(sql_count_all_data);
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Feature> list() {
        ArrayList<Feature> features = new ArrayList<>();
        String sql = "SELECT [feature].[id]\n"
                + "      ,[feature].[name]\n"
                + "      ,[feature].[feature]\n"
                + "  FROM [feature]\n";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("id"));
                feature.setName(rs.getString("name"));
                feature.setFeature(rs.getString("feature"));
                features.add(feature);
            }
            return features;
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Feature get(int id) {
        String sql = "SELECT [feature].[id]\n"
                + "      ,[feature].[name]\n"
                + "      ,[feature].[feature]\n"
                + "  FROM [feature]\n"
                + " WHERE [feature].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("id"));
                feature.setName(rs.getString("name"));
                feature.setFeature(rs.getString("feature"));
                return feature;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Feature insert(Feature feature) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO [feature]\n"
                    + "           ([name]\n"
                    + "           ,[feature])\n"
                    + "     VALUES(?, ?)";
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, feature.getName());
            stm.setString(2, feature.getFeature());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                feature.setId(id);
                return feature;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Feature feature) {
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE [feature]\n"
                    + "       SET [feature].[name] = ? \n"
                    + "          ,[feature].[feature] = ? \n"
                    + "  WHERE [feature].[id] = ? ";
            stm = connection.prepareStatement(sql);
            stm.setString(1, feature.getName());
            stm.setString(2, feature.getFeature());
            stm.setInt(3, feature.getId());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM [feature]\n"
                + " WHERE [feature].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteByIds(List<Integer> ids) {
        String sql = "DELETE FROM [feature]\n"
                + " WHERE [feature].[id] = ?";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (Integer id : ids) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, id);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultPageable<Feature> searchByNameOrFeatureFilterMultiField(String search, Pageable pageable) {
        ResultPageable<Feature> resultPageable = new ResultPageable<>();
        ArrayList<Feature> features = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [feature].[id]\n"
                + "      ,[feature].[name]\n"
                + "      ,[feature].[feature]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY ";
        if (pageable.getOrderings() != null && pageable.getOrderings().size() > 0) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                sql_query_data += " [feature].[" + key + "] " + val + ",";
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [feature].[id] DESC ";
        }
        sql_query_data += " ) as row_index\n";
        sql_query_data += "  FROM [feature]\n"
                + " WHERE [feature].[name] LIKE ? OR [feature].[feature] LIKE ? ) [feature]\n"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_search_data = "SELECT COUNT([feature].[id]) as size FROM [feature]\n"
                + " WHERE [feature].[name] LIKE ? OR [feature].[feature] LIKE ? ";
                
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            stm.setInt(5, pageable.getPageIndex());
            stm.setInt(6, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("id"));
                feature.setName(rs.getString("name"));
                feature.setFeature(rs.getString("feature"));
                features.add(feature);
            }
            resultPageable.setList(features);
            stm = connection.prepareCall(sql_count_search_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
