package com.lawzoom.complianceservice.model.complianceCategoryModel;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
//@Entity
//@Table(name = "compliance_category")
public class ComplianceCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String title;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String slug;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
	
//	@OneToMany(mappedBy = "complianceCategory",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<Compliance> complianceList=new ArrayList<>();

}
