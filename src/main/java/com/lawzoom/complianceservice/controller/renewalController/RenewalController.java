package com.lawzoom.complianceservice.controller.renewalController;

import com.lawzoom.complianceservice.model.renewalModel.RenewalReminder;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.renewalReminderService.RenewalReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/compliance/renewal-reminder")
public class RenewalController {

	@Autowired
	private RenewalReminderService renewalReminderService;

	@PostMapping("/save")
	public ResponseEntity saveRenewalReminder(@Valid @RequestBody RenewalReminder renewalReminder){
		return this.renewalReminderService.saveRenewalReminder(renewalReminder);
	}
	
	@PutMapping("/update")
	public ResponseEntity updateRenewalReminder(@Valid @RequestBody RenewalReminder renewalReminder){
		return this.renewalReminderService.updateRenewalReminder(renewalReminder);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity fetchRenewalReminder(@PathVariable("id") Long id){
		return this.renewalReminderService.fetchRenewalReminder(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteRenewalReminder(@PathVariable("id") Long id){
		return this.renewalReminderService.deleteRenewalReminder(id);
	}
}
