package com.neusoft.view;

import java.util.Scanner;

/**
 * 主菜单视图
 */
public class HomeView {
    public HomeView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n========== 养老管理系统==========");
            System.out.println("1. 客户管理");
            System.out.println("2. 床位管理");
            System.out.println("3. 护理管理");
            System.out.println("4. 健康管家");
            System.out.println("5. 用户管理");
            System.out.println("0. 退出系统");
            System.out.print("请输入功能编号：");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    new CustomerView().showView();
                    break;
                case "2":
                    new BedManageView().showView();
                    break;
                case "3":
                    new NurseManageView().showView();
                    break;
                case "4":
                    new GuardManageView().showView();
                    break;
                case "5":
                    new UserView().showView();
                    break;
                case "0":
                    System.out.println("系统退出，再见！");
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }
}
