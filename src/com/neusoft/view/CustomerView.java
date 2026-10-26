package com.neusoft.view;

import com.neusoft.controller.BackdownController;
import com.neusoft.controller.CustomerController;
import com.neusoft.controller.OutwardController;
import com.neusoft.entity.Backdown;
import com.neusoft.entity.Customer;
import com.neusoft.entity.Outward;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 * 客户管理视图
 */
public class CustomerView {
    private static Scanner sc = new Scanner(System.in);
    private static CustomerController customerController = new CustomerController();
    private static BackdownController backdownController = new BackdownController();
    private static OutwardController outwardController = new OutwardController();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void showView() {
        while (true) {
            System.out.println("\n======= 客户管理模块 =======");
            System.out.println("1. 入住登记（客户信息管理）");
            System.out.println("2. 退住登记管理");
            System.out.println("3. 外出登记管理");
            System.out.println("0. 返回主菜单");
            System.out.print("请输入功能编号：");
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    customerSubMenu();
                    break;
                case 2:
                    backdownSubMenu();
                    break;
                case 3:
                    outwardSubMenu();
                    break;
                case 0:
                    System.out.println("返回主菜单");
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    // ==================== 客户档案管理子菜单 ====================
    private void customerSubMenu() {
        while (true) {
            System.out.println("\n----- 入住登记（客户信息管理） -----");
            System.out.println("1. 查询客户列表");
            System.out.println("2. 新增客户");
            System.out.println("3. 修改客户信息");
            System.out.println("4. 删除客户（逻辑删除）");
            System.out.println("0. 返回上一级");
            System.out.print("请输入功能编号：");
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    findCustomerPageView();
                    break;
                case 2:
                    addCustomerView();
                    break;
                case 3:
                    updateCustomerView();
                    break;
                case 4:
                    deleteCustomerView();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    private void findCustomerPageView() {
        System.out.print("输入客户姓名（模糊查询，直接回车不筛选）：");
        String name = sc.nextLine();
        System.out.print("输入护理等级ID（直接回车不筛选）：");
        String levelStr = sc.nextLine();
        Integer levelId = null;
        if (!levelStr.isEmpty()) {
            levelId = Integer.parseInt(levelStr);
        }
        System.out.print("页码：");
        int pageIndex = sc.nextInt();
        System.out.print("每页条数：");
        int pageSize = sc.nextInt();
        sc.nextLine();
        List<Customer> list = customerController.findCustomerPage(name, levelId, pageIndex, pageSize);
        long total = customerController.getCustomerTotal(name, levelId);
        System.out.println("总记录数：" + total);
        for (Customer c : list) {
            System.out.println(c);
        }
    }

    private void addCustomerView() {
        Customer customer = new Customer();
        System.out.print("输入客户姓名：");
        customer.setCustomerName(sc.nextLine());
        System.out.print("输入年龄：");
        customer.setCustomerAge(sc.nextInt());
        System.out.print("输入性别（1男 2女）：");
        customer.setCustomerSex(sc.nextInt());
        sc.nextLine();
        System.out.print("输入身份证号：");
        customer.setIdcard(sc.nextLine());
        System.out.print("输入房间号：");
        customer.setRoomNo(sc.nextLine());
        System.out.print("输入楼号：");
        customer.setBuildingNo(sc.nextLine());
        System.out.print("输入入住时间(yyyy-MM-dd)：");
        try {
            java.util.Date utilDate = sdf.parse(sc.nextLine());
            customer.setCheckinDate(new java.sql.Date(utilDate.getTime()));
        } catch (ParseException e) {
            System.out.println("日期格式错误");
            return;
        }
        System.out.print("输入合同到期时间(yyyy-MM-dd)：");
        try {
            java.util.Date utilDate = sdf.parse(sc.nextLine());
            customer.setExpirationDate(new java.sql.Date(utilDate.getTime()));
        } catch (ParseException e) {
            System.out.println("日期格式错误");
            return;
        }
        System.out.print("输入联系电话：");
        customer.setContactTel(sc.nextLine());
        System.out.print("输入床位ID：");
        customer.setBedId(sc.nextInt());
        sc.nextLine();
        System.out.print("输入家属姓名：");
        customer.setFamilyMember(sc.nextLine());
        String msg = customerController.addCustomer(customer);
        System.out.println(msg);
    }

    private void updateCustomerView() {
        System.out.print("输入要修改的客户ID：");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.print("输入新姓名（直接回车不修改）：");
        String name = sc.nextLine();
        Customer customer = new Customer();
        customer.setId(id);
        if (!name.isEmpty()) {
            customer.setCustomerName(name);
        }
        System.out.print("输入新联系电话（直接回车不修改）：");
        String tel = sc.nextLine();
        if (!tel.isEmpty()) {
            customer.setContactTel(tel);
        }
        String msg = customerController.updateCustomer(customer);
        System.out.println(msg);
    }

    private void deleteCustomerView() {
        System.out.print("输入要删除的客户ID：");
        Long id = sc.nextLong();
        sc.nextLine();
        String msg = customerController.logicDeleteCustomer(id);
        System.out.println(msg);
    }

    // ==================== 退住登记子菜单 ====================
    private void backdownSubMenu() {
        while (true) {
            System.out.println("\n----- 退住登记管理 -----");
            System.out.println("1. 查询退住记录列表");
            System.out.println("2. 审核退住申请");
            System.out.println("3. 按客户ID查询退住记录");
            System.out.println("0. 返回上一级");
            System.out.print("请输入功能编号：");
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    findBackDownPageView();
                    break;
                case 2:
                    auditBackDownView();
                    break;
                case 3:
                    findBackDownByCidView();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    private void findBackDownPageView() {
        System.out.print("输入客户姓名（模糊查询，直接回车不筛选）：");
        String name = sc.nextLine();
        System.out.print("页码：");
        int pageIndex = sc.nextInt();
        System.out.print("每页条数：");
        int pageSize = sc.nextInt();
        sc.nextLine();
        List<Backdown> list = backdownController.findBackDownPage(name, pageIndex, pageSize);
        long total = backdownController.getBackDownTotal(name);
        System.out.println("总记录数：" + total);
        for (Backdown b : list) {
            System.out.println(b);
        }
    }

    private void auditBackDownView() {
        System.out.print("输入退住申请ID：");
        Long id = sc.nextLong();
        System.out.print("输入审核状态（1通过 2不通过）：");
        Integer status = sc.nextInt();
        sc.nextLine();
        System.out.print("输入审核人：");
        String auditor = sc.nextLine();
        boolean res = backdownController.auditBackDown(id, status, auditor);
        System.out.println(res ? "审核完成" : "审核失败");
    }

    private void findBackDownByCidView() {
        System.out.print("输入客户ID：");
        Long cid = sc.nextLong();
        sc.nextLine();
        List<Backdown> list = backdownController.findByCustomerId(cid);
        if (list.isEmpty()) {
            System.out.println("该客户暂无退住记录");
            return;
        }
        for (Backdown b : list) {
            System.out.println(b);
        }
    }

    // ==================== 外出登记子菜单 ====================
    private void outwardSubMenu() {
        while (true) {
            System.out.println("\n----- 外出登记管理 -----");
            System.out.println("1. 查询外出记录列表");
            System.out.println("2. 审核外出申请");
            System.out.println("3. 按客户ID查询外出记录");
            System.out.println("4. 登记实际回院时间");
            System.out.println("0. 返回上一级");
            System.out.print("请输入功能编号：");
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    findOutwardPageView();
                    break;
                case 2:
                    auditOutwardView();
                    break;
                case 3:
                    findOutwardByCidView();
                    break;
                case 4:
                    updateReturnTimeView();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
            }
        }
    }

    private void findOutwardPageView() {
        System.out.print("输入客户姓名（模糊查询，直接回车不筛选）：");
        String name = sc.nextLine();
        System.out.print("页码：");
        int pageIndex = sc.nextInt();
        System.out.print("每页条数：");
        int pageSize = sc.nextInt();
        sc.nextLine();
        List<Outward> list = outwardController.findOutwardPage(name, pageIndex, pageSize);
        long total = outwardController.getOutwardTotal(name);
        System.out.println("总记录数：" + total);
        for (Outward o : list) {
            System.out.println(o);
        }
    }

    private void auditOutwardView() {
        System.out.print("输入外出申请ID：");
        Long id = sc.nextLong();
        System.out.print("输入审核状态（1通过 2不通过）：");
        Integer status = sc.nextInt();
        sc.nextLine();
        System.out.print("输入审核人：");
        String auditor = sc.nextLine();
        boolean res = outwardController.auditOutward(id, status, auditor);
        System.out.println(res ? "审核完成" : "审核失败");
    }

    private void findOutwardByCidView() {
        System.out.print("输入客户ID：");
        Long cid = sc.nextLong();
        sc.nextLine();
        List<Outward> list = outwardController.findByCustomerId(cid);
        for (Outward o : list) {
            System.out.println(o);
        }
    }

    private void updateReturnTimeView() {
        System.out.print("输入外出申请ID：");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.print("输入实际回院时间(yyyy-MM-dd)：");
        try {
            java.util.Date utilDate = sdf.parse(sc.nextLine());
            boolean res = outwardController.updateReturnTime(id, new java.sql.Date(utilDate.getTime()));
            System.out.println(res ? "回院时间登记成功" : "登记失败");
        } catch (ParseException e) {
            System.out.println("日期格式错误");
        }
    }
}
