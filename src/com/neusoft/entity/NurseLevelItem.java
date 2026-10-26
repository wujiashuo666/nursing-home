package com.neusoft.entity;

/**
 * 护理等级项目(护理等级-护理内容关联)实体
 */
public class NurseLevelItem {
    private Long id;         // 关联id
    private Integer levelId; // 护理等级id
    private Integer itemId;  // 护理内容(项目)id

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id;
    }
    public Integer getLevelId() { 
        return levelId; 
    }
    public void setLevelId(Integer levelId) { 
        this.levelId = levelId; 
    }
    public Integer getItemId() { 
        return itemId; 
    }
    public void setItemId(Integer itemId) { 
        this.itemId = itemId; 
    }

    @Override
    public String toString() {
        return "NurseLevelItem [id=" + id + ", levelId=" + levelId
                + ", itemId=" + itemId + "]";
    }
}
