package com.neusoft.entity;

import java.sql.Date;

/**
 * 客户实体
 */
public class Customer {
    private Long id;                    // 客户id
    private int isDeleted;          // 是否删除：0否 1是
    private String customerName;        // 客户姓名
    private int customerAge;        // 年龄
    private int customerSex;        // 性别：1男 2女
    private String idcard;              // 身份证号
    private String roomNo;              // 房间号
    private String buildingNo;          // 楼号
    private Date checkinDate;           // 入住日期
    private Date expirationDate;        // 到期日期
    private String contactTel;          // 联系电话
    private int bedId;              // 床位id
    private String psychosomaticState;  // 身心状态
    private String attention;           // 注意事项
    private Date birthday;              // 生日
    private String height;              // 身高
    private String weight;              // 体重
    private String bloodType;           // 血型
    private String filepath;            // 档案路径
    private int userId;             // 用户id
    private int levelId;            // 等级id
    private String familyMember;        // 家属

    public Long getId() { return id; }
    public void setId(Long id) { 
        this.id = id; 
    }
    public int getIsDeleted() { 
        return isDeleted; 
    }
    public void setIsDeleted(int isDeleted) { 
        this.isDeleted = isDeleted; 
    }
    public String getCustomerName() { 
        return customerName; 
    }
    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    }
    public int getCustomerAge() { 
        return customerAge; 
    }
    public void setCustomerAge(int customerAge) { 
        this.customerAge = customerAge; 
    }
    public int getCustomerSex() { 
        return customerSex; 
    }
    public void setCustomerSex(int customerSex) { 
        this.customerSex = customerSex; 
    }
    public String getIdcard() { 
        return idcard; 
    }
    public void setIdcard(String idcard) { 
        this.idcard = idcard; 
    }
    public String getRoomNo() { 
        return roomNo; 
    }
    public void setRoomNo(String roomNo) { 
        this.roomNo = roomNo; 
    }
    public String getBuildingNo() { 
        return buildingNo; 
    }
    public void setBuildingNo(String buildingNo) { 
        this.buildingNo = buildingNo; 
    }
    public Date getCheckinDate() { 
        return checkinDate; 
    }
    public void setCheckinDate(Date checkinDate) { 
        this.checkinDate = checkinDate; 
    }
    public Date getExpirationDate() { 
        return expirationDate; 
    }
    public void setExpirationDate(Date expirationDate) { 
        this.expirationDate = expirationDate; 
    }
    public String getContactTel() { 
        return contactTel; 
    }
    public void setContactTel(String contactTel) { 
        this.contactTel = contactTel; 
    }
    public int getBedId() { 
        return bedId; 
    }
    public void setBedId(int bedId) { 
        this.bedId = bedId; 
    }
    public String getPsychosomaticState() { 
        return psychosomaticState; 
    }
    public void setPsychosomaticState(String psychosomaticState) { 
        this.psychosomaticState = psychosomaticState; 
    }
    public String getAttention() { 
        return attention; 
    }
    public void setAttention(String attention) { 
        this.attention = attention; 
    }
    public Date getBirthday() { 
        return birthday; 
    }
    public void setBirthday(Date birthday) { 
        this.birthday = birthday; 
    }
    public String getHeight() { 
        return height; 
    }
    public void setHeight(String height) { 
        this.height = height; 
    }
    public String getWeight() { 
        return weight; 
    }
    public void setWeight(String weight) { 
        this.weight = weight; 
    }
    public String getBloodType() { 
        return bloodType; 
    }
    public void setBloodType(String bloodType) { 
        this.bloodType = bloodType; 
    }
    public String getFilepath() { 
        return filepath; 
    }
    public void setFilepath(String filepath) { 
        this.filepath = filepath; 
    }
    public int getUserId() { 
        return userId; 
    }
    public void setUserId(int userId) { 
        this.userId = userId; 
    }
    public int getLevelId() { 
        return levelId; 
    }
    public void setLevelId(int levelId) { 
        this.levelId = levelId; 
    }
    public String getFamilyMember() { 
        return familyMember; 
    }
    public void setFamilyMember(String familyMember) { 
        this.familyMember = familyMember; 
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", customerName=" + customerName + ", roomNo=" + roomNo
                + ", buildingNo=" + buildingNo + ", bedId=" + bedId + "]";
    }
}
