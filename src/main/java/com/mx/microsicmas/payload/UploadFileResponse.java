package com.mx.microsicmas.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UploadFileResponse {
    private Long fileId;
    private Long dieselId;
    private String fileName;
    private String fileType;
    private String fileContentType;
    private Long fileSize;
    private byte[] fileData;
    private String fileDownloadUri;
    private Date dateCreated;
    private boolean rca;
    private long statusId;
	private String status;
}
