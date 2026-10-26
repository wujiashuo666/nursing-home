package com.neusoft.view;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.BackdownController;
import com.neusoft.controller.CustomerController;
import com.neusoft.entity.Backdown;
import com.neusoft.entity.Customer;

/**
 * 退住申请视图(健康管家侧)
 */
public class BackdownApplyView {
    private int guardId;
    private CustomerController customerController = new CustomerController();
    private BackdownController backdownController = new BackdownController();

    public BackdownApplyView(int guardId) {
        this.guardId = guardId;
    }

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 退住申请 -----");
            System.out.println("1. 查询我的服务客户列表");
            System.out.println("2. 添加退住申请");
            System.out.println("3. 查询退住申请列表");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": listCustomers(scanner); break;
                case "2": addApply(scanner); break;
                case "3": listApplies(scanner); break;
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

    private void addApply(Scanner scanner) {
        System.out.println("----添加退住申请----");
        try {
            Backdown b = new Backdown();
            System.out.print("客户ID: ");
            b.setCustomerId(Integer.parseInt(scanner.nextLine().trim()));
            System.out.print("退住类型(1正常退住 2死亡退住 3保留床位): ");
            b.setRetreatType(Integer.parseInt(scanner.nextLine().trim()));
            System.out.print("退住原因: ");
            b.setRetreatReason(scanner.nextLine().trim());
            System.out.print("退住时间 yyyy-MM-dd: ");
            b.setRetreatTime(Date.valueOf(scanner.nextLine().trim()));
            b.setRemarks(null);
            boolean ok = backdownController.add(b);
            System.out.println(ok ? "退住申请已提交(审批状态：已提交)！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数字格式错误");
        } catch (IllegalArgumentException e) {
            System.out.println("日期格式错误，应为 yyyy-MM-dd");
        }
    }

    private void listApplies(Scanner scanner) {
        System.out.println("----退住申请列表----");
        System.out.print("客户姓名(可空，直接回车查全部): ");
        String name = scanner.nextLine().trim();
        List<Map<String, Object>> list = backdownController.listByGuard(guardId, name);
        if (list.isEmpty()) {
            System.out.println("暂无退住申请");
            return;
        }
        for (Map<String, Object> row : list) {
            System.out.println("ID=" + row.get("id")
                    + " | 客户=" + row.get("customerName") + "(id:" + row.get("customerId") + ")"
                    + " | 退住类型=" + retreatTypeText((Integer) row.get("retreatType"))
                    + " | 原因=" + row.get("retreatReason")
                    + " | 退住时间=" + row.get("retreatTime")
                    + " | 审批=" + statusText((Integer) row.get("auditStatus")));
        }
    }

    private String retreatTypeText(Integer type) {
        if (type == null) return "未知";
        switch (type) {
            case 1: return "正常退住";
            case 2: return "死亡退住";
            case 3: return "保留床位";
            default: return "未知";
        }
    }

    private String statusText(Integer status) {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "已提交";
            case 1: return "通过";
            case 2: return "不通过";
            default: return "未知";
        }
    }
}
