package com.neusoft.view;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.CustomerController;
import com.neusoft.controller.OutwardController;
import com.neusoft.entity.Customer;
import com.neusoft.entity.Outward;

/**
 * 外出申请视图(健康管家侧)
 */
public class OutwardApplyView {
    private int guardId;
    private CustomerController customerController = new CustomerController();
    private OutwardController outwardController = new OutwardController();

    public OutwardApplyView(int guardId) {
        this.guardId = guardId;
    }

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 外出申请 -----");
            System.out.println("1. 查询我的服务客户列表");
            System.out.println("2. 添加外出申请");
            System.out.println("3. 查询外出申请列表");
            System.out.println("4. 登记回院时间");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": listCustomers(scanner); break;
                case "2": addApply(scanner); break;
                case "3": listApplies(scanner); break;
                case "4": registerReturn(scanner); break;
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
        System.out.println("----添加外出申请----");
        try {
            Outward o = new Outward();
            System.out.print("客户ID: ");
            o.setCustomerId(Integer.parseInt(scanner.nextLine().trim()));
            System.out.print("外出事由: ");
            o.setOutgoingReason(scanner.nextLine().trim());
            System.out.print("外出时间 yyyy-MM-dd: ");
            o.setOutgoingTime(Date.valueOf(scanner.nextLine().trim()));
            System.out.print("预计回院时间 yyyy-MM-dd: ");
            o.setExpectedReturnTime(Date.valueOf(scanner.nextLine().trim()));
            System.out.print("陪同人(可空): ");
            String escorted = scanner.nextLine().trim();
            o.setEscorted(escorted.isEmpty() ? null : escorted);
            System.out.print("与老人关系(可空): ");
            String relation = scanner.nextLine().trim();
            o.setRelation(relation.isEmpty() ? null : relation);
            System.out.print("陪同人电话(可空): ");
            String tel = scanner.nextLine().trim();
            o.setEscortedTel(tel.isEmpty() ? null : tel);
            o.setRemarks(null);
            o.setActualReturnTime(null);
            boolean ok = outwardController.add(o);
            System.out.println(ok ? "外出申请已提交(审批状态：已提交)！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数字格式错误");
        } catch (IllegalArgumentException e) {
            System.out.println("日期格式错误，应为 yyyy-MM-dd");
        }
    }

    private void listApplies(Scanner scanner) {
        System.out.println("----外出申请列表----");
        System.out.print("客户姓名(可空，直接回车查全部): ");
        String name = scanner.nextLine().trim();
        List<Map<String, Object>> list = outwardController.listByGuard(guardId, name);
        if (list.isEmpty()) {
            System.out.println("暂无外出申请");
            return;
        }
        for (Map<String, Object> row : list) {
            System.out.println("ID=" + row.get("id")
                    + " | 客户=" + row.get("customerName") + "(id:" + row.get("customerId") + ")"
                    + " | 事由=" + row.get("outgoingReason")
                    + " | 外出时间=" + row.get("outgoingTime")
                    + " | 预计回院=" + row.get("expectedReturnTime")
                    + " | 实际回院=" + (row.get("actualReturnTime") == null ? "未登记" : row.get("actualReturnTime"))
                    + " | 审批=" + statusText((Integer) row.get("auditStatus")));
        }
    }

    private void registerReturn(Scanner scanner) {
        System.out.println("----登记回院时间----");
        try {
            System.out.print("请输入外出申请ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            System.out.print("实际回院时间 yyyy-MM-dd: ");
            Date actual = Date.valueOf(scanner.nextLine().trim());
            boolean ok = outwardController.updateReturnTime(id, actual);
            System.out.println(ok ? "回院时间登记成功！" : "登记失败：未找到该外出申请");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        } catch (IllegalArgumentException e) {
            System.out.println("日期格式错误，应为 yyyy-MM-dd");
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
