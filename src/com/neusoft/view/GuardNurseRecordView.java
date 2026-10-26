package com.neusoft.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.CustomerController;
import com.neusoft.controller.NurseRecordController;
import com.neusoft.entity.Customer;

/**
 * 服务对象护理记录视图(健康管家侧)
 */
public class GuardNurseRecordView {
    private int guardId;
    private CustomerController customerController = new CustomerController();
    private NurseRecordController nurseRecordController = new NurseRecordController();

    public GuardNurseRecordView(int guardId) {
        this.guardId = guardId;
    }

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 服务对象护理记录 -----");
            System.out.println("1. 查询我的服务客户列表");
            System.out.println("2. 查询客户护理记录");
            System.out.println("3. 移除护理记录");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": listCustomers(scanner); break;
                case "2": listRecords(scanner); break;
                case "3": remove(scanner); break;
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
            System.out.println("ID=" + c.getId() + " | 姓名=" + c.getCustomerName()
                    + " | 房间=" + c.getRoomNo() + " | 床位号(id)=" + c.getBedId());
        }
    }

    private void listRecords(Scanner scanner) {
        System.out.println("----查询客户护理记录----");
        try {
            System.out.print("请输入客户ID: ");
            Integer customerId = Integer.parseInt(scanner.nextLine().trim());
            List<Map<String, Object>> list = nurseRecordController.listByCustomer(customerId);
            if (list.isEmpty()) {
                System.out.println("该客户暂无护理记录");
                return;
            }
            for (Map<String, Object> row : list) {
                System.out.println("ID=" + row.get("id")
                        + " | 客户=" + row.get("customerName")
                        + " | 项目=" + row.get("itemName")
                        + " | 时间=" + row.get("nursingTime")
                        + " | 次数=" + row.get("nursingCount")
                        + " | 护理人=" + row.get("operator"));
                if (row.get("nursingContent") != null && !row.get("nursingContent").toString().isEmpty()) {
                    System.out.println("     内容=" + row.get("nursingContent"));
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("客户ID输入无效");
        }
    }

    private void remove(Scanner scanner) {
        System.out.println("----移除护理记录----");
        try {
            System.out.print("请输入护理记录ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            boolean ok = nurseRecordController.delete(id);
            System.out.println(ok ? "移除成功！" : "移除失败：未找到该护理记录");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }
}
