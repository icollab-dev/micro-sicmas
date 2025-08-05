package com.mx.microsicmas.controller;

import com.mx.microsicmas.domain.SupportPlanning;
import com.mx.microsicmas.model.request.UploadFilePlanningDTO;
import com.mx.microsicmas.payload.UploadFileResponse;
import com.mx.microsicmas.service.SupportPlanningService;
import com.mx.microsicmas.util.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/support")
public class SupportPlanningController {
    @Autowired
    private SupportPlanningService supportPlanningService;

    public static String ABSOLUT_PATH = "/opt/icollab/data/tmp/sicma/";

    @CrossOrigin(origins = "*")
    @PostMapping(value="/saveFile")
    public UploadFileResponse saveFile(@RequestBody() UploadFilePlanningDTO uploadFileResponseDTO) {
        SupportPlanning dbFile = supportPlanningService.storeFile(uploadFileResponseDTO);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        BeanUtils.copyProperties(dbFile, uploadFileResponse);
        uploadFileResponse.setFileDownloadUri(fileDownloadUri);
        uploadFileResponse.setFileId(dbFile.getId());
        uploadFileResponse.setPlanningId(dbFile.getPlanning().getId());
        String val = String.valueOf(dbFile.getId());
        FileUtils.saveOnDisk(ABSOLUT_PATH, val, uploadFileResponse.getFileName(), uploadFileResponse.getFileData());
        return uploadFileResponse;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) {
        // Load file from database
        SupportPlanning dbFile = supportPlanningService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getFileData()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getFile/{fileId}")
    public UploadFileResponse getFile(@PathVariable("fileId") Long fileId) {
        // Load file from database
        SupportPlanning dbFile = supportPlanningService.getFile(fileId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();

        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        BeanUtils.copyProperties(dbFile, uploadFileResponse);
        uploadFileResponse.setFileData(dbFile.getFileData());
        uploadFileResponse.setPlanningId(dbFile.getPlanning().getId());
        uploadFileResponse.setFileId(dbFile.getId());
        uploadFileResponse.setFileDownloadUri(fileDownloadUri);
        uploadFileResponse.setFileData(null);
        return uploadFileResponse;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listFiles/{planningId}")
    public Object listFiles(@PathVariable("planningId") Long planningId) {
        return supportPlanningService.listFilesByPlanning(planningId);
    }
}
