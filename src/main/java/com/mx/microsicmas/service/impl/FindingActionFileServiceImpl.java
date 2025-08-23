package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.FindingAction;
import com.mx.microsicmas.domain.FindingActionFile;
import com.mx.microsicmas.model.request.UploadFileFindingActionDTO;
import com.mx.microsicmas.payload.UploadFileFindingResponse;
import com.mx.microsicmas.repository.FindingActionFileRepository;
import com.mx.microsicmas.repository.FindingActionRepository;
import com.mx.microsicmas.service.FindingActionFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FindingActionFileServiceImpl implements FindingActionFileService {
    @Autowired
    private FindingActionFileRepository findingActionFileRepository;
    @Autowired
    private FindingActionRepository findingActionRepository;

    @Override
    public FindingActionFile getFile(Long fileId) {
        return findingActionFileRepository.findFindingActionFileById(fileId);
    }

    @Override
    public FindingActionFile storeFile(UploadFileFindingActionDTO dbFile) {
        FindingActionFile findingActionFile = new FindingActionFile();
        BeanUtils.copyProperties(dbFile, findingActionFile);
        findingActionFile.setActive(true);
        FindingAction findingAction = findingActionRepository.findById(dbFile.getFindingActionId())
                .orElseThrow(() -> new RuntimeException("Finding action not found"));
        findingActionFile.setFindingAction(findingAction);
        return findingActionFileRepository.save(findingActionFile);
    }

    @Override
    public List<UploadFileFindingResponse> listFilesByActionFinding(Long findingActionId) {
        List<UploadFileFindingResponse> uploadFileResponses = new ArrayList<UploadFileFindingResponse>();
        findingActionFileRepository.findByFindingActionId(findingActionId).stream().map(
                (dbFile)-> {
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/action/downloadFile/")
                            .path(String.valueOf(dbFile.getId()))
                            .toUriString();
                    UploadFileFindingResponse uploadFileResponse = new UploadFileFindingResponse();
                    BeanUtils.copyProperties(dbFile, uploadFileResponse);
                    uploadFileResponse.setFileDownloadUri(fileDownloadUri);
                    uploadFileResponse.setFileName(dbFile.getFileName());
                    uploadFileResponse.setFileSize(dbFile.getFileSize());
                    uploadFileResponse.setFileType(dbFile.getFileType());
                    uploadFileResponse.setFileId(dbFile.getId());
                    uploadFileResponse.setFindingActionId(dbFile.getFindingAction().getId());
                    uploadFileResponse.setFileData(null);
                    return uploadFileResponse;
                }
        ).forEachOrdered((uploadFileResponse)->{
            uploadFileResponses.add(uploadFileResponse);
        });
        return uploadFileResponses;
    }
}
