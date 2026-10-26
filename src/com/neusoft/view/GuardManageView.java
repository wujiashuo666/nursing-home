package com.neusoft.view;

import java.util.Scanner;

import com.neusoft.controller.CustomerController;

/**
 * 健康管家(护工)主菜单视图
 * 进入时手动输入当前登录健康管家的 user_id，后续所有功能基于该身份过滤服务客户
 */
public class GuardManageView {
    private CustomerController customerController = new CustomerController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        int guardId;
        try {
            System.out.print("请输入你的用户ID(user_id): ");
            guardId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("用户ID输入无效，返回上级菜单");
            return;
        }
        String nickname = customerController.getGuardNickname(guardId);
        if (nickname == null) {
            System.out.println("提示：未在user表中找到该健康管家(user_id=" + guardId + ")，服务客户列表可能为空");
        } else {
            System.out.println("当前健康管家：" + nickname + "(user_id=" + guardId + ")");
        }

        while (true) {
            System.out.println("\n----- 健康管家 -----");
            System.out.println("1. 日常护理");
            System.out.println("2. 服务对象护理记录");
            System.out.println("3. 外出申请");
            System.out.println("4. 退住申请");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": new DailyCareView(guardId).showView(); break;
                case "2": new GuardNurseRecordView(guardId).showView(); break;
                case "3": new OutwardApplyView(guardId).showView(); break;
                case "4": new BackdownApplyView(guardId).showView(); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }
}
