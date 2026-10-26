package com.neusoft.entity;

/**
 * 用户实体类
 */
public class User {
    private Long id;              // 用户id
    private String username;      // 登录账号
    private String password;      // 登录密码
    private String nickname;      // 昵称
    private String phone;         // 手机号
    private Integer role;         // 角色：1管理员 2健康管家
    private Integer isDeleted;    // 是否删除：0否 1是

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
    public Integer getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Integer isDeleted) { this.isDeleted = isDeleted; }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", nickname=" + nickname
                + ", phone=" + phone + ", role=" + role + ", isDeleted=" + isDeleted + "]";
    }
}
