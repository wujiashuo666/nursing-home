package com.neusoft.entity;

/**
 * 护理内容(护理项目)实体
 */
public class NurseContent {
    private Long id;              // 护理内容id
    private String serialNumber;  // 编号
    private String nursingName;   // 护理名称
    private String servicePrice;  // 服务价格
    private String message;       // 说明
    private Integer status;       // 状态：1启用 0停用
    private String executionCycle;// 执行周期
    private String executionTimes;// 执行次数
    private Integer isDeleted;    // 是否删除：0否 1是

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getSerialNumber() { 
        return serialNumber; 
    }
    public void setSerialNumber(String serialNumber) { 
        this.serialNumber = serialNumber; 
    }
    public String getNursingName() { 
        return nursingName; 
    }
    public void setNursingName(String nursingName) { 
        this.nursingName = nursingName; 
    }
    public String getServicePrice() { 
        return servicePrice; 
    }
    public void setServicePrice(String servicePrice) { 
        this.servicePrice = servicePrice; 
    }
    public String getMessage() { 
        return message; 
    }
    public void setMessage(String message) { 
        this.message = message; 
    }
    public Integer getStatus() { 
        return status; 
    }
    public void setStatus(Integer status) { 
        this.status = status; 
    }
    public String getExecutionCycle() { 
        return executionCycle; 
    }
    public void setExecutionCycle(String executionCycle) { 
        this.executionCycle = executionCycle; 
    }
    public String getExecutionTimes() { 
        return executionTimes; 
    }
    public void setExecutionTimes(String executionTimes) { 
        this.executionTimes = executionTimes; 
    }
    public Integer getIsDeleted() { 
        return isDeleted; 
    }
    public void setIsDeleted(Integer isDeleted) { 
        this.isDeleted = isDeleted; 
    }

    @Override
    public String toString() {
        return "NurseContent [id=" + id + ", serialNumber=" + serialNumber
                + ", nursingName=" + nursingName + ", servicePrice=" + servicePrice
                + ", message=" + message + ", status=" + status
                + ", executionCycle=" + executionCycle + ", executionTimes=" + executionTimes
                + ", isDeleted=" + isDeleted + "]";
    }
}
