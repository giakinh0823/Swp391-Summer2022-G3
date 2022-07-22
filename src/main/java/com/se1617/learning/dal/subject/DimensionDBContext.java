/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.subject;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
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
public class DimensionDBContext extends DBContext<Dimension> {

    public ArrayList<Dimension> findBySubjectId(int id) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        String sql = "SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]"
                + "  WHERE [dimension].[subjectid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                dimensions.add(dimension);
            }
            return dimensions;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<Dimension> findByTypeId(int id) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        String sql = "SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]"
                + "  WHERE [dimension].[typeid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                dimensions.add(dimension);
            }
            return dimensions;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Dimension> findBySubjectIdPageable(int id, Pageable pageable) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        ResultPageable<Dimension> resultPageable = new ResultPageable<>();
        String sql = "SELECT * FROM(SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [dimension].[id] DESC) as row_index"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]"
                + "  WHERE [dimension].[subjectid] = ? ) [dimensions]"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_data = "SELECT COUNT([dimension].[id]) as size FROM [dimension]\n"
                + " WHERE [dimension].[subjectid] = ? ";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, pageable.getPageIndex());
            stm.setInt(3, pageable.getPageSize());
            stm.setInt(4, pageable.getPageIndex());
            stm.setInt(5, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                dimensions.add(dimension);
            }
            resultPageable.setList(dimensions);
            stm = connection.prepareCall(sql_count_data);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Dimension> list() {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        String sql = "SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                dimensions.add(dimension);
            }
            return dimensions;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Dimension get(int id) {
        String sql = "SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]\n"
                + " WHERE [dimension].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                return dimension;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Dimension insert(Dimension model) {
        String sql = "INSERT INTO [dimension]\n"
                + "           ([name]\n"
                + "           ,[description]\n"
                + "           ,[subjectid]\n"
                + "           ,[typeid])\n"
                + "     VALUES(?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getName());
            stm.setString(2, model.getDescription());
            stm.setInt(3, model.getSubjectId());
            stm.setInt(4, model.getTypeId());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void update(Dimension model) {
        String sql = "UPDATE [dimension]\n"
                + "       SET  [name] = ?\n"
                + "           ,[description] = ?\n"
                + "           ,[subjectid] = ?\n"
                + "           ,[typeid] = ?\n"
                + "      WHERE [dimension].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setString(2, model.getDescription());
            stm.setInt(3, model.getSubjectId());
            stm.setInt(4, model.getTypeId());
            stm.setInt(5, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM [dimension]\n"
                + " WHERE [dimension].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Dimension> searchSelected(int subjectId) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        String sql = "SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]"
                + "  WHERE [dimension].[subjectid] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                dimensions.add(dimension);
            }
            return dimensions;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Dimension> findBySubjectAndType(int subjectid, int typeid) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        String sql = "SELECT [dimension].[id]\n"
                + "      ,[dimension].[name]\n"
                + "      ,[dimension].[description]\n"
                + "      ,[dimension].[subjectid]\n"
                + "      ,[dimension].[typeid]\n"
                + "	 ,[st1].[type] as 'type_type'\n"
                + "      ,[st1].[value] as 'type_value'\n"
                + "      ,[st1].[order] as 'type_order'\n"
                + "      ,[st1].[status] as 'type_status'\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[st2].[type] as 'subject_category_type'\n"
                + "      ,[st2].[value] as 'subject_category_value'\n"
                + "      ,[st2].[order] as 'subject_category_order'\n"
                + "      ,[st2].[status] as 'subject_category_status'\n"
                + "  FROM [dimension]\n"
                + "  LEFT JOIN [setting] st1 ON [st1].[id] = [dimension].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [dimension].[subjectid]\n"
                + "  LEFT JOIN [setting] st2 ON [st2].[id] = [subject].[categoryid]"
                + "  WHERE [dimension].[subjectid] = ? AND [dimension].[typeid]=?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectid);
            stm.setInt(2, typeid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Dimension dimension = new Dimension();
                dimension.setId(rs.getInt("id"));
                dimension.setName(rs.getString("name"));
                dimension.setDescription(rs.getString("description"));
                dimension.setTypeId(rs.getInt("typeid"));
                dimension.setSubjectId(rs.getInt("subjectid"));

                Setting type = new Setting();
                type.setId(rs.getInt("typeid"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setOrder(rs.getInt("type_order"));
                dimension.setType(type);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryid"));
                category.setType(rs.getString("subject_category_type"));
                category.setValue(rs.getString("subject_category_value"));
                category.setStatus(rs.getBoolean("subject_category_status"));
                category.setOrder(rs.getInt("subject_category_order"));
                subject.setCategory(category);
                dimension.setSubject(subject);

                dimensions.add(dimension);
            }
            return dimensions;
        } catch (SQLException ex) {
            Logger.getLogger(DimensionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
