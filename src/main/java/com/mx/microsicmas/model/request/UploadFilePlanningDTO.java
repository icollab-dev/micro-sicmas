package com.mx.microsicmas.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFilePlanningDTO {
    private Long fileId;
    private Long planningId;
    private String fileName;
    private String fileType;
    private String fileContentType;
    private Long fileSize;
    private byte[] fileData;
    private String fileDownloadUri;
    private boolean rca;
}
