package com.example.sap.service;

import com.example.sap.dto.ModuleSummaryResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ErpModuleService {

    public List<ModuleSummaryResponse> listModules() {
        return List.of(
                new ModuleSummaryResponse(
                        "FI - Financial Accounting",
                        "Active",
                        "External reporting",
                        "Legal books of record, statutory reporting, and financial close.",
                        "Without FI, a company cannot operate legally.",
                        List.of(
                                "General Ledger",
                                "Accounts Payable",
                                "Accounts Receivable",
                                "Asset Accounting",
                                "Financial Reporting"
                        )
                ),
                new ModuleSummaryResponse(
                        "CO - Controlling",
                        "Active",
                        "Internal decision-making",
                        "Management accounting for cost governance and performance.",
                        "CO complements FI by driving internal planning and optimization.",
                        List.of(
                                "Cost center accounting",
                                "Profit center analysis",
                                "Budget tracking",
                                "Internal cost monitoring"
                        )
                ),
                new ModuleSummaryResponse(
                        "MM - Materials Management",
                        "Active",
                        "Operational",
                        "End-to-end purchasing and stock economics.",
                        "",
                        List.of(
                                "Purchase orders",
                                "Vendor management",
                                "Inventory control",
                                "Goods receipt",
                                "Stock valuation"
                        )
                ),
                new ModuleSummaryResponse(
                        "PP - Production Planning",
                        "Active",
                        "Operational",
                        "Planning and execution for manufacturing flow.",
                        "",
                        List.of(
                                "Bill of materials (BOM)",
                                "Production orders",
                                "Capacity planning",
                                "Manufacturing scheduling"
                        )
                ),
                new ModuleSummaryResponse(
                        "SD - Sales and Distribution",
                        "Active",
                        "Operational",
                        "Commercial process from order to cash and delivery.",
                        "",
                        List.of(
                                "Sales orders",
                                "Pricing",
                                "Billing",
                                "Shipping",
                                "Customer management"
                        )
                ),
                new ModuleSummaryResponse(
                        "HCM - Human Capital Management",
                        "Active",
                        "Operational",
                        "Workforce operations from hiring through compensation and time tracking.",
                        "",
                        List.of(
                                "Payroll",
                                "Employee records",
                                "Attendance",
                                "Recruitment"
                        )
                )
        );
    }
}
