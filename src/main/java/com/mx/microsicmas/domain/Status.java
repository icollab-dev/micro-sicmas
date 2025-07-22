package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAT_SIC_ADITORY_STATUS")
@Getter
@Setter
@NoArgsConstructor
public class Status extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_CAT_SIC_ADITORY_STATUS")
    @SequenceGenerator(name = "id_Sequence_CAT_SIC_ADITORY_STATUS", sequenceName = "CAT_SIC_ADITORY_STATUS_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "AUDITORY")
    private Boolean auditory;
    @Column(name = "APPROVAL")
    private Boolean approval;

    @OneToMany(mappedBy = "auditoryStatus", fetch = FetchType.LAZY)
    private List<Planning> planningsAsAuditoryStatus = new ArrayList<>();

    @OneToMany(mappedBy = "approvalStatus", fetch = FetchType.LAZY)
    private List<Planning> planningsAsApprovalStatus = new ArrayList<>();
}
