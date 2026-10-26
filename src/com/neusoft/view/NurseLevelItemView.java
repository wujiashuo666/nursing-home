package com.neusoft.view;

import java.util.List;
import java.util.Scanner;

import com.neusoft.controller.NurseLevelItemController;
import com.neusoft.entity.NurseLevelItem;

/**
 * 护理等级项目(护理等级-护理内容关联)管理视图
 */
public class NurseLevelItemView {
    private NurseLevelItemController controller = new NurseLevelItemController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 护理等级项目管理 -----");
            System.out.println("1. 查询全部关联");
            System.out.println("2. 新增关联");
            System.out.println("3. 删除关联");
            System.out.println("4. 查询单条关联");
            System.out.println("5. 按等级ID查询关联项目");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": list(); break;
                case "2": add(scanner); break;
                case "3": delete(scanner); break;
                case "4": getOne(scanner); break;
                case "5": getItemIds(scanner); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }

    private void list() {
        System.out.println("----护理等级项目列表----");
        List<NurseLevelItem> list = controller.list();
        if (list.isEmpty()) {
            System.out.println("暂无关联数据");
            return;
        }
        for (NurseLevelItem item : list) {
            System.out.println("ID=" + item.getId()
                    + " | 等级ID=" + item.getLevelId()
                    + " | 项目ID=" + item.getItemId());
        }
    }

    private void add(Scanner scanner) {
        System.out.println("----新增关联----");
        try {
            System.out.print("请输入护理等级ID level_id: ");
            Integer levelId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("请输入护理项目ID item_id: ");
            Integer itemId = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = controller.add(levelId, itemId);
            System.out.println(ok ? "新增成功！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID格式错误");
        }
    }

    private void delete(Scanner scanner) {
        System.out.println("----删除关联----");
        try {
            System.out.print("请输入关联ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            boolean ok = controller.delete(id);
            System.out.println(ok ? "删除成功！" : "删除失败：未找到该关联");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void getOne(Scanner scanner) {
        System.out.println("----查询单条关联----");
        try {
            System.out.print("请输入关联ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseLevelItem item = controller.getById(id);
            if (item == null) {
                System.out.println("未找到该关联");
                return;
            }
            System.out.println("ID=" + item.getId()
                    + " | 等级ID=" + item.getLevelId()
                    + " | 项目ID=" + item.getItemId());
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void getItemIds(Scanner scanner) {
        System.out.println("----按等级ID查询关联项目----");
        try {
            System.out.print("请输入护理等级ID: ");
            Integer levelId = Integer.parseInt(scanner.nextLine().trim());
            List<Integer> itemIds = controller.getItemIdsByLevelId(levelId);
            if (itemIds.isEmpty()) {
                System.out.println("该等级暂未配置护理项目");
                return;
            }
            System.out.print("等级ID " + levelId + " 关联的护理项目ID：");
            for (Integer itemId : itemIds) {
                System.out.print(itemId + "  ");
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }
}
