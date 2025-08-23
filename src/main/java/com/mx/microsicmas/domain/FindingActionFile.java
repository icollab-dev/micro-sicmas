package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "TX_SIC_FINDING_ACTION_FILES")
@Getter
@Setter
@NoArgsConstructor
public class FindingActionFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_TX_SIC_FINDING_ACTION_FILES")
    @SequenceGenerator(name = "id_Sequence_TX_SIC_FINDING_ACTION_FILES", sequenceName = "TX_SIC_FINDING_ACTION_FILES_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINDING_ACTION_ID", nullable = false)
    private FindingAction findingAction;

    @Basic(optional = false)
    @Column(name = "FILE_NAME")
    private String fileName;

    @Basic(optional = false)
    @Column(name = "FILE_TYPE")
    private String fileType;

    @Basic(optional = false)
    @Column(name = "FILE_CONTENT_TYPE")
    private String fileContentType;

    @Basic(optional = false)
    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Lob
    @Column(name = "FILE_DATA")
    private byte[] fileData;
}
