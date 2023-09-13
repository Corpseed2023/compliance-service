package com.lawzoom.complianceservice.serviceImpl.reminderServiceImpl;

import com.lawzoom.complianceservice.dao.reminderDao.ReminderDao;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.reminderService.ReminderService;
import com.lawzoom.complianceservice.utility.CommonUtil;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderDao reminderDao;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity saveReminder(Reminder reminder) {
        if(reminder.getCompliance()==null
                &&reminder.getComplianceTask()==null&&reminder.getComplianceSubTask()==null)
        return new ResponseEntity().badRequest("Please select compliance/Task/Sub-Task !!");

        Reminder findReminder=this.reminderDao.findReminderByComplianceOrTaskOrSubTask
                (reminder.getCompliance(),reminder.getComplianceTask(),reminder.getComplianceSubTask());

        if(findReminder!=null)
            return new ResponseEntity().badRequest("Reminder already exist !!");
        reminder.setCreatedAt(CommonUtil.getDate());
        reminder.setUpdatedAt(CommonUtil.getDate());
        reminder.setEnable(true);
        Reminder saveReminder=this.reminderDao.saveReminder(reminder);
        if(saveReminder==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateReminder(Reminder reminder) {
        if(reminder.getCompliance()==null
                &&reminder.getComplianceTask()==null&&reminder.getComplianceSubTask()==null)
            return new ResponseEntity().badRequest("Please select compliance/Task/Sub-Task !!");

        Reminder findReminder=this.reminderDao.findReminderByComplianceOrTaskOrSubTaskAndIdNot
                (reminder.getCompliance(),reminder.getComplianceTask(),reminder.getComplianceSubTask(),reminder.getId());

        if(findReminder!=null)
            return new ResponseEntity().badRequest("Reminder already exist !!");

        reminder.setUpdatedAt(CommonUtil.getDate());
        Reminder updateReminder=this.reminderDao.updateReminder(reminder);
        if(updateReminder==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchReminder(Long id) {

        Reminder reminder=this.reminderDao.findReminderById(id);
        if(reminder==null)
            return new ResponseEntity().badRequest("Reminder Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToReminderResponse(reminder));
    }

    @Override
    public ResponseEntity deleteReminder(Long id) {
        Reminder reminder=this.reminderDao.findReminderById(id);
        if(reminder==null)
            return new ResponseEntity().badRequest("Reminder Not Found !!");

        boolean deleteReminder=this.reminderDao.deleteReminder(reminder);
        if(!deleteReminder)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
