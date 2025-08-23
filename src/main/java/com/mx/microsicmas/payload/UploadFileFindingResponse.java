package com.mx.microsicmas.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UploadFileFindingResponse {
    private Long fileId;
    private Long findingActionId;
    private String fileName;
    private String fileType;
    private String fileContentType;
    private Long fileSize;
    private byte[] fileData;
    private String fileDownloadUri;
    private Date dateCreated;
}
