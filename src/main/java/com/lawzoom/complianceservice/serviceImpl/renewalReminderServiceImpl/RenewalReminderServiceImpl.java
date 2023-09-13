package com.lawzoom.complianceservice.serviceImpl.renewalReminderServiceImpl;

import com.lawzoom.complianceservice.dao.renewalReminderDao.RenewalReminderDao;
import com.lawzoom.complianceservice.model.renewalModel.RenewalReminder;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.renewalReminderService.RenewalReminderService;
import com.lawzoom.complianceservice.utility.CommonUtil;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RenewalReminderServiceImpl implements RenewalReminderService {

    @Autowired
    private RenewalReminderDao renewalReminderDao;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity saveRenewalReminder(RenewalReminder renewalReminder) {
        if(renewalReminder.getComplianceTask()==null
                &&renewalReminder.getComplianceSubTask()==null)
            return new ResponseEntity().badRequest("Please select compliance Task/Sub-Task !!");

        RenewalReminder findRenewalReminder=this.renewalReminderDao.findRenewalReminderByComplianceTaskOrSubTask
                (renewalReminder.getComplianceTask(),renewalReminder.getComplianceSubTask());

        if(findRenewalReminder!=null)
            return new ResponseEntity().badRequest("Renewal Reminder already exist !!");
        renewalReminder.setCreatedAt(CommonUtil.getDate());
        renewalReminder.setUpdatedAt(CommonUtil.getDate());
        renewalReminder.setEnable(true);
        RenewalReminder saveRenewalReminder=this.renewalReminderDao.saveRenewalReminder(renewalReminder);
        if(saveRenewalReminder==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateRenewalReminder(RenewalReminder renewalReminder) {
        if(renewalReminder.getComplianceTask()==null
                &&renewalReminder.getComplianceSubTask()==null)
            return new ResponseEntity().badRequest("Please select compliance Task/Sub-Task !!");

        RenewalReminder findRenewalReminder=this.renewalReminderDao.findRenewalReminderByComplianceTaskOrSubTaskAndIdNot
                (renewalReminder.getComplianceTask(),renewalReminder.getComplianceSubTask(),renewalReminder.getId());

        if(findRenewalReminder!=null)
            return new ResponseEntity().badRequest("Renewal Reminder already exist !!");

        renewalReminder.setUpdatedAt(CommonUtil.getDate());
        RenewalReminder updateRenewalReminder=this.renewalReminderDao.updateRenewalReminder(renewalReminder);
        if(updateRenewalReminder==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchRenewalReminder(Long id) {
        RenewalReminder renewalReminder=this.renewalReminderDao.findRenewalReminderById(id);
        if(renewalReminder==null)
            return new ResponseEntity().badRequest("Renewal Reminder Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToRenewalReminderResponse(renewalReminder));
    }

    @Override
    public ResponseEntity deleteRenewalReminder(Long id) {
        RenewalReminder renewalReminder=this.renewalReminderDao.findRenewalReminderById(id);
        if(renewalReminder==null)
            return new ResponseEntity().badRequest("Renewal Reminder Not Found !!");

        boolean deleteRenewalReminder=this.renewalReminderDao.deleteRenewalReminder(renewalReminder);
        if(!deleteRenewalReminder)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
