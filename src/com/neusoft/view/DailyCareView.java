package com.neusoft.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.CustomerController;
import com.neusoft.controller.DailyCareController;
import com.neusoft.entity.Customer;

/**
 * 日常护理视图(健康管家侧)
 */
public class DailyCareView {
    private int guardId;
    private CustomerController customerController = new CustomerController();
    private DailyCareController dailyCareController = new DailyCareController();

    public DailyCareView(int guardId) {
        this.guardId = guardId;
    }

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 日常护理 -----");
            System.out.println("1. 查询我的服务客户列表");
            System.out.println("2. 查看客户护理项目");
            System.out.println("3. 执行护理(添加护理记录)");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": listCustomers(scanner); break;
                case "2": listItems(scanner); break;
                case "3": perform(scanner); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }

    private void listCustomers(Scanner scanner) {
        System.out.println("----我的服务客户列表----");
        System.out.print("客户姓名(可空，直接回车查全部): ");
        String name = scanner.nextLine().trim();
        List<Customer> list = customerController.listServedByGuard(guardId, name);
        if (list.isEmpty()) {
            System.out.println("暂无服务客户");
            return;
        }
        for (Customer c : list) {
            System.out.println("ID=" + c.getId()
                    + " | 姓名=" + c.getCustomerName()
                    + " | 性别=" + (c.getCustomerSex() == 1 ? "男" : "女")
                    + " | 年龄=" + c.getCustomerAge()
                    + " | 房间=" + c.getRoomNo()
                    + " | 床位号(id)=" + c.getBedId());
        }
    }

    private void listItems(Scanner scanner) {
        System.out.println("----查看客户护理项目----");
        try {
            System.out.print("请输入客户ID: ");
            Integer customerId = Integer.parseInt(scanner.nextLine().trim());
            List<Map<String, Object>> list = dailyCareController.listItemsByCustomer(customerId);
            if (list.isEmpty()) {
                System.out.println("该客户暂无护理项目");
                return;
            }
            for (Map<String, Object> row : list) {
                System.out.println("分配ID=" + row.get("id")
                        + " | 项目=" + row.get("itemName") + "(item_id:" + row.get("itemId") + ")"
                        + " | 护理次数=" + row.get("nurseNumber")
                        + " | 到期=" + row.get("maturityTime")
                        + " | 状态=" + row.get("status"));
            }
        } catch (NumberFormatException e) {
            System.out.println("客户ID输入无效");
        }
    }

    private void perform(Scanner scanner) {
        System.out.println("----执行护理----");
        try {
            System.out.print("请输入客户ID: ");
            Integer customerId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("请输入该客户的护理项目分配ID(customernurseitem.id): ");
            Long cniId = Long.parseLong(scanner.nextLine().trim());
            System.out.print("护理内容(可空): ");
            String content = scanner.nextLine();
            System.out.print("本次护理数量: ");
            int count = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = dailyCareController.performNursing(customerId, cniId, content, count, guardId);
            System.out.println(ok ? "护理记录已生成，并更新客户护理数量！" : "护理失败：未找到该护理项目分配记录");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数量格式错误");
        }
    }
}
