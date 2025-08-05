package com.mx.microsicmas.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TX_SIC_PLANNING")
@Getter
@Setter
@NoArgsConstructor
public class Planning extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence_TX_SIC_PLANNING")
    @SequenceGenerator(name = "id_Sequence_TX_SIC_PLANNING", sequenceName = "TX_SIC_PLANNING_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RULE_ID")
    private Rule rule;
    @Column(name = "VERSION")
    private String version;
    @Column(name = "OBJETIVE")
    private String objetive;
    @Column(name = "SCOPE")
    private String scope;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADITORY_ENTITY_ID")
    private AuditoryEntity auditoryEntity;
    @Column(name = "AUDITOR_NAME")
    private String auditorName;
    @Column(name = "SUMMARY")
    private String summary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUDITORY_STATUS_ID")
    private Status auditoryStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_APPROVAL_ID")
    private Status approvalStatus;
    @Column(name = "NUM")
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASIFICATION_ID")
    private ClassificationAudit classification;
    @Column(name = "START_DATE_AUDITORY")
    private Date startDateAuditory;
    @Column(name = "END_DATE_AUDITORY")
    private Date endDateAuditory;
    @OneToMany(mappedBy = "planning", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanningRecommendation> recommendations = new ArrayList<>();
    @OneToMany(mappedBy = "planning", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Finding> findings = new ArrayList<>();
    @OneToMany(mappedBy = "planning", fetch = FetchType.LAZY)
    private List<SupportPlanning> supports;
}
