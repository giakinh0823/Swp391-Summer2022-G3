/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giaki
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {

    private int pageIndex;
    private int pageSize;
    private String fieldSort;
    private String order;
    private Map<String, String> orderings;
    private Map<String, ArrayList<String>> filters;

    public Pageable(int pageIndex, int pageSize, String fieldSort, String order) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.fieldSort = fieldSort;
        this.order = order;
    }

    public boolean checkSort(String fieldSort) {
        if (orderings != null) {
            for (Map.Entry<String, String> entry : orderings.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                key = key.replaceFirst("[0-9]+.", "");
                if ((key + "_" + val).equalsIgnoreCase(fieldSort)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkField(String field) {
        if (orderings != null && !orderings.isEmpty()) {
            for (Map.Entry<String, String> entry : orderings.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                key = key.replaceFirst("[0-9]+.", "");
                if ((key).equalsIgnoreCase(field)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkFilters(String field, String value) {
        if (filters != null) {
            for (Map.Entry<String, ArrayList<String>> entry : filters.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> val = entry.getValue();
                if (key.equalsIgnoreCase(field)) {
                    for (String string : val) {
                        if (string.equalsIgnoreCase(value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkFilter(String field) {
        if (filters != null) {
            for (Map.Entry<String, ArrayList<String>> entry : filters.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> val = entry.getValue();
                if (key.equalsIgnoreCase(field)) {
                    return true;
                }
            }
        }
        return false;
    }
}
