package com.neusoft.entity;

import java.sql.Date;

/**
 * 退住申请实体
 */
public class Backdown {
    private Long id;              // 申请id
    private String remarks;       // 备注
    private Integer isDeleted;    // 是否删除：0否 1是
    private Integer customerId;   // 客户id
    private Date retreatTime;     // 退住时间
    private Integer retreatType;  // 退住类型：1正常退住 2死亡退住 3保留床位
    private String retreatReason; // 退住原因
    private Integer auditStatus;  // 审批状态：0已提交 1通过 2不通过
    private String auditPerson;   // 审批人
    private Date auditTime;       // 审批时间

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Integer getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Date getRetreatTime() { return retreatTime; }
    public void setRetreatTime(Date retreatTime) { this.retreatTime = retreatTime; }
    public Integer getRetreatType() { return retreatType; }
    public void setRetreatType(Integer retreatType) { this.retreatType = retreatType; }
    public String getRetreatReason() { return retreatReason; }
    public void setRetreatReason(String retreatReason) { this.retreatReason = retreatReason; }
    public Integer getAuditStatus() { return auditStatus; }
    public void setAuditStatus(Integer auditStatus) { this.auditStatus = auditStatus; }
    public String getAuditPerson() { return auditPerson; }
    public void setAuditPerson(String auditPerson) { this.auditPerson = auditPerson; }
    public Date getAuditTime() { return auditTime; }
    public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }

    @Override
    public String toString() {
        return "Backdown [id=" + id + ", customerId=" + customerId
                + ", retreatType=" + retreatType + ", retreatReason=" + retreatReason
                + ", retreatTime=" + retreatTime + ", auditStatus=" + auditStatus
                + ", isDeleted=" + isDeleted + "]";
    }
}
