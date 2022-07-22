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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.subject.Subject;

public class PackagePriceDBContext extends DBContext<PackagePrice> {

    public ArrayList<PackagePrice> findBySubjectId(int id) {
        ArrayList<PackagePrice> packages = new ArrayList<>();
        String sql = "SELECT [price_package].[id]\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status]\n"
                + "      ,[price_package].[subjectid]\n"
                + "      ,[price_package].[description]\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status] as 'setting_status'\n"
                + "  FROM [price_package]\n"
                + "  LEFT JOIN [subject] ON [subject].[id] = [price_package].[subjectid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]"
                + "  WHERE [subject].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PackagePrice package_price = new PackagePrice();
                package_price.setId(rs.getInt("id"));
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("status"));
                package_price.setSubjectId(rs.getInt("subjectid"));
                package_price.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                package_price.setSubject(subject);
                packages.add(package_price);
            }
            return packages;
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    public ArrayList<PackagePrice> findByLessonId(int id) {
        ArrayList<PackagePrice> packages = new ArrayList<>();
        String sql = "SELECT [price_package].[id]\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status]\n"
                + "      ,[price_package].[subjectid]\n"
                + "      ,[price_package].[description]\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status] as 'setting_status'\n"
                + "  FROM [price_package]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [price_package].[subjectid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]"
                + "  INNER JOIN [lesson_price_package] on [lesson_price_package].[price_packageid] = [price_package].[id]"
                + "  WHERE [lesson_price_package].[lessonid] = ? ";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PackagePrice package_price = new PackagePrice();
                package_price.setId(rs.getInt("id"));
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("status"));
                package_price.setSubjectId(rs.getInt("subjectid"));
                package_price.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                package_price.setSubject(subject);
                packages.add(package_price);
            }
            return packages;
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<PackagePrice> findBySubjectIdAndActive(int id) {
        ArrayList<PackagePrice> packages = new ArrayList<>();
        String sql = "SELECT [price_package].[id]\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status]\n"
                + "      ,[price_package].[subjectid]\n"
                + "      ,[price_package].[description]\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status] as 'setting_status'\n"
                + "  FROM [price_package]\n"
                + "  LEFT JOIN [subject] ON [subject].[id] = [price_package].[subjectid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]"
                + "  WHERE [subject].[id] = ? AND [price_package].[status] = 1";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PackagePrice package_price = new PackagePrice();
                package_price.setId(rs.getInt("id"));
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("status"));
                package_price.setSubjectId(rs.getInt("subjectid"));
                package_price.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                package_price.setSubject(subject);
                packages.add(package_price);
            }
            return packages;
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<PackagePrice> findBySubjectIdPageable(int id, Pageable pageable) {
        ArrayList<PackagePrice> packages = new ArrayList<>();
        ResultPageable<PackagePrice> resultPageable = new ResultPageable<>();
        String sql = "SELECT * FROM(SELECT [price_package].[id]\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status]\n"
                + "      ,[price_package].[subjectid]\n"
                + "      ,[price_package].[description]\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status] as 'setting_status'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [price_package].[id] DESC) as row_index"
                + "  FROM [price_package]\n"
                + "  LEFT JOIN [subject] ON [subject].[id] = [price_package].[subjectid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]"
                + "  WHERE [subject].[id] = ?) [price_package]"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_data = "SELECT COUNT([price_package].[id]) as size FROM [price_package]\n"
                + " WHERE [price_package].[subjectid] = ? ";

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
                PackagePrice package_price = new PackagePrice();
                package_price.setId(rs.getInt("id"));
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("status"));
                package_price.setSubjectId(rs.getInt("subjectid"));
                package_price.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                package_price.setSubject(subject);
                packages.add(package_price);
            }
            resultPageable.setList(packages);
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
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<PackagePrice> list() {
        ArrayList<PackagePrice> packages = new ArrayList<>();
        String sql = "SELECT [price_package].[id]\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status]\n"
                + "      ,[price_package].[subjectid]\n"
                + "      ,[price_package].[description]\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status] as 'setting_status'\n"
                + "  FROM [price_package]\n"
                + "  LEFT JOIN [subject] ON [subject].[id] = [price_package].[subjectid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PackagePrice package_price = new PackagePrice();
                package_price.setId(rs.getInt("id"));
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("status"));
                package_price.setSubjectId(rs.getInt("subjectid"));
                package_price.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                package_price.setSubject(subject);
                packages.add(package_price);
            }
            return packages;
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PackagePrice get(int id) {
        String sql = "SELECT [price_package].[id]\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status]\n"
                + "      ,[price_package].[subjectid]\n"
                + "      ,[price_package].[description]\n"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[image]\n"
                + "	 ,[setting].[type]\n"
                + "      ,[setting].[value]\n"
                + "      ,[setting].[order]\n"
                + "      ,[setting].[status] as 'setting_status'\n"
                + "  FROM [price_package]\n"
                + "  LEFT JOIN [subject] ON [subject].[id] = [price_package].[subjectid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  WHERE [price_package].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PackagePrice package_price = new PackagePrice();
                package_price.setId(rs.getInt("id"));
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("status"));
                package_price.setSubjectId(rs.getInt("subjectid"));
                package_price.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                package_price.setSubject(subject);
                return package_price;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PackagePrice insert(PackagePrice model) {
        String sql = "INSERT INTO [price_package]\n"
                + "           ([name]\n"
                + "           ,[duration]\n"
                + "           ,[list_price]\n"
                + "           ,[sale_price]\n"
                + "           ,[status]\n"
                + "           ,[description]\n"
                + "           ,[subjectid])\n"
                + "     VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getName());
            stm.setInt(2, model.getDuration());
            stm.setDouble(3, model.getList_price());
            stm.setDouble(4, model.getSale_price());
            stm.setBoolean(5, model.isStatus());
            stm.setString(6, model.getDescription());
            stm.setInt(7, model.getSubject().getId());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void update(PackagePrice model) {
        String sql = "UPDATE [price_package]\n"
                + "     SET   [name] = ?\n"
                + "           ,[duration] = ?\n"
                + "           ,[list_price] = ?\n"
                + "           ,[sale_price] = ?\n"
                + "           ,[status] = ?\n"
                + "           ,[description] = ?\n"
                + "           ,[subjectid] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setInt(2, model.getDuration());
            stm.setDouble(3, model.getList_price());
            stm.setDouble(4, model.getSale_price());
            stm.setBoolean(5, model.isStatus());
            stm.setString(6, model.getDescription());
            stm.setInt(7, model.getSubject().getId());
            stm.setInt(8, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM [price_package]\n"
                + " WHERE [price_package].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PackagePriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
