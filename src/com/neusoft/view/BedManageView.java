package com.neusoft.view;

import java.util.Scanner;

/**
 * 床位管理总视图：主菜单进入后，再选择"床位示意图 / 床位管理"
 */
public class BedManageView {

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 床位管理 -----");
            System.out.println("1. 床位示意图");
            System.out.println("2. 床位管理");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    new BedView().showView();
                    break;
                case "2":
                    new BedDetailsView().showView();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("输入无效");
            }
        }
    }
}
