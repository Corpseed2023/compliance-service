package com.lawzoom.complianceservice.dto.renewalReminderResponse;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RenewalReminderResponse {

	private Long id;

	private Date certificateIssueDate;
	
	private int certificateIssueForValue;
	
	private String certificateIssueForType;
	
	private int notificationTimelineValue;
	
	private String notificationTimelineType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
