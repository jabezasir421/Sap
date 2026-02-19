package com.example.sap.technical;

import com.example.sap.dto.TechnicalComponentResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TechnicalComponentService {

    public List<TechnicalComponentResponse> listComponents() {
        return List.of(
                new TechnicalComponentResponse("ABAP", "Integrated", "ABAP compatibility via integration surface and IDoc APIs."),
                new TechnicalComponentResponse("Database (HANA)", "Profile Ready", "HANA profile template in application-hana.yml."),
                new TechnicalComponentResponse("Application Server", "Active", "Spring Boot application server with REST + SSE."),
                new TechnicalComponentResponse("Authorization System", "Active", "OAuth2 JWT + role-based method security + tenant isolation."),
                new TechnicalComponentResponse("Integration Tools", "Active", "REST APIs and IDoc ingestion endpoints.")
        );
    }
}
