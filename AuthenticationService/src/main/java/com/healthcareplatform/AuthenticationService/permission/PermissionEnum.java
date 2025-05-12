package com.healthcareplatform.AuthenticationService.permission;

public enum PermissionEnum {

    VIEW_PATIENT_RECORDS("ViewPatientRecords",
            "Allows access to patient medical records for review and verification."),
    EDIT_PATIENT_RECORDS("EditPatientRecords",
            "Allows modifications to existing patient medical records."),
    DELETE_PATIENT_RECORDS("DeletePatientRecords",
            "Allows deletion of patient medical records when authorized."),
    CREATE_PATIENT_RECORDS("CreatePatientRecords",
            "Allows creation of new patient records in the system."),
    VIEW_SCHEDULING("ViewScheduling",
            "Allows access to view appointment and scheduling information."),
    EDIT_SCHEDULING("EditScheduling",
            "Allows modifications to appointment schedules and shifts."),
    APPROVE_BILLING("ApproveBilling",
            "Allows approval of billing transactions and insurance claims."),
    EDIT_BILLING("EditBilling",
            "Allows modifications of billing information and invoice details."),
    VIEW_FINANCIAL_REPORTS("ViewFinancialReports",
            "Allows access to financial performance and revenue reports."),
    MANAGE_USER_ACCOUNTS("ManageUserAccounts",
            "Allows creation, modification, and deletion of user accounts."),
    ASSIGN_ROLES("AssignRoles",
            "Allows assignment and revocation of roles to/from user accounts."),
    ACCESS_DASHBOARD("AccessDashboard",
            "Allows access to the system’s operational dashboards."),
    GENERATE_REPORTS("GenerateReports",
            "Allows creation of various operational, clinical, and administrative reports."),
    MANAGE_INVENTORY("ManageInventory",
            "Allows control over medical and office inventory records."),
    APPROVE_INVENTORY_ORDERS("ApproveInventoryOrders",
            "Allows approval of orders for inventory replenishment."),
    MANAGE_STAFF_SCHEDULES("ManageStaffSchedules",
            "Allows scheduling and shift management for staff members."),
    ACCESS_AUDIT_LOGS("AccessAuditLogs",
            "Allows viewing of system audit logs and user activity histories."),
    MANAGE_PERMISSIONS("ManagePermissions",
            "Allows configuration and modification of system permissions."),
    ACCESS_ADMINISTRATION_PANEL("AccessAdministrationPanel",
            "Allows access to the administration control panel for system settings."),
    EDIT_DEPARTMENT_DATA("EditDepartmentData",
            "Allows modification of department-specific data and settings."),
    VIEW_DEPARTMENT_DATA("ViewDepartmentData",
            "Allows viewing of data specific to a hospital department."),
    MANAGE_CLINICAL_TRIALS("ManageClinicalTrials",
            "Allows control and administration of clinical trial information."),
    ACCESS_RESEARCH_DATA("AccessResearchData",
            "Allows access to research data, study results, and documentation."),
    MANAGE_APPOINTMENTS("ManageAppointments",
            "Allows creation, modification, and cancellation of patient appointments."),
    VIEW_INVENTORY("ViewInventory",
            "Allows viewing of current inventory levels and item details."),
    PROCESS_MEDICAL_ORDERS("ProcessMedicalOrders",
            "Allows processing and verification of medical orders."),
    MANAGE_EMERGENCY_RESPONSES("ManageEmergencyResponses",
            "Allows oversight and management of emergency response protocols."),
    AUTHORIZE_SURGERY("AuthorizeSurgery",
            "Allows authorization and scheduling of surgical procedures."),
    APPROVE_MEDICAL_PROCEDURES("ApproveMedicalProcedures",
            "Allows final approval of various medical procedures and interventions."),
    MANAGE_MEDICAL_RECORDS("ManageMedicalRecords",
            "Allows full management (creation, editing, deletion) of patient medical records."),
    CONFIGURE_SYSTEM_SETTINGS("ConfigureSystemSettings",
            "Allows configuration of system-wide settings and parameters."),
    ACCESS_IT_RESOURCES("AccessITResources",
            "Allows access to IT systems, network resources, and infrastructure management."),
    VIEW_STAFF_PROFILES("ViewStaffProfiles",
            "Allows viewing of hospital staff profiles."),
    EDIT_STAFF_PROFILES("EditStaffProfiles",
            "Allows editing of hospital staff profiles."),
    VIEW_PRESCRIPTIONS("ViewPrescriptions",
            "Allows viewing of prescription records."),
    CREATE_INVOICES("CreateInvoices",
            "Allows generation of invoice records."),
    VIEW_CLAIMS("ViewClaims",
            "Allows viewing of insurance claim statuses."),
    SUBMIT_CLAIMS("SubmitClaims",
            "Allows submission of insurance claims."),
    VIEW_NOTIFICATIONS("ViewNotifications",
            "Allows viewing of system notifications."),
    MANAGE_NOTIFICATIONS("ManageNotifications",
            "Allows configuration of notification settings."),
    VIEW_EVENT_STREAM("ViewEventStream",
            "Allows access to raw event stream for analytics.");



    private final String permissionName;

    private final String permissionDescription;

    PermissionEnum(String permissionName, String permissionDescription) {
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }
}

