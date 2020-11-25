package org.jeecg.modules.goods.entity;

import java.io.Serializable;

/**
 * @Description
 * @Author HuHuanJin
 * @Date 2020-11-06 14:42
 */
public class SkuDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String skuKey;
    private String salePrice;
    private Integer orderNum;
    private String stock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuKey() {
        return skuKey;
    }

    public void setSkuKey(String skuKey) {
        this.skuKey = skuKey;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
