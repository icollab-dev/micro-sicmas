package com.mx.microsicmas.service;

import com.dgc.dto.uploadFileBd.UploadFileResponseDTO;
import com.mx.microsicmas.domain.SupportPlanning;
import com.mx.microsicmas.model.request.UploadFilePlanningDTO;
import com.mx.microsicmas.payload.UploadFileResponse;

import java.util.List;

public interface SupportPlanningService {

    public SupportPlanning getFile(Long fileId);
    public SupportPlanning storeFile(UploadFilePlanningDTO dbFile);
    public List<UploadFileResponse> listFilesByPlanning(Long planningId);
}
