package com.lawzoom.complianceservice.model.documentModel;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "compliance_document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "doc_name")
	private String documentName;
	
	@Column(name = "file_name")
	private String fileName;

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_date")
	private Date issueDate;
	
	@Column(name = "reference_number")
	private String referenceNumber;
	
	@Column(name = "remarks",columnDefinition = "TEXT")
	private String remarks;

	@Temporal(TemporalType.DATE)
	@Column(name = "upload_date")
	private Date uploadDate;
	
	@ManyToOne(targetEntity = Compliance.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_id")
	private Compliance compliance;
	
	@ManyToOne(targetEntity = ComplianceTask.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_task_id")
	private ComplianceTask complianceTask;
	
	@ManyToOne(targetEntity = ComplianceSubTask.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_sub_task_id")
	private ComplianceSubTask complianceSubTask;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
