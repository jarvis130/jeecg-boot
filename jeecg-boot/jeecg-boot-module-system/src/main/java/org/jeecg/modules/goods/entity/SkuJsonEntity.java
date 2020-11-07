package org.jeecg.modules.goods.entity;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * @Description
 * @Author HuHuanJin
 * @Date 2020-11-06 14:17
 */
public class SkuJsonEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    private JSONArray dataSource;
    private JSONArray columns;
    private JSONArray skus;

    public JSONArray getDataSource() {
        return dataSource;
    }

    public void setDataSource(JSONArray dataSource) {
        this.dataSource = dataSource;
    }

    public JSONArray getColumns() {
        return columns;
    }

    public void setColumns(JSONArray columns) {
        this.columns = columns;
    }

    public JSONArray getSkus() {
        return skus;
    }

    public void setSkus(JSONArray skus) {
        this.skus = skus;
    }
}
