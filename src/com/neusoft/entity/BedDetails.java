package com.neusoft.entity;

import java.sql.Date;

/**
 * 床位使用详情实体
 */
public class BedDetails {
    private Long id;              // 详情id
    private Date startDate;       // 入住日期
    private Date endDate;         // 结束日期(为空表示正在使用)
    private String bedDetails;    // 详情备注
    private int customerId;   // 客户id
    private int bedId;        // 床位id
    private int isDeleted;    // 是否删除：0否 1是

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBedDetails() {
        return bedDetails;
    }

    public void setBedDetails(String bedDetails) {
        this.bedDetails = bedDetails;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "BedDetails [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate
                + ", bedDetails=" + bedDetails + ", customerId=" + customerId
                + ", bedId=" + bedId + ", isDeleted=" + isDeleted + "]";
    }
}