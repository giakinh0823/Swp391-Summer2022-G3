/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.slider;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.slider.Slider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lanh0
 */
public class SliderDBContext extends DBContext<Slider> {
    
    
    public ArrayList<Slider> getListActive() {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT [slider].[id]\n"
                    + "      ,[slider].[title]\n"
                    + "      ,[slider].[image]\n"
                    + "      ,[slider].[backlink]\n"
                    + "      ,[slider].[status]\n"
                    + "      ,[slider].[note]\n"
                    + "  FROM [slider]"
                    + " WHERE [slider].[status] = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                sliders.add(slider);
            }
        } catch (Exception ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    public ResultPageable<Slider> listPageable(Pageable pageable) {
        ResultPageable<Slider> resultPageable = new ResultPageable<>();
        ArrayList<Slider> sliders = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [slider].[id]\n"
                + "      ,[slider].[title]\n"
                + "      ,[slider].[image]\n"
                + "      ,[slider].[backlink]\n"
                + "      ,[slider].[status]\n"
                + "      ,[slider].[note]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [slider].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [slider]) [slider]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([slider].[id]) as size FROM [slider]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setInt(1, pageable.getPageIndex());
            stm.setInt(2, pageable.getPageSize());
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                sliders.add(slider);
            }
            resultPageable.setList(sliders);

            stm = connection.prepareCall(sql_count_all_data);
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Slider> searchByTitleAndStatusPageable(String name, boolean status, Pageable pageable) {
        ResultPageable<Slider> resultPageable = new ResultPageable<>();
        ArrayList<Slider> sliders = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [slider].[id]\n"
                + "      ,[slider].[title]\n"
                + "      ,[slider].[image]\n"
                + "      ,[slider].[backlink]\n"
                + "      ,[slider].[status]\n"
                + "      ,[slider].[note]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [slider].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [slider]"
                + " WHERE (([slider].[title] LIKE ? OR [slider].[backlink] LIKE ?) AND [slider].[status] = ? ) [slider]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([slider].[id]) as size FROM [slider]\n"
                + " WHERE ([slider].[title] LIKE ? OR [slider].[backlink] LIKE ?) AND [slider].[status] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + name + "%");
            stm.setString(2, "%" + name + "%");
            stm.setBoolean(3, status);
            stm.setInt(4, pageable.getPageIndex());
            stm.setInt(5, pageable.getPageSize());
            stm.setInt(6, pageable.getPageIndex());
            stm.setInt(7, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                sliders.add(slider);
            }
            resultPageable.setList(sliders);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + name + "%");
            stm.setString(2, "%" + name + "%");
            stm.setBoolean(3, status);
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Slider> searchByTitlePageable(String name, Pageable pageable) {
        ResultPageable<Slider> resultPageable = new ResultPageable<>();
        ArrayList<Slider> sliders = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [slider].[id]\n"
                + "      ,[slider].[title]\n"
                + "      ,[slider].[image]\n"
                + "      ,[slider].[backlink]\n"
                + "      ,[slider].[status]\n"
                + "      ,[slider].[note]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [slider].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [slider]"
                + " WHERE [slider].[title] LIKE ?) [slider]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([slider].[id]) as size FROM [slider]\n"
                + " WHERE [slider].[title] LIKE ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + name + "%");
            stm.setInt(2, pageable.getPageIndex());
            stm.setInt(3, pageable.getPageSize());
            stm.setInt(4, pageable.getPageIndex());
            stm.setInt(5, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                sliders.add(slider);
            }
            resultPageable.setList(sliders);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + name + "%");
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Slider> list() {
        ArrayList<Slider> sliders = new ArrayList<>();
        try {
            String sql = "SELECT [slider].[id]\n"
                    + "      ,[slider].[title]\n"
                    + "      ,[slider].[image]\n"
                    + "      ,[slider].[backlink]\n"
                    + "      ,[slider].[status]\n"
                    + "      ,[slider].[note]\n"
                    + "  FROM [slider]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                sliders.add(slider);
            }
        } catch (Exception ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sliders;
    }

    @Override
    public Slider get(int id) {
        try {
            String sql = "SELECT [id]\n"
                    + "      ,[title]\n"
                    + "      ,[image]\n"
                    + "      ,[backlink]\n"
                    + "      ,[status]\n"
                    + "      ,[note]\n"
                    + "  FROM [slider]\n"
                    + "  Where [id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                return slider;
            }
        } catch (Exception ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Slider insert(Slider model) {
        String sql = "INSERT INTO [slider]\n"
                + "           ([title]\n"
                + "           ,[image]\n"
                + "           ,[backlink]\n"
                + "           ,[status]\n"
                + "           ,[note])\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getTitle());
            stm.setString(2, model.getImage());
            stm.setString(3, model.getBacklink());
            stm.setBoolean(4, model.isStatus());
            stm.setString(5, model.getNotes());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Slider model) {
        String sql = "UPDATE [slider]\n"
                + "   SET [title] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[backlink] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[note] = ?\n"
                + " WHERE [slider].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getTitle());
            stm.setString(2, model.getImage());
            stm.setString(3, model.getBacklink());
            stm.setBoolean(4, model.isStatus());
            stm.setString(5, model.getNotes());
            stm.setInt(6, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM [slider]\n"
                + "      WHERE [slider].[id]=?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByIds(List<Integer> listIds) {
        String sql = "DELETE FROM [slider]\n"
                + "      WHERE [slider].[id]=?";
        PreparedStatement stm = null;
        try {
            connection.setAutoCommit(false);
            for (Integer id : listIds) {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, id);
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultPageable<Slider> searchByTitleOrBackLinkAndFilterStatusPageable(String search, String statusParams, Pageable pageable) {
        ResultPageable<Slider> resultPageable = new ResultPageable<>();
        ArrayList<Slider> sliders = new ArrayList<>();

        boolean status = statusParams != null && !statusParams.trim().isEmpty() && statusParams.equalsIgnoreCase("active");

        String sql_query_data = "SELECT * FROM(SELECT [slider].[id]\n"
                + "      ,[slider].[title]\n"
                + "      ,[slider].[image]\n"
                + "      ,[slider].[backlink]\n"
                + "      ,[slider].[status]\n"
                + "      ,[slider].[note]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [slider].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [slider]"
                + " WHERE ([slider].[title] LIKE ? OR [slider].[backlink] LIKE ?) ";
        if (statusParams != null && !statusParams.trim().isEmpty()) {
            sql_query_data += " AND [slider].[status] = ? ";
        }
        sql_query_data += " ) [slider]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([slider].[id]) as size FROM [slider]\n"
                + " WHERE ([slider].[title] LIKE ? OR [slider].[backlink] LIKE ?) ";
        if (statusParams != null && !statusParams.trim().isEmpty()) {
            sql_count_all_data += " AND [slider].[status] = ? ";
        }

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (statusParams != null && !statusParams.trim().isEmpty()) {
                stm.setBoolean(3, status);
                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
                stm.setInt(6, pageable.getPageIndex());
                stm.setInt(7, pageable.getPageSize());
            } else {
                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNotes(rs.getString("note"));
                sliders.add(slider);
            }
            resultPageable.setList(sliders);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (statusParams != null && !statusParams.trim().isEmpty()) {
                stm.setBoolean(3, status);
            }

            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SliderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    

}
