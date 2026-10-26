package com.neusoft.view;

import java.util.List;
import java.util.Scanner;

import com.neusoft.controller.NurseLevelController;
import com.neusoft.entity.NurseLevel;

/**
 * 护理等级管理视图
 */
public class NurseLevelView {
    private NurseLevelController controller = new NurseLevelController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 护理等级管理 -----");
            System.out.println("1. 查询护理等级列表");
            System.out.println("2. 新增护理等级");
            System.out.println("3. 修改护理等级");
            System.out.println("4. 删除护理等级");
            System.out.println("5. 查询护理等级单条");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": list(); break;
                case "2": add(scanner); break;
                case "3": update(scanner); break;
                case "4": delete(scanner); break;
                case "5": getOne(scanner); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }

    private void list() {
        System.out.println("----护理等级列表----");
        List<NurseLevel> list = controller.list();
        if (list.isEmpty()) {
            System.out.println("暂无护理等级数据");
            return;
        }
        for (NurseLevel nl : list) {
            System.out.println("ID=" + nl.getId()
                    + " | 等级名称=" + nl.getLevelName()
                    + " | 状态=" + statusText(nl.getLevelStatus()));
        }
    }

    private void add(Scanner scanner) {
        System.out.println("----新增护理等级----");
        try {
            NurseLevel nl = new NurseLevel();
            System.out.print("等级名称 level_name: ");
            nl.setLevelName(scanner.nextLine().trim());
            System.out.print("等级状态 1启用 0停用: ");
            nl.setLevelStatus(Integer.parseInt(scanner.nextLine().trim()));
            boolean ok = controller.add(nl);
            System.out.println(ok ? "新增成功！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：状态格式错误");
        }
    }

    private void update(Scanner scanner) {
        System.out.println("----修改护理等级----");
        try {
            System.out.print("请输入护理等级ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseLevel nl = new NurseLevel();
            nl.setId(id);
            System.out.print("等级名称 level_name: ");
            nl.setLevelName(scanner.nextLine().trim());
            System.out.print("等级状态 1启用 0停用: ");
            nl.setLevelStatus(Integer.parseInt(scanner.nextLine().trim()));
            boolean ok = controller.update(nl);
            System.out.println(ok ? "修改成功！" : "修改失败：未找到该护理等级");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或状态格式错误");
        }
    }

    private void delete(Scanner scanner) {
        System.out.println("----删除护理等级----");
        try {
            System.out.print("请输入护理等级ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            boolean ok = controller.delete(id);
            System.out.println(ok ? "删除成功！" : "删除失败：未找到该护理等级");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void getOne(Scanner scanner) {
        System.out.println("----查询护理等级单条----");
        try {
            System.out.print("请输入护理等级ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseLevel nl = controller.getById(id);
            if (nl == null) {
                System.out.println("未找到该护理等级");
                return;
            }
            System.out.println("ID=" + nl.getId()
                    + " | 等级名称=" + nl.getLevelName()
                    + " | 状态=" + statusText(nl.getLevelStatus()));
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private String statusText(Integer status) {
        return status != null && status == 1 ? "启用" : "停用";
    }
}
