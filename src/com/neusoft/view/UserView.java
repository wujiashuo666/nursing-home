package com.neusoft.view;

import com.neusoft.controller.UserController;
import com.neusoft.entity.User;

import java.util.List;
import java.util.Scanner;

/**
 * 用户管理视图
 */
public class UserView {
    private static Scanner sc = new Scanner(System.in);
    private static UserController userController = new UserController();

    public void showView() {
        while (true) {
            System.out.println("\n======= 用户管理模块 =======");
            System.out.println("1. 查询用户列表");
            System.out.println("2. 新增用户");
            System.out.println("3. 修改用户信息");
            System.out.println("4. 删除用户（逻辑删除）");
            System.out.println("0. 返回主菜单");
            System.out.print("请输入功能编号：");
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    findAllUserView();
                    break;
                case 2:
                    addUserView();
                    break;
                case 3:
                    updateUserView();
                    break;
                case 4:
                    deleteUserView();
                    break;
                case 0:
                    System.out.println("返回主菜单");
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    private void findAllUserView() {
        List<User> list = userController.findAll();
        System.out.println("用户列表：");
        for (User u : list) {
            System.out.println(u);
        }
    }

    private void addUserView() {
        User user = new User();
        System.out.print("输入登录账号：");
        user.setUsername(sc.nextLine());
        System.out.print("输入登录密码：");
        user.setPassword(sc.nextLine());
        System.out.print("输入昵称：");
        user.setNickname(sc.nextLine());
        System.out.print("输入手机号：");
        user.setPhone(sc.nextLine());
        System.out.print("输入角色（1管理员 2健康管家）：");
        user.setRole(sc.nextInt());
        sc.nextLine();
        String msg = userController.addUser(user);
        System.out.println(msg);
    }

    private void updateUserView() {
        System.out.print("输入要修改的用户ID：");
        Long id = sc.nextLong();
        sc.nextLine();
        User user = new User();
        user.setId(id);
        System.out.print("输入新昵称（直接回车不修改）：");
        String nickname = sc.nextLine();
        if (!nickname.isEmpty()) {
            user.setNickname(nickname);
        }
        System.out.print("输入新手机号（直接回车不修改）：");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) {
            user.setPhone(phone);
        }
        String msg = userController.updateUser(user);
        System.out.println(msg);
    }

    private void deleteUserView() {
        System.out.print("输入要删除的用户ID：");
        Long id = sc.nextLong();
        sc.nextLine();
        String msg = userController.logicDeleteUser(id);
        System.out.println(msg);
    }
}
