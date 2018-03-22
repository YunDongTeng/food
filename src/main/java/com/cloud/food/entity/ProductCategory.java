package com.cloud.food.entity;


import org.hibernate.annotations.DynamicUpdate;
import org.junit.runner.RunWith;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
@DynamicUpdate
public class ProductCategory {

    @Id
    private String categoryId;

    private String categoryName;

    private String categoryType;

    private Date createTime;

    private Date updateTime;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
