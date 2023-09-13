package com.lawzoom.complianceservice.service.renewalReminderService;

import com.lawzoom.complianceservice.model.renewalModel.RenewalReminder;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface RenewalReminderService {
    ResponseEntity saveRenewalReminder(RenewalReminder renewalReminder);

    ResponseEntity updateRenewalReminder(RenewalReminder renewalReminder);

    ResponseEntity fetchRenewalReminder(Long id);

    ResponseEntity deleteRenewalReminder(Long id);
}
