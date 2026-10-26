package com.neusoft.view;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.NurseRecordController;
import com.neusoft.entity.NurseRecord;

/**
 * 护理记录管理视图
 */
public class NurseRecordView {
    private NurseRecordController controller = new NurseRecordController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 护理记录管理 -----");
            System.out.println("1. 查询护理记录列表");
            System.out.println("2. 新增护理记录");
            System.out.println("3. 修改护理记录");
            System.out.println("4. 删除护理记录");
            System.out.println("5. 查询护理记录单条");
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
        System.out.println("----护理记录列表----");
        List<Map<String, Object>> list = controller.list();
        if (list.isEmpty()) {
            System.out.println("暂无护理记录数据");
            return;
        }
        for (Map<String, Object> row : list) {
            System.out.println("ID=" + row.get("id")
                    + " | 客户=" + row.get("customerName") + "(id:" + row.get("customerId") + ")"
                    + " | 项目=" + row.get("itemName") + "(id:" + row.get("itemId") + ")"
                    + " | 时间=" + row.get("nursingTime")
                    + " | 次数=" + row.get("nursingCount")
                    + " | 护理人=" + row.get("operator"));
            if (row.get("nursingContent") != null && !row.get("nursingContent").toString().isEmpty()) {
                System.out.println("     内容=" + row.get("nursingContent"));
            }
        }
    }

    private void add(Scanner scanner) {
        System.out.println("----新增护理记录----");
        try {
            NurseRecord nr = readFields(scanner, null);
            boolean ok = controller.add(nr);
            System.out.println(ok ? "新增成功！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数字字段格式错误");
        }
    }

    private void update(Scanner scanner) {
        System.out.println("----修改护理记录----");
        try {
            System.out.print("请输入护理记录ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseRecord nr = readFields(scanner, id);
            boolean ok = controller.update(nr);
            System.out.println(ok ? "修改成功！" : "修改失败：未找到该护理记录");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数字字段格式错误");
        }
    }

    private void delete(Scanner scanner) {
        System.out.println("----删除护理记录----");
        try {
            System.out.print("请输入护理记录ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            boolean ok = controller.delete(id);
            System.out.println(ok ? "删除成功！" : "删除失败：未找到该护理记录");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void getOne(Scanner scanner) {
        System.out.println("----查询护理记录单条----");
        try {
            System.out.print("请输入护理记录ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            NurseRecord nr = controller.getById(id);
            if (nr == null) {
                System.out.println("未找到该护理记录");
                return;
            }
            System.out.println("ID=" + nr.getId());
            System.out.println("客户ID=" + nr.getCustomerId());
            System.out.println("项目ID=" + nr.getItemId());
            System.out.println("护理时间=" + nr.getNursingTime());
            System.out.println("护理内容=" + nr.getNursingContent());
            System.out.println("护理次数=" + nr.getNursingCount());
            System.out.println("护理人(user_id)=" + nr.getUserId());
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    // 读取公共字段；护理时间为空时默认当前时间
    private NurseRecord readFields(Scanner scanner, Long id) {
        NurseRecord nr = new NurseRecord();
        nr.setId(id);
        System.out.print("客户ID customer_id: ");
        nr.setCustomerId(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("护理项目ID item_id: ");
        nr.setItemId(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("护理时间 yyyy-MM-dd HH:mm:ss(可空,默认当前时间): ");
        String time = scanner.nextLine().trim();
        if (time.isEmpty()) {
            nr.setNursingTime(new Timestamp(System.currentTimeMillis()));
        } else {
            nr.setNursingTime(Timestamp.valueOf(time));
        }
        System.out.print("护理内容 nursing_content(可空): ");
        String content = scanner.nextLine();
        nr.setNursingContent((content == null || content.trim().isEmpty()) ? null : content.trim());
        System.out.print("护理次数 nursing_count: ");
        nr.setNursingCount(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("护理人 user_id: ");
        nr.setUserId(Integer.parseInt(scanner.nextLine().trim()));
        return nr;
    }
}
