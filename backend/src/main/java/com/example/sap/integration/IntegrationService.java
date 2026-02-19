package com.example.sap.integration;

import com.example.sap.dto.CreateIdocRequest;
import com.example.sap.dto.IdocResponse;
import com.example.sap.multitenancy.TenantContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final IdocRepository idocRepository;

    @Transactional
    public IdocResponse createIdoc(CreateIdocRequest request) {
        Idoc idoc = new Idoc(request.messageType(), request.direction(), request.payload());
        Idoc saved = idocRepository.save(idoc);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IdocResponse> listIdocs() {
        String tenantId = TenantContext.requireTenant();
        return idocRepository.findAllByTenantIdOrderByCreatedAtDesc(tenantId).stream()
                .map(this::toResponse)
                .toList();
    }

    private IdocResponse toResponse(Idoc idoc) {
        return new IdocResponse(
                idoc.getId(),
                idoc.getMessageType(),
                idoc.getDirection(),
                idoc.getStatus(),
                idoc.getPayload(),
                idoc.getCreatedAt());
    }
}
