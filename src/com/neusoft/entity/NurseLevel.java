package com.neusoft.entity;

/**
 * 护理等级实体
 */
public class NurseLevel {
    private Long id;            // 等级id
    private String levelName;   // 等级名称
    private Integer levelStatus;// 等级状态：1启用 0停用
    private Integer isDeleted;  // 是否删除：0否 1是

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getLevelName() { 
        return levelName; 
    }
    public void setLevelName(String levelName) { 
        this.levelName = levelName; 
    }
    public Integer getLevelStatus() { 
        return levelStatus; 
    }
    public void setLevelStatus(Integer levelStatus) { 
        this.levelStatus = levelStatus; 
    }
    public Integer getIsDeleted() { 
        return isDeleted; 
    }
    public void setIsDeleted(Integer isDeleted) { 
        this.isDeleted = isDeleted; 
    }

    @Override
    public String toString() {
        return "NurseLevel [id=" + id + ", levelName=" + levelName
                + ", levelStatus=" + levelStatus + ", isDeleted=" + isDeleted + "]";
    }
}
