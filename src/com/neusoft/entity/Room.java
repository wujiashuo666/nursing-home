package com.neusoft.entity;

/**
 * 房间实体
 */
public class Room {
    private Long id;           // 房间id
    private String roomFloor;  // 楼层
    private int roomNo;    // 房间号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", roomFloor=" + roomFloor + ", roomNo=" + roomNo + "]";
    }
}
