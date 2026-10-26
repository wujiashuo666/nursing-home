package com.neusoft.entity;

import java.sql.Date;

/**
 * 客户护理项目实体
 */
public class CustomerNurseItem {
    private Long id;            // 分配id
    private Integer itemId;     // 护理项目id
    private Integer customerId; // 客户id
    private Integer levelId;    // 护理等级id
    private Integer nurseNumber;// 护理次数
    private Integer isDeleted;  // 是否删除：0否 1是
    private Date buyTime;       // 购买时间
    private Date maturityTime;  // 到期时间

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public Integer getItemId() { 
        return itemId; 
    }
    public void setItemId(Integer itemId) { 
        this.itemId = itemId; 
    }
    public Integer getCustomerId() { 
        return customerId; 
    }
    public void setCustomerId(Integer customerId) { 
        this.customerId = customerId; 
    }
    public Integer getLevelId() { 
        return levelId; 
    }
    public void setLevelId(Integer levelId) { 
        this.levelId = levelId; 
    }
    public Integer getNurseNumber() { 
        return nurseNumber; 
    }
    public void setNurseNumber(Integer nurseNumber) { 
        this.nurseNumber = nurseNumber; 
    }
    public Integer getIsDeleted() { 
        return isDeleted; 
    }
    public void setIsDeleted(Integer isDeleted) { 
        this.isDeleted = isDeleted; 
    }
    public Date getBuyTime() { 
        return buyTime; 
    }
    public void setBuyTime(Date buyTime) { 
        this.buyTime = buyTime; 
    }
    public Date getMaturityTime() { 
        return maturityTime; 
    }
    public void setMaturityTime(Date maturityTime) { 
        this.maturityTime = maturityTime; 
    }

    @Override
    public String toString() {
        return "CustomerNurseItem [id=" + id + ", itemId=" + itemId
                + ", customerId=" + customerId + ", levelId=" + levelId
                + ", nurseNumber=" + nurseNumber + ", buyTime=" + buyTime
                + ", maturityTime=" + maturityTime + ", isDeleted=" + isDeleted + "]";
    }
}
