package com.lawzoom.complianceservice.dto.legalGuideDto;

import com.lawzoom.complianceservice.model.complianceGuide.ComplianceGuide;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LegalGuideResponse {

	private Long id;

	private String title;

	private String referenceSlug;

	private String description;

	private String department;

	private String authority;

	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date publishDate;

	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date applicableDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
