package com.mx.microsicmas.service.impl;

import com.dgc.dto.uploadFileBd.UploadFileResponseDTO;
import com.mx.microsicmas.domain.Planning;
import com.mx.microsicmas.domain.SupportPlanning;
import com.mx.microsicmas.model.request.UploadFilePlanningDTO;
import com.mx.microsicmas.payload.UploadFileResponse;
import com.mx.microsicmas.repository.PlanningRepository;
import com.mx.microsicmas.repository.SupportPlanningRepository;
import com.mx.microsicmas.service.SupportPlanningService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.ArrayList;

@Service
public class SupportPlanningServiceImpl implements SupportPlanningService {
    @Autowired
    SupportPlanningRepository dbFileRepository;

    @Autowired
    PlanningRepository planningRepository;


    @Override
    public SupportPlanning getFile(Long fileId) {
        return dbFileRepository.findSupportPlanningsById(fileId);
    }

    @Override
    public SupportPlanning storeFile(UploadFilePlanningDTO dbFile) {

        SupportPlanning supportPlanning = new SupportPlanning();
        BeanUtils.copyProperties(dbFile, supportPlanning);
        supportPlanning.setActive(true);
        Planning planning = planningRepository.findById(dbFile.getPlanningId())
                .orElseThrow(() -> new RuntimeException("Planning not found"));
        supportPlanning.setPlanning(planning);

        return dbFileRepository.save(supportPlanning);
    }


    @Override
    public List<UploadFileResponse> listFilesByPlanning(Long planningId) {
        List<UploadFileResponse> dtos = new ArrayList<UploadFileResponse>();
        dbFileRepository.findByPlanningId(planningId).stream().map((dbFile) -> {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();
            UploadFileResponse out = new UploadFileResponse();
            BeanUtils.copyProperties(dbFile, out);
            out.setFileDownloadUri(fileDownloadUri);
            out.setFileName(dbFile.getFileName());
            out.setFileSize(dbFile.getFileSize());
            out.setFileType(dbFile.getFileType());
            out.setFileId(dbFile.getId());
            out.setPlanningId(dbFile.getPlanning().getId());
            out.setFileData(null);
            return out;
        }).forEachOrdered((out) -> {
            dtos.add(out);
        });
        return dtos;
    }
}
