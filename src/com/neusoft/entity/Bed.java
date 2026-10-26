package com.neusoft.entity;

/**
 * 床位实体
 */
public class Bed {
    private Long id;            // 床位id
    private int roomNo;     // 房间号
    private int bedStatus;  // 床位状态：1空闲 2有人 3外出
    private String bedNo;       // 床位号
    private String remarks;     // 备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(int bedStatus) {
        this.bedStatus = bedStatus;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Bed [id=" + id + ", roomNo=" + roomNo + ", bedStatus=" + bedStatus
                + ", bedNo=" + bedNo + ", remarks=" + remarks + "]";
    }
}
