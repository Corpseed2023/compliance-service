package com.lawzoom.complianceservice.dto.complianceSubTaskDto;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceSubTaskResponse {

	private Long id;
	
	private String subTaskName;

	private String description;
	
	private int timelineValue;
	
	private String timelineType;
	
	private String status;
	
	private String approvalState;
	
	private String applicableZone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date startDate;
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date dueDate;
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date completedDate;

	private String criticality;

	private Long reporterUserId;

	private Long assigneeUserId;
	
}
