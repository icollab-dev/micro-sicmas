package com.mx.microsicmas.service;

import com.mx.microsicmas.domain.FindingStaff;
import com.mx.microsicmas.model.request.StaffFindingRequest;
import com.mx.microsicmas.model.response.StaffFindingResponse;

import java.util.List;

public interface FindingStaffService {
    StaffFindingResponse addStaffToFinding(StaffFindingRequest request) ;
    List<StaffFindingResponse> getStaffByFindingId(Long id);
}
