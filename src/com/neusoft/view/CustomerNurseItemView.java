package com.neusoft.view;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.CustomerNurseItemController;
import com.neusoft.entity.CustomerNurseItem;

/**
 * 客户护理项目管理视图
 */
public class CustomerNurseItemView {
    private CustomerNurseItemController controller = new CustomerNurseItemController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 客户护理项目管理 -----");
            System.out.println("1. 查询全部客户护理项目");
            System.out.println("2. 按客户查询护理项目");
            System.out.println("3. 新增分配护理项目");
            System.out.println("4. 修改分配护理项目");
            System.out.println("5. 删除分配护理项目");
            System.out.println("6. 查询分配单条");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1": list(); break;
                case "2": getByCustomerId(scanner); break;
                case "3": add(scanner); break;
                case "4": update(scanner); break;
                case "5": delete(scanner); break;
                case "6": getOne(scanner); break;
                case "0": return;
                default: System.out.println("输入无效");
            }
        }
    }

    private void list() {
        System.out.println("----客户护理项目列表----");
        List<Map<String, Object>> list = controller.list();
        if (list.isEmpty()) {
            System.out.println("暂无客户护理项目数据");
            return;
        }
        for (Map<String, Object> row : list) {
            printRow(row);
        }
    }

    private void getByCustomerId(Scanner scanner) {
        System.out.println("----按客户查询护理项目----");
        try {
            System.out.print("请输入客户ID: ");
            Integer customerId = Integer.parseInt(scanner.nextLine().trim());
            List<Map<String, Object>> list = controller.getByCustomerId(customerId);
            if (list.isEmpty()) {
                System.out.println("该客户暂无分配的护理项目");
                return;
            }
            for (Map<String, Object> row : list) {
                printRow(row);
            }
        } catch (NumberFormatException e) {
            System.out.println("客户ID输入无效");
        }
    }

    private void add(Scanner scanner) {
        System.out.println("----新增分配护理项目----");
        try {
            CustomerNurseItem cni = readFields(scanner, null);
            boolean ok = controller.add(cni);
            System.out.println(ok ? "新增成功！" : "新增失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数字字段格式错误");
        } catch (IllegalArgumentException e) {
            System.out.println("日期格式错误，应为 yyyy-MM-dd");
        }
    }

    private void update(Scanner scanner) {
        System.out.println("----修改分配护理项目----");
        try {
            System.out.print("请输入分配ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            CustomerNurseItem cni = readFields(scanner, id);
            boolean ok = controller.update(cni);
            System.out.println(ok ? "修改成功！" : "修改失败：未找到该分配记录");
        } catch (NumberFormatException e) {
            System.out.println("输入无效：ID或数字字段格式错误");
        } catch (IllegalArgumentException e) {
            System.out.println("日期格式错误，应为 yyyy-MM-dd");
        }
    }

    private void delete(Scanner scanner) {
        System.out.println("----删除分配护理项目----");
        try {
            System.out.print("请输入分配ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            boolean ok = controller.delete(id);
            System.out.println(ok ? "删除成功！" : "删除失败：未找到该分配记录");
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void getOne(Scanner scanner) {
        System.out.println("----查询分配单条----");
        try {
            System.out.print("请输入分配ID: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            CustomerNurseItem cni = controller.getById(id);
            if (cni == null) {
                System.out.println("未找到该分配记录");
                return;
            }
            System.out.println("ID=" + cni.getId()
                    + " | 项目ID=" + cni.getItemId()
                    + " | 客户ID=" + cni.getCustomerId()
                    + " | 等级ID=" + cni.getLevelId()
                    + " | 护理次数=" + cni.getNurseNumber()
                    + " | 购买时间=" + cni.getBuyTime()
                    + " | 到期时间=" + cni.getMaturityTime());
        } catch (NumberFormatException e) {
            System.out.println("ID输入无效");
        }
    }

    private void printRow(Map<String, Object> row) {
        System.out.println("ID=" + row.get("id")
                + " | 客户=" + row.get("customerName") + "(id:" + row.get("customerId") + ")"
                + " | 项目=" + row.get("itemName") + "(id:" + row.get("itemId") + ")"
                + " | 等级=" + row.get("levelName") + "(id:" + row.get("levelId") + ")"
                + " | 次数=" + row.get("nurseNumber")
                + " | 购买=" + row.get("buyTime")
                + " | 到期=" + row.get("maturityTime"));
    }

    // 读取公共字段
    private CustomerNurseItem readFields(Scanner scanner, Long id) {
        CustomerNurseItem cni = new CustomerNurseItem();
        cni.setId(id);
        System.out.print("护理项目ID item_id: ");
        cni.setItemId(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("客户ID customer_id: ");
        cni.setCustomerId(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("护理等级ID level_id(可空): ");
        String levelStr = scanner.nextLine().trim();
        cni.setLevelId(levelStr.isEmpty() ? null : Integer.parseInt(levelStr));
        System.out.print("护理次数 nurse_number: ");
        cni.setNurseNumber(Integer.parseInt(scanner.nextLine().trim()));
        System.out.print("购买时间 buy_time yyyy-MM-dd: ");
        cni.setBuyTime(Date.valueOf(scanner.nextLine().trim()));
        System.out.print("到期时间 maturity_time yyyy-MM-dd: ");
        cni.setMaturityTime(Date.valueOf(scanner.nextLine().trim()));
        return cni;
    }
}
