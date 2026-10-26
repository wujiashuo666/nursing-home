package com.neusoft.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.BedDetailsController;

/**
 * 床位管理视图
 */
public class BedDetailsView {
    private BedDetailsController controller = new BedDetailsController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 床位管理 -----");
            System.out.println("1. 查询床位使用详情");
            System.out.println("2. 修改床位结束时间");
            System.out.println("3. 床位调换");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    findUse(scanner);
                    break;
                case "2":
                    updateEndDate(scanner);
                    break;
                case "3":
                    transferBed(scanner);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("输入无效");
            }
        }
    }

    // 查询床位使用详情(默认正在使用)
    private void findUse(Scanner scanner) {
        System.out.println("----查询床位使用详情----");
        System.out.print("客户姓名(可空): ");
        String customerName = scanner.nextLine();
        System.out.print("入住日期 yyyy-MM-dd(可空): ");
        String startDate = scanner.nextLine();
        System.out.print("使用状态 1正在使用 2历史(可空,默认正在使用): ");
        String statusStr = scanner.nextLine();
        int status = 0;
        if (statusStr != null && !statusStr.isEmpty()) {
            try {
                status = Integer.parseInt(statusStr);
            } catch (NumberFormatException e) {
                status = 0;
            }
        }
        List<Map<String, Object>> list = controller.findUse(
                (customerName == null || customerName.isEmpty()) ? null : customerName,
                (startDate == null || startDate.isEmpty()) ? null : startDate,
                status);
        if (list.isEmpty()) {
            System.out.println("暂无匹配的床位使用详情");
            return;
        }
        for (Map<String, Object> row : list) {
            String state = Integer.valueOf(1).equals(row.get("status")) ? "正在使用" : "使用历史";
            System.out.println("客户ID: " + row.get("customerId")
                    + " | 客户: " + row.get("customerName")
                    + " | 房间: " + row.get("roomNo")
                    + " | 床号: " + row.get("bedNo")
                    + " | 楼: " + row.get("roomFloor")
                    + " | 入住: " + row.get("startDate")
                    + " | 结束: " + row.get("endDate")
                    + " | 状态: " + state);
        }
    }

    // 修改床位结束时间
    private void updateEndDate(Scanner scanner) {
        System.out.println("----修改床位结束时间----");
        System.out.print("请输入客户ID(如260324): ");
        String idStr = scanner.nextLine();
        System.out.print("请输入新的结束时间 yyyy-MM-dd(为空表示结束该次使用): ");
        String endDate = scanner.nextLine();
        try {
            int customerId = Integer.parseInt(idStr.trim());
            boolean ok = controller.updateEndDate(
                    customerId, (endDate == null || endDate.isEmpty()) ? null : endDate.trim());
            System.out.println(ok ? "修改成功！" : "修改失败：未找到该客户正在使用的床位使用详情");
        } catch (NumberFormatException e) {
            System.out.println("客户ID输入无效");
        }
    }

    // 床位调换
    private void transferBed(Scanner scanner) {
        System.out.println("----床位调换----");
        try {
            System.out.println("正在使用床位的客户:");
            List<Map<String, Object>> active = controller.findUse(null, null, 1);
            if (active.isEmpty()) {
                System.out.println("当前没有正在使用床位的客户，无法调换");
                return;
            }
            for (Map<String, Object> row : active) {
                System.out.println("  客户ID=" + row.get("customerId")
                        + " 客户=" + row.get("customerName")
                        + " 床号=" + row.get("bedNo")
                        + " 房间=" + row.get("roomNo"));
            }
            System.out.print("请输入客户ID(上面列表中的): ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("请输入新房间号: ");
            int newRoomNo = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("该房间空闲床位:");
            List<Map<String, Object>> freeBeds = controller.findFreeBedsByRoom(newRoomNo);
            if (freeBeds.isEmpty()) {
                System.out.println("该房间没有空闲床位，无法调换");
                return;
            }
            for (Map<String, Object> b : freeBeds) {
                System.out.println("  床号=" + b.get("bedNo") + " (ID=" + b.get("bedId") + ")");
            }
            System.out.print("请输入要调换到的新床位号(如A02): ");
            String newBedNo = scanner.nextLine().trim();
            int newBedId = -1;
            for (Map<String, Object> b : freeBeds) {
                if (newBedNo.equalsIgnoreCase(String.valueOf(b.get("bedNo")))) {
                    newBedId = ((Number) b.get("bedId")).intValue();
                    break;
                }
            }
            if (newBedId == -1) {
                System.out.println("未找到该床号，调换取消");
                return;
            }
            boolean ok = controller.transferBed(customerId, newRoomNo, newBedId);
            System.out.println(ok ? "床位调换成功！" : "床位调换失败！");
        } catch (NumberFormatException e) {
            System.out.println("输入无效");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
