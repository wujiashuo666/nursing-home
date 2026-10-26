package com.neusoft.entity;

import java.sql.Date;

/**
 * 外出申请实体
 */
public class Outward {
    private Long id;              // 申请id
    private String remarks;       // 备注
    private Integer isDeleted;    // 是否删除：0否 1是
    private Integer customerId;   // 客户id
    private String outgoingReason;    // 外出事由
    private Date outgoingTime;        // 外出时间
    private Date expectedReturnTime;  // 预计回院时间
    private Date actualReturnTime;    // 实际回院时间
    private String escorted;          // 陪同人
    private String relation;          // 与老人关系
    private String escortedTel;       // 陪同人电话
    private Integer auditStatus;      // 审批状态：0已提交 1通过 2不通过
    private String auditPerson;       // 审批人
    private Date auditTime;           // 审批时间

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Integer getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getOutgoingReason() { return outgoingReason; }
    public void setOutgoingReason(String outgoingReason) { this.outgoingReason = outgoingReason; }
    public Date getOutgoingTime() { return outgoingTime; }
    public void setOutgoingTime(Date outgoingTime) { this.outgoingTime = outgoingTime; }
    public Date getExpectedReturnTime() { return expectedReturnTime; }
    public void setExpectedReturnTime(Date expectedReturnTime) { this.expectedReturnTime = expectedReturnTime; }
    public Date getActualReturnTime() { return actualReturnTime; }
    public void setActualReturnTime(Date actualReturnTime) { this.actualReturnTime = actualReturnTime; }
    public String getEscorted() { return escorted; }
    public void setEscorted(String escorted) { this.escorted = escorted; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
    public String getEscortedTel() { return escortedTel; }
    public void setEscortedTel(String escortedTel) { this.escortedTel = escortedTel; }
    public Integer getAuditStatus() { return auditStatus; }
    public void setAuditStatus(Integer auditStatus) { this.auditStatus = auditStatus; }
    public String getAuditPerson() { return auditPerson; }
    public void setAuditPerson(String auditPerson) { this.auditPerson = auditPerson; }
    public Date getAuditTime() { return auditTime; }
    public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }

    @Override
    public String toString() {
        return "Outward [id=" + id + ", customerId=" + customerId
                + ", outgoingReason=" + outgoingReason + ", outgoingTime=" + outgoingTime
                + ", expectedReturnTime=" + expectedReturnTime + ", actualReturnTime=" + actualReturnTime
                + ", auditStatus=" + auditStatus + ", isDeleted=" + isDeleted + "]";
    }
}
