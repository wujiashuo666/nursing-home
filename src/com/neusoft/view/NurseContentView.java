package com.neusoft.view;

import java.util.List;
import java.util.Scanner;

import com.neusoft.controller.NurseContentController;
import com.neusoft.entity.NurseContent;

/**
 * 护理内容(护理项目)管理视图
 */
public class NurseContentView {
    private NurseContentController controller = new NurseContentController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 护理内容管理 -----");
            System.out.println("1. 查询护理内容列表");
            System.out.println("2. 新增护理内容");
            System.out.println("3. 修改护理内容");
            System.out.println("4. 删除护理内容");
            System.out.println("5. 查询护理内容详情");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": list(scanner); break;
                case "2": add(scanner); break;
                case "3": update(scanner); break;
                case "4": delete(scanner); break;
                case "5": getById(scanner); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }

    private void list(Scanner scanner) {
        System.out.println("----查询护理内容列表----");
        System.out.print("状态 1启用 0停用(可空): ");
        String statusStr = scanner.nextLine();
        Integer status = null;
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                status = Integer.parseInt(statusStr.trim());
            } catch (NumberFormatException e) {
                status = null;
            }
        }
        System.out.print("护理名称(可空，模糊): ");
        String name = scanner.nextLine();
        name = (name == null || name.trim().isEmpty()) ? null : name.trim();

        List<NurseContent> list = controller.list(status, name);
        if (list.isEmpty()) {
            System.out.println("暂无护理内容数据");
            return;
        }
        for (NurseContent nc : list) {
            System.out.println("ID=" + nc.getId()
                    + " | 编号=" + nc.getSerialNumber()
                    + " | 名称=" + nc.getNursingName()
                    + " | 价格=" + nc.getServicePrice()
                    + " | 状态=" + statusText(nc.getStatus())
                    + " | 周期=" + nc.getExecutionCycle()
                    + " | 次数=" + nc.getExecutionTimes());
            if (nc.getMessage() != null && !nc.getMessage().isEmpty()) {
                System.out.println("     说明=" + nc.getMessage());
            }
        }
    }

    private void add(Scanner scanner) {
        System.out.println("----新增护理内容----");
        try {
            NurseContent nc = readFields(scanner, null);
            boolean ok = controller.add(nc);
            System.out.println(ok ? "新增成功！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：状态/编号等字段格式错误");
        }
    }

    private void update(Scanner scanner) {
        System.out.println("----修改护理内容----");
        try {
            System.out.print("请输入护理内容ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseContent nc = readFields(scanner, id);
            boolean ok = controller.update(nc);
            System.out.println(ok ? "修改成功！" : "修改失败：未找到该护理内容");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或字段格式错误");
        }
    }

    private void delete(Scanner scanner) {
        System.out.println("----删除护理内容----");
        try {
            System.out.print("请输入护理内容ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            boolean ok = controller.delete(id);
            System.out.println(ok ? "删除成功！" : "删除失败：未找到该护理内容");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void getById(Scanner scanner) {
        System.out.println("----查询护理内容详情----");
        try {
            System.out.print("请输入护理内容ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseContent nc = controller.getById(id);
            if (nc == null) {
                System.out.println("未找到该护理内容");
                return;
            }
            System.out.println("ID=" + nc.getId());
            System.out.println("编号=" + nc.getSerialNumber());
            System.out.println("名称=" + nc.getNursingName());
            System.out.println("价格=" + nc.getServicePrice());
            System.out.println("说明=" + nc.getMessage());
            System.out.println("状态=" + statusText(nc.getStatus()));
            System.out.println("执行周期=" + nc.getExecutionCycle());
            System.out.println("执行次数=" + nc.getExecutionTimes());
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    // 读取除ID外的公共字段
    private NurseContent readFields(Scanner scanner, Long id) {
        NurseContent nc = new NurseContent();
        nc.setId(id);
        System.out.print("编号 serial_number: ");
        nc.setSerialNumber(scanner.nextLine().trim());
        System.out.print("护理名称 nursing_name: ");
        nc.setNursingName(scanner.nextLine().trim());
        System.out.print("服务价格 service_price: ");
        nc.setServicePrice(scanner.nextLine().trim());
        System.out.print("说明 message(可空): ");
        String msg = scanner.nextLine();
        nc.setMessage((msg == null || msg.isEmpty()) ? null : msg.trim());
        System.out.print("状态 1启用 0停用: ");
        nc.setStatus(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("执行周期 execution_cycle(可空): ");
        String cycle = scanner.nextLine();
        nc.setExecutionCycle((cycle == null || cycle.isEmpty()) ? null : cycle.trim());
        System.out.print("执行次数 execution_times(可空): ");
        String times = scanner.nextLine();
        nc.setExecutionTimes((times == null || times.isEmpty()) ? null : times.trim());
        return nc;
    }

    private String statusText(Integer status) {
        return status != null && status == 1 ? "启用" : "停用";
    }
}
