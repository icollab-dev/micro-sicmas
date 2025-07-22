package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TX_SIC_PLANNING_SUPPORT")
@Getter
@Setter
@NoArgsConstructor
public class SupportPlanning {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_TX_SIC_PLANNING_SUPPORT")
    @SequenceGenerator(name = "id_Sequence_TX_SIC_PLANNING_SUPPORT", sequenceName = "TX_SIC_PLANNING_SUPPORT_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Basic(optional = false)
    @Column(name = "PLANNING_ID")
    private long planningId;

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
