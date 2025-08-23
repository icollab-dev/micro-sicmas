package com.mx.microsicmas.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileFindingActionDTO {
    private Long fileId;
    private Long findingActionId;
    private String fileName;
    private String fileType;
    private String fileContentType;
    private Long fileSize;
    private byte[] fileData;
    private String fileDownloadUri;
}
