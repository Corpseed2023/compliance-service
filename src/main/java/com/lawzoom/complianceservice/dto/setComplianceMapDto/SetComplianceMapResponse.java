package com.lawzoom.complianceservice.dto.setComplianceMapDto;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SetComplianceMapResponse {

	private Long companyId;

	private String companyName;

	private Long businessUnitId;

	private String businessUnitCity;

	private String stateJurisdiction;

	private String businessActivity;

	private Long complianceCount;

	private String teamName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
