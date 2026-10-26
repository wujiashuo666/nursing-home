package com.neusoft.view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.neusoft.controller.BedController;

/**
 * 床位示意图视图
 */
public class BedView {
    private BedController controller = new BedController();

    public void showView() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- 床位示意图 -----");
            System.out.println("1. 统计总床位/空闲/有人/外出");
            System.out.println("2. 按楼层查询房间及床位");
            System.out.println("0. 返回上级");
            System.out.print("请选择：");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    statistics();
                    break;
                case "2":
                    listByFloor(scanner);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("输入无效");
            }
        }
    }

    private void statistics() {
        Map<String, Object> result = controller.statistics();
        System.out.println("总床位数: " + result.get("total"));
        System.out.println("空闲    : " + result.get("free"));
        System.out.println("有人    : " + result.get("occupied"));
        System.out.println("外出    : " + result.get("outward"));
    }

    private void listByFloor(Scanner scanner) {
        System.out.print("请输入楼层(默认1): ");
        String floor = scanner.nextLine();
        if (floor == null || floor.isEmpty()) {
            floor = "1";
        }
        List<Map<String, Object>> rooms = controller.listByFloor(floor);
        if (rooms.isEmpty()) {
            System.out.println("该楼层暂无房间/床位数据");
            return;
        }
        for (Map<String, Object> room : rooms) {
            System.out.println("房间 " + room.get("roomNo") + " (楼 " + room.get("roomFloor") + "):");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> beds = (List<Map<String, Object>>) room.get("beds");
            if (beds == null || beds.isEmpty()) {
                System.out.println("   (无床位)");
                continue;
            }
            for (Map<String, Object> bed : beds) {
                String statusText = statusText((Integer) bed.get("bedStatus"));
                System.out.println("   床号 " + bed.get("bedNo") + " 状态 " + statusText);
            }
        }
    }

    private String statusText(int status) {
        switch (status) {
            case 1: return "空闲";
            case 2: return "有人";
            case 3: return "外出";
            default: return String.valueOf(status);
        }
    }
}
