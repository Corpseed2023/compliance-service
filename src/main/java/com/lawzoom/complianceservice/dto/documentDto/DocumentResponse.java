package com.lawzoom.complianceservice.dto.documentDto;

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
public class DocumentResponse {

	private Long id;
	
	private String documentName;
	
	private String fileName;
	
	private Date issueDate;
	
	private String referenceNumber;
	
	private String remarks;

	private Date uploadDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
}
