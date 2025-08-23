package com.mx.microsicmas.controller;

import com.mx.microsicmas.domain.*;
import com.mx.microsicmas.model.request.*;
import com.mx.microsicmas.model.response.FindingActionResoponse;
import com.mx.microsicmas.model.response.FindingResponse;
import com.mx.microsicmas.model.response.FindingResponseOut;
import com.mx.microsicmas.model.response.StaffFindingResponse;
import com.mx.microsicmas.payload.UploadFileFindingResponse;
import com.mx.microsicmas.payload.UploadFileResponse;
import com.mx.microsicmas.service.FindigActionService;
import com.mx.microsicmas.service.FindingActionFileService;
import com.mx.microsicmas.service.FindingService;
import com.mx.microsicmas.service.FindingStaffService;
import com.mx.microsicmas.util.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/microsicmas/finding")
@Validated
public class FindingController {
    @Autowired
    private FindingService findingService;
    @Autowired
    private FindingStaffService findingStaffService;
    @Autowired
    private FindigActionService findigActionService;
    @Autowired
    private FindingActionFileService findingActionFileService;
    public static String ABSOLUT_PATH = "/opt/icollab/data/tmp/sicma/";
    @PostMapping("/new")
    public ResponseEntity<FindingResponse> newFinding(@RequestBody @Validated FindingRequest finding) {
        return ResponseEntity.ok(findingService.save(finding));
    }

    @PostMapping("/list")
    public ResponseEntity<List<FindingResponseOut>> listFinding() {
        return ResponseEntity.ok(findingService.list());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<FindingResponseOut> listFinding(@PathVariable Long id) {
        return ResponseEntity.ok(findingService.findById(id));
    }

    @PostMapping("/staff/add")
    public ResponseEntity<StaffFindingResponse> addStaff(@RequestBody @Validated StaffFindingRequest staff) {
        return ResponseEntity.ok(findingStaffService.addStaffToFinding(staff));
    }

    @GetMapping("/list/staff/{id}")
    public ResponseEntity<List<StaffFindingResponse>> listFindingStaff(@PathVariable Long id) {
        return ResponseEntity.ok(findingStaffService.getStaffByFindingId(id));
    }

    @PostMapping("/action/new")
    public ResponseEntity<FindingActionResoponse> save(@RequestBody @Validated FindingActionRequest request) {
        return ResponseEntity.ok(findigActionService.save(request));
    }

    @GetMapping("/action/list/{id}")
    public ResponseEntity<List<FindingActionResoponse>> listFindingAction(@PathVariable Long id) {
        return ResponseEntity.ok(findigActionService.listByFinding(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(findingService.delete(id));
    }

    @PutMapping()
    public ResponseEntity<FindingResponse> update(@RequestBody @Validated FindingUpdateRequest findingRequest) {
        return ResponseEntity.ok(findingService.update(findingRequest));
    }

    @PutMapping("/action")
    public ResponseEntity<FindingActionResoponse> update(@RequestBody @Validated FindingActionUpdateRequest findingRequest) {
        return ResponseEntity.ok(findigActionService.update(findingRequest));
    }
    
    @DeleteMapping("/action/{id}")
    public ResponseEntity<Boolean> deleteAction(@PathVariable Long id) {
        return ResponseEntity.ok(findigActionService.delete(id));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/action/saveFile")
    public UploadFileFindingResponse saveFile(@RequestBody UploadFileFindingActionDTO uploadFileFindingActionDTO) {
        FindingActionFile dbFile = findingActionFileService.storeFile(uploadFileFindingActionDTO);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/action/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();
        UploadFileFindingResponse uploadFileFindingResponse = new UploadFileFindingResponse();
        BeanUtils.copyProperties(dbFile, uploadFileFindingResponse);
        uploadFileFindingResponse.setFileDownloadUri(fileDownloadUri);
        uploadFileFindingResponse.setFileId(dbFile.getId());
        uploadFileFindingResponse.setFindingActionId(dbFile.getFindingAction().getId());
        String val = String.valueOf(dbFile.getId());
        FileUtils.saveOnDisk(ABSOLUT_PATH,val,uploadFileFindingResponse.getFileName(), uploadFileFindingResponse.getFileData());
        return uploadFileFindingResponse;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/action/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) {
        // Load file from database
        FindingActionFile dbFile = findingActionFileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getFileData()));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/action/getFile/{fileId}")
    public UploadFileFindingResponse getFile(@PathVariable("fileId") Long fileId) {
        // Load file from database
        FindingActionFile dbFile = findingActionFileService.getFile(fileId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/action/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();

        UploadFileFindingResponse uploadFileResponse = new UploadFileFindingResponse();
        BeanUtils.copyProperties(dbFile, uploadFileResponse);
        uploadFileResponse.setFileData(dbFile.getFileData());
        uploadFileResponse.setFindingActionId(dbFile.getFindingAction().getId());
        uploadFileResponse.setFileId(dbFile.getId());
        uploadFileResponse.setFileDownloadUri(fileDownloadUri);
        uploadFileResponse.setFileData(null);
        return uploadFileResponse;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/action/listFiles/{findingActionId}")
    public Object listFiles(@PathVariable("findingActionId") Long findingActionId) {
        return findingActionFileService.listFilesByActionFinding(findingActionId);
    }

}
