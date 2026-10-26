package com.neusoft.view;

import java.util.Scanner;

/**
 * 护理管理总视图：主菜单进入后，再选择具体护理子模块
 */
public class NurseManageView {

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 护理管理 -----");
            System.out.println("1. 护理内容管理");
            System.out.println("2. 护理等级管理");
            System.out.println("3. 护理等级项目管理");
            System.out.println("4. 护理记录管理");
            System.out.println("5. 客户护理项目管理");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": new NurseContentView().showView(); break;
                case "2": new NurseLevelView().showView(); break;
                case "3": new NurseLevelItemView().showView(); break;
                case "4": new NurseRecordView().showView(); break;
                case "5": new CustomerNurseItemView().showView(); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }
}
