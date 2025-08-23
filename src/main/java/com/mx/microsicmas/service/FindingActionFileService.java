package com.mx.microsicmas.service;


import com.mx.microsicmas.domain.FindingActionFile;
import com.mx.microsicmas.model.request.UploadFileFindingActionDTO;
import com.mx.microsicmas.payload.UploadFileFindingResponse;

import java.util.List;

public interface FindingActionFileService {
    public FindingActionFile getFile(Long fileId);
    public FindingActionFile storeFile(UploadFileFindingActionDTO dbFile);
    public List<UploadFileFindingResponse> listFilesByActionFinding(Long findingActionId);
}
