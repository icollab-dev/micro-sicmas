package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TX_SIC_FINDING")
@Getter
@Setter
@NoArgsConstructor
public class Finding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence_TX_SIC_FINDING")
    @SequenceGenerator(name = "id_Sequence_TX_SIC_FINDING", sequenceName = "TX_SIC_FINDING_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "NUM_FINDING")
    private String numFinding;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLANNING", nullable = true)
    private Planning planning;
    @Column(name = "FINDING_DATE")
    private Date date;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "NAME")
    private String name;
    @Column(name=   "POINT_RULE")
    private String pointRule;
    @Column(name = "END_DATE")
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIORITY_ID", nullable = false)
    private Priority priority;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASIFICATION_ID", nullable = false)
    private ClassificationAudit classification;
    @Column(name = "USER_REPORTER")
    private String userReporter;
    @Column(name = "DATE_REPORTED")
    private Date dateReported;
    @Column(name = "USER_SUPERVISED")
    private String userSupervised;
    @Column(name = "DATE_SUPERVISED")
    private Date dateSupervised;
    @Column(name = "USER_APPROVAL")
    private String userApproval;
    @Column(name = "DATE_APPROVED")
    private Date dateApproved;
    @Column(name = "USER_REJECT")
    private String userReject;
    @Column(name = "DATE_REJECTED")
    private Date dateRejected;
    @Column(name = "STATUS_APPROVAL")
    private String statusApproval;
    @Column(name = "STATUS_EVENT")
    private String statusEvent;
    @ManyToOne
    @JoinColumn(name = "RESOURCE_REQUEST_ID")
    private ResourceRequest resourceRequest;
    @OneToMany(mappedBy = "finding", fetch = FetchType.LAZY)
    private List<FindingAction> actions = new ArrayList<>();
}
