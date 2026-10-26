package com.neusoft.service.Impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.CustomerNurseItemDao;
import com.neusoft.dao.NurseRecordDao;
import com.neusoft.dao.impl.CustomerNurseItemDaoImpl;
import com.neusoft.dao.impl.NurseRecordDaoImpl;
import com.neusoft.entity.CustomerNurseItem;
import com.neusoft.entity.NurseRecord;
import com.neusoft.service.IDailyCareService;

public class IDailyCareServiceImpl implements IDailyCareService {

    private CustomerNurseItemDao customerNurseItemDao = new CustomerNurseItemDaoImpl();
    private NurseRecordDao nurseRecordDao = new NurseRecordDaoImpl();

    @Override
    public List<Map<String, Object>> listItemsByCustomer(Integer customerId) {
        List<Map<String, Object>> rows = customerNurseItemDao.getByCustomerId(customerId);
        Date today = new Date(System.currentTimeMillis());
        for (Map<String, Object> row : rows) {
            Integer nurseNumber = row.get("nurseNumber") == null ? 0 : (Integer) row.get("nurseNumber");
            String maturity = row.get("maturityTime") == null ? null : row.get("maturityTime").toString();
            String status;
            if (nurseNumber <= 0) {
                status = "次数已用完";
            } else if (maturity != null && !maturity.isEmpty()) {
                try {
                    Date maturityDate = Date.valueOf(maturity);
                    status = maturityDate.before(today) ? "已到期" : "未到期";
                } catch (Exception e) {
                    status = "未知";
                }
            } else {
                status = "未到期";
            }
            row.put("status", status);
        }
        return rows;
    }

    @Override
    public boolean performNursing(Integer customerId, Long customerNurseItemId,
                                  String nursingContent, int nursingCount, int guardId) {
        CustomerNurseItem cni = customerNurseItemId == null ? null : customerNurseItemDao.getById(customerNurseItemId);
        if (cni == null) {
            return false;
        }
        NurseRecord nr = new NurseRecord();
        nr.setIsDeleted(0);
        nr.setCustomerId(customerId);
        nr.setItemId(cni.getItemId());
        nr.setNursingTime(new Timestamp(System.currentTimeMillis()));
        nr.setNursingContent((nursingContent == null || nursingContent.trim().isEmpty()) ? null : nursingContent.trim());
        nr.setNursingCount(nursingCount);
        nr.setUserId(guardId);
        boolean ok = nurseRecordDao.add(nr);
        if (ok) {
            customerNurseItemDao.incrementCount(customerNurseItemId, nursingCount);
        }
        return ok;
    }
}
