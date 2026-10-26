package com.neusoft.entity;

import java.sql.Timestamp;

/**
 * 护理记录实体
 */
public class NurseRecord {
    private Long id;              // 记录id
    private Integer isDeleted;    // 是否删除：0否 1是
    private Integer customerId;   // 客户id
    private Integer itemId;       // 护理项目id
    private Timestamp nursingTime;// 护理时间
    private String nursingContent;// 护理内容
    private Integer nursingCount; // 护理次数
    private Integer userId;       // 护理人员(用户)id

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public Integer getIsDeleted() { 
        return isDeleted; 
    }
    public void setIsDeleted(Integer isDeleted) { 
        this.isDeleted = isDeleted; 
    }
    public Integer getCustomerId() { 
        return customerId; 
    }
    public void setCustomerId(Integer customerId) { 
        this.customerId = customerId; 
    }
    public Integer getItemId() { 
        return itemId; 
    }
    public void setItemId(Integer itemId) { 
        this.itemId = itemId; 
    }
    public Timestamp getNursingTime() { 
        return nursingTime; 
    }
    public void setNursingTime(Timestamp nursingTime) { 
        this.nursingTime = nursingTime; 
    }
    public String getNursingContent() { 
        return nursingContent; 
    }
    public void setNursingContent(String nursingContent) { 
        this.nursingContent = nursingContent; 
    }
    public Integer getNursingCount() { 
        return nursingCount; 
    }
    public void setNursingCount(Integer nursingCount) { 
        this.nursingCount = nursingCount; 
    }
    public Integer getUserId() { 
        return userId; 
    }
    public void setUserId(Integer userId) { 
        this.userId = userId; 
    }

    @Override
    public String toString() {
        return "NurseRecord [id=" + id + ", customerId=" + customerId
                + ", itemId=" + itemId + ", nursingTime=" + nursingTime
                + ", nursingContent=" + nursingContent + ", nursingCount=" + nursingCount
                + ", userId=" + userId + ", isDeleted=" + isDeleted + "]";
    }
}
