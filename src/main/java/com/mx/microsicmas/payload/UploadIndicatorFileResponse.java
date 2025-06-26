package com.mx.microsicmas.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadIndicatorFileResponse {
	private Long fileId;
    private Long idindicatorconfig;
    private String fileName;
    private String fileType;
    private String fileContentType;
    private Long fileSize;
    private byte[] fileData;
    private String fileDownloadUri;
}
