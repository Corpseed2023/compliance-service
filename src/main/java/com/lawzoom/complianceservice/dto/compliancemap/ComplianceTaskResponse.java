package com.lawzoom.complianceservice.dto.compliancemap;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceTaskResponse {

	private Long id;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String taskName;
	
	@Min(value = 1)
	private int timelineValue;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String timelineType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	private String criticality;

	private List<ComplianceSubTaskResponse> complianceSubTaskResponseList=new ArrayList<>();

}
