package com.mx.microsicmas.service.impl;

import com.mx.microsicmas.domain.Finding;
import com.mx.microsicmas.domain.FindingStaff;
import com.mx.microsicmas.domain.Staff;
import com.mx.microsicmas.model.request.StaffFindingRequest;
import com.mx.microsicmas.model.response.StaffFindingResponse;
import com.mx.microsicmas.repository.FindingRepository;
import com.mx.microsicmas.repository.FindingStaffRepository;
import com.mx.microsicmas.repository.StaffRepository;
import com.mx.microsicmas.service.FindingStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindingStaffServiceImpl implements FindingStaffService {
    @Autowired
    private FindingRepository findingRepository;

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private FindingStaffRepository findingStaffRepository;

    public StaffFindingResponse addStaffToFinding(StaffFindingRequest request) {
        Finding finding = findingRepository.findById(request.getIdFinding())
                .orElseThrow(() -> new EntityNotFoundException("Finding no encontrado con id: " + request.getIdFinding()));

        Staff staff = staffRepository.findById(request.getIdStaff())
                .orElseThrow(() -> new EntityNotFoundException("Staff no encontrado con id: " + request.getIdStaff()));

        FindingStaff fs = new FindingStaff();
        fs.setFinding(finding);
        fs.setStaff(staff);
        // Datos del BaseEntity
        fs.setActive(true);

        FindingStaff findingSave =  findingStaffRepository.save(fs);
        return StaffFindingResponse.builder()
                .id(findingSave.getId())
                .company(findingSave.getStaff().getCompany())
                .name(findingSave.getStaff().getName())
                .build();
    }
    public List<StaffFindingResponse> getStaffByFindingId(Long findingId) {
        List<FindingStaff> staffLinks = findingStaffRepository.findByFindingIdAndActiveTrue(findingId);

        return staffLinks.stream().map(fs -> {
            Staff s = fs.getStaff();
            StaffFindingResponse dto = new StaffFindingResponse();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setCompany(s.getCompany());
            return dto;
        }).collect(Collectors.toList());
    }
}
