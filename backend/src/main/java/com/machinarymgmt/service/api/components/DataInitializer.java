package com.machinarymgmt.service.api.components;

import com.machinarymgmt.service.api.data.*;
import com.machinarymgmt.service.api.data.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EquipmentCategoryRepository equipmentCategoryRepository;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final EquipmentRepository equipmentRepository;
    private final EmployeeAssignmentRepository employeeAssignmentRepository;
    private final EquipmentUtilizationRepository equipmentUtilizationRepository;
    private final IncidentReportRepository incidentReportRepository;
    private final ItemRepository itemRepository;
    private final MachineryMaintenanceLogRepository machineryMaintenanceLogRepository;
    private final MaintenancePartsUsedRepository maintenancePartsUsedRepository;
    private final MaintenanceReadingRepository maintenanceReadingRepository;
    private final MastAnchorageDetailsRepository mastAnchorageDetailsRepository;
    private final MaterialsConsumptionTransactionRepository materialsConsumptionTransactionRepository;
    private final OvertimeReportRepository overtimeReportRepository;
    private final PettyCashTransactionRepository pettyCashTransactionRepository;
    private final StockStatementRepository stockStatementRepository;
//    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing database with sample data...");

        // Clear existing data
        clearData();

        // Initialize data in order of dependencies
        initializeDepartments();
        initializeDesignations();
        initializeEmployees();
        initializeProjects();
        initializeEquipmentCategories();
        initializeMakes();
        initializeModels();
        initializeEquipment();
        initializeEmployeeAssignments();
        initializeItems();
        initializeEquipmentUtilizations();
        initializeIncidentReports();
        initializeMachineryMaintenanceLogs();
        initializeMaintenanceReadings();
        initializeMaintenancePartsUsed();
        initializeMastAnchorageDetails();
        initializeMaterialsConsumptionTransactions();
        initializeOvertimeReports();
        initializePettyCashTransactions();
        initializeStockStatements();
//        initializeUsers();

        log.info("Database initialization completed successfully!");
    }

    private void clearData() {
        // Clear data in reverse order of dependencies
        stockStatementRepository.deleteAll();
        pettyCashTransactionRepository.deleteAll();
        overtimeReportRepository.deleteAll();
        materialsConsumptionTransactionRepository.deleteAll();
        mastAnchorageDetailsRepository.deleteAll();
        maintenancePartsUsedRepository.deleteAll();
        maintenanceReadingRepository.deleteAll();
        machineryMaintenanceLogRepository.deleteAll();
        incidentReportRepository.deleteAll();
        equipmentUtilizationRepository.deleteAll();
        employeeAssignmentRepository.deleteAll();
        equipmentRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
        equipmentCategoryRepository.deleteAll();
        itemRepository.deleteAll();
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
        designationRepository.deleteAll();
        departmentRepository.deleteAll();
//        userRepository.deleteAll();
    }

    private void initializeDepartments() {
        log.info("Initializing Departments...");
        Department dept1 = Department.builder()
                .name("Engineering")
                .description("Engineering Department")
                .build();

        Department dept2 = Department.builder()
                .name("Operations")
                .description("Operations Department")
                .build();

        departmentRepository.saveAll(Arrays.asList(dept1, dept2));
    }

    private void initializeDesignations() {
        log.info("Initializing Designations...");
        Designation designation1 = Designation.builder()
                .name("Project Manager")
                .description("Manages projects and teams")
                .build();

        Designation designation2 = Designation.builder()
                .name("Equipment Operator")
                .description("Operates heavy machinery")
                .build();

        designationRepository.saveAll(Arrays.asList(designation1, designation2));
    }

    private void initializeEmployees() {
        log.info("Initializing Employees...");
        Department dept1 = departmentRepository.findByName("Engineering");
        Department dept2 = departmentRepository.findByName("Operations");

        Designation designation1 = designationRepository.findByName("Project Manager");
        Designation designation2 = designationRepository.findByName("Equipment Operator");

        Employee employee1 = Employee.builder()
                .name("John Doe")
                .department(dept1)
                .designation(designation1)
                .remarks("Senior employee")
                .build();

        Employee employee2 = Employee.builder()
                .name("Jane Smith")
                .department(dept2)
                .designation(designation2)
                .remarks("Experienced operator")
                .build();

        employeeRepository.saveAll(Arrays.asList(employee1, employee2));
    }

    private void initializeProjects() {
        log.info("Initializing Projects...");
        Project project1 = Project.builder()
                .name("Highway Construction")
                .location("North Region")
                .description("Construction of 50km highway")
                .startDate("2023-01-01")
                .endDate("2024-12-31")
                .status("In Progress")
                .build();

        Project project2 = Project.builder()
                .name("Bridge Renovation")
                .location("South Region")
                .description("Renovation of old bridge")
                .startDate("2023-03-15")
                .endDate("2023-12-31")
                .status("In Progress")
                .build();

        projectRepository.saveAll(Arrays.asList(project1, project2));
    }

    private void initializeEquipmentCategories() {
        log.info("Initializing Equipment Categories...");
        EquipmentCategory category1 = EquipmentCategory.builder()
                .name("Heavy Machinery")
                .build();

        EquipmentCategory category2 = EquipmentCategory.builder()
                .name("Light Equipment")
                .build();

        equipmentCategoryRepository.saveAll(Arrays.asList(category1, category2));
    }

    private void initializeMakes() {
        log.info("Initializing Makes...");
        Make make1 = Make.builder()
                .name("Caterpillar")
                .build();

        Make make2 = Make.builder()
                .name("Komatsu")
                .build();

        makeRepository.saveAll(Arrays.asList(make1, make2));
    }

    private void initializeModels() {
        log.info("Initializing Models...");
        Make make1 = makeRepository.findByName("Caterpillar");
        Make make2 = makeRepository.findByName("Komatsu");

        Model model1 = Model.builder()
                .name("CAT-320")
                .make(make1)
                .build();

        Model model2 = Model.builder()
                .name("PC-200")
                .make(make2)
                .build();

        modelRepository.saveAll(Arrays.asList(model1, model2));
    }

    private void initializeEquipment() {
        log.info("Initializing Equipment...");
        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        EquipmentCategory category1 = equipmentCategoryRepository.findByName("Heavy Machinery");
        EquipmentCategory category2 = equipmentCategoryRepository.findByName("Light Equipment");

        Model model1 = modelRepository.findByName("CAT-320");
        Model model2 = modelRepository.findByName("PC-200");

        Equipment equipment1 = Equipment.builder()
                .name("Excavator 1")
                .project(project1)
                .category(category1)
                .model(model1)
                .assetCode("EX-001")
                .yearOfManufacture(2020)
                .build();

        Equipment equipment2 = Equipment.builder()
                .name("Bulldozer 1")
                .project(project2)
                .category(category1)
                .model(model2)
                .assetCode("BD-001")
                .yearOfManufacture(2019)
                .build();

        equipmentRepository.saveAll(Arrays.asList(equipment1, equipment2));
    }

    private void initializeEmployeeAssignments() {
        log.info("Initializing Employee Assignments...");
        Employee employee1 = employeeRepository.findByName("John Doe");
        Employee employee2 = employeeRepository.findByName("Jane Smith");

        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        EmployeeAssignment assignment1 = EmployeeAssignment.builder()
                .employee(employee1)
                .project(project1)
                .equipment(equipment1)
                .joiningDate(LocalDate.of(2023, 1, 15))
                .build();

        EmployeeAssignment assignment2 = EmployeeAssignment.builder()
                .employee(employee2)
                .project(project2)
                .equipment(equipment2)
                .joiningDate(LocalDate.of(2023, 3, 20))
                .build();

        employeeAssignmentRepository.saveAll(Arrays.asList(assignment1, assignment2));
    }

    private void initializeItems() {
        log.info("Initializing Items...");
        Item item1 = Item.builder()
                .code("MAT-001")
                .description("Cement")
                .uom("Bags")
                .type(Item.ItemType.MATERIAL)
                .build();

        Item item2 = Item.builder()
                .code("SPR-001")
                .description("Hydraulic Oil")
                .uom("Liters")
                .type(Item.ItemType.SPARE)
                .build();

        itemRepository.saveAll(Arrays.asList(item1, item2));
    }

    private void initializeEquipmentUtilizations() {
        log.info("Initializing Equipment Utilizations...");
        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        EquipmentUtilization utilization1 = EquipmentUtilization.builder()
                .equipment(equipment1)
                .project(project1)
                .month(4)
                .year(2023)
                .targetHoursKms(new BigDecimal("200.00"))
                .startingHoursKms(new BigDecimal("1000.00"))
                .closingHoursKms(new BigDecimal("1150.00"))
                .breakdownHoursKms(new BigDecimal("10.00"))
                .dieselConsumedLtrs(new BigDecimal("500.00"))
                .avgFuelConsumption(new BigDecimal("3.33"))
                .availabilityHours(new BigDecimal("190.00"))
                .utilizationPercentage(new BigDecimal("95.00"))
                .remarks("Good performance")
                .build();

        EquipmentUtilization utilization2 = EquipmentUtilization.builder()
                .equipment(equipment2)
                .project(project2)
                .month(4)
                .year(2023)
                .targetHoursKms(new BigDecimal("180.00"))
                .startingHoursKms(new BigDecimal("800.00"))
                .closingHoursKms(new BigDecimal("920.00"))
                .breakdownHoursKms(new BigDecimal("15.00"))
                .dieselConsumedLtrs(new BigDecimal("450.00"))
                .avgFuelConsumption(new BigDecimal("3.75"))
                .availabilityHours(new BigDecimal("165.00"))
                .utilizationPercentage(new BigDecimal("91.67"))
                .remarks("Satisfactory performance")
                .build();

        equipmentUtilizationRepository.saveAll(Arrays.asList(utilization1, utilization2));
    }

    private void initializeIncidentReports() {
        log.info("Initializing Incident Reports...");
        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        IncidentReport incident1 = IncidentReport.builder()
                .equipment(equipment1)
                .project(project1)
                .incidentType(IncidentReport.IncidentType.TYPE1)
                .incidentDetails("Hydraulic system failure")
                .incidentDate(LocalDate.of(2023, 4, 10))
                .actionTaken("Replaced hydraulic hose")
                .estimatedCompletionDate(LocalDate.of(2023, 4, 12))
                .closedDate(LocalDate.of(2023, 4, 11))
                .status(IncidentReport.Status.CLOSED)
                .build();

        IncidentReport incident2 = IncidentReport.builder()
                .equipment(equipment2)
                .project(project2)
                .incidentType(IncidentReport.IncidentType.TYPE2)
                .incidentDetails("Engine overheating")
                .incidentDate(LocalDate.of(2023, 4, 15))
                .actionTaken("Cooling system repair in progress")
                .estimatedCompletionDate(LocalDate.of(2023, 4, 18))
                .status(IncidentReport.Status.IN_PROGRESS)
                .build();

        incidentReportRepository.saveAll(Arrays.asList(incident1, incident2));
    }

    private void initializeMachineryMaintenanceLogs() {
        log.info("Initializing Machinery Maintenance Logs...");
        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        MachineryMaintenanceLog log1 = MachineryMaintenanceLog.builder()
                .equipment(equipment1)
                .date(LocalDate.of(2023, 4, 5))
                .startReading(1000)
                .closeReading(1050)
                .serviceHours(250)
                .serviceDate(LocalDate.of(2023, 4, 5))
                .balanceForService(200)
                .purposeActivities("Regular maintenance")
                .typeOfMaintenance("Preventive")
                .operatorName("John Doe")
                .operatorSignature("JD")
                .maintenanceSignature("MT")
                .feedback("Good condition")
                .remarks("No issues found")
                .build();

        MachineryMaintenanceLog log2 = MachineryMaintenanceLog.builder()
                .equipment(equipment2)
                .date(LocalDate.of(2023, 4, 8))
                .startReading(800)
                .closeReading(850)
                .serviceHours(200)
                .serviceDate(LocalDate.of(2023, 4, 8))
                .balanceForService(150)
                .purposeActivities("Oil change")
                .typeOfMaintenance("Preventive")
                .operatorName("Jane Smith")
                .operatorSignature("JS")
                .maintenanceSignature("MT")
                .feedback("Satisfactory")
                .remarks("Oil filter replaced")
                .build();

        machineryMaintenanceLogRepository.saveAll(Arrays.asList(log1, log2));
    }

    private void initializeMaintenanceReadings() {
        log.info("Initializing Maintenance Readings...");
        MachineryMaintenanceLog log1 = machineryMaintenanceLogRepository.findAll().get(0);
        MachineryMaintenanceLog log2 = machineryMaintenanceLogRepository.findAll().get(1);

        MaintenanceReading reading1 = MaintenanceReading.builder()
                .maintenanceLog(log1)
                .oilPressure(new BigDecimal("45.5"))
                .engineTemperature(new BigDecimal("85.0"))
                .airPressure(new BigDecimal("30.0"))
                .hydraulicTemperature(new BigDecimal("65.0"))
                .hsdUsed(new BigDecimal("25.0"))
                .engineOil(new BigDecimal("5.0"))
                .hydraulicOil(new BigDecimal("10.0"))
                .gearOil(new BigDecimal("3.0"))
                .greaseUsed(new BigDecimal("2.0"))
                .build();

        MaintenanceReading reading2 = MaintenanceReading.builder()
                .maintenanceLog(log2)
                .oilPressure(new BigDecimal("42.0"))
                .engineTemperature(new BigDecimal("82.0"))
                .airPressure(new BigDecimal("28.5"))
                .hydraulicTemperature(new BigDecimal("62.0"))
                .hsdUsed(new BigDecimal("22.0"))
                .engineOil(new BigDecimal("4.5"))
                .hydraulicOil(new BigDecimal("9.0"))
                .gearOil(new BigDecimal("2.5"))
                .greaseUsed(new BigDecimal("1.5"))
                .build();

        maintenanceReadingRepository.saveAll(Arrays.asList(reading1, reading2));
    }

    private void initializeMaintenancePartsUsed() {
        log.info("Initializing Maintenance Parts Used...");
        MachineryMaintenanceLog log1 = machineryMaintenanceLogRepository.findAll().get(0);
        MachineryMaintenanceLog log2 = machineryMaintenanceLogRepository.findAll().get(1);

        Item item1 = itemRepository.findByCode("MAT-001");
        Item item2 = itemRepository.findByCode("SPR-001");

        MaintenancePartsUsed partsUsed1 = MaintenancePartsUsed.builder()
                .maintenanceLog(log1)
                .item(item1)
                .quantity(new BigDecimal("2.0"))
                .build();

        MaintenancePartsUsed partsUsed2 = MaintenancePartsUsed.builder()
                .maintenanceLog(log2)
                .item(item2)
                .quantity(new BigDecimal("5.0"))
                .build();

        maintenancePartsUsedRepository.saveAll(Arrays.asList(partsUsed1, partsUsed2));
    }

    private void initializeMastAnchorageDetails() {
        log.info("Initializing Mast Anchorage Details...");
        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        MastAnchorageDetails details1 = MastAnchorageDetails.builder()
                .project(project1)
                .equipment(equipment1)
                .status("Active")
                .location("North Tower")
                .mastAvailableAtSite(5)
                .mastFixedAtSite(3)
                .mastIdleAtSite(2)
                .totalMastRequirement(6)
                .anchorageAtSite(8)
                .anchorageFixedAtSite(6)
                .anchorageIdleAtSite(2)
                .totalAnchorageRequirement(10)
                .presentHeightOfHoist("30m")
                .presentBuildingHeight("40m")
                .totalBuildingHeight("60m")
                .remarks("On schedule")
                .build();

        MastAnchorageDetails details2 = MastAnchorageDetails.builder()
                .project(project2)
                .equipment(equipment2)
                .status("Active")
                .location("South Tower")
                .mastAvailableAtSite(4)
                .mastFixedAtSite(2)
                .mastIdleAtSite(2)
                .totalMastRequirement(5)
                .anchorageAtSite(6)
                .anchorageFixedAtSite(4)
                .anchorageIdleAtSite(2)
                .totalAnchorageRequirement(8)
                .presentHeightOfHoist("25m")
                .presentBuildingHeight("35m")
                .totalBuildingHeight("50m")
                .remarks("Slight delay")
                .build();

        mastAnchorageDetailsRepository.saveAll(Arrays.asList(details1, details2));
    }

    private void initializeMaterialsConsumptionTransactions() {
        log.info("Initializing Materials Consumption Transactions...");
        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        Item item1 = itemRepository.findByCode("MAT-001");
        Item item2 = itemRepository.findByCode("SPR-001");

        MaterialsConsumptionTransaction transaction1 = MaterialsConsumptionTransaction.builder()
                .project(project1)
                .issueDate(LocalDate.of(2023, 4, 10))
                .equipment(equipment1)
                .item(item1)
                .quantity(50)
                .costPerUnit(new BigDecimal("350.00"))
                .totalCost(new BigDecimal("17500.00"))
                .remarks("For foundation work")
                .createdAt(LocalDateTime.now())
                .build();

        MaterialsConsumptionTransaction transaction2 = MaterialsConsumptionTransaction.builder()
                .project(project2)
                .issueDate(LocalDate.of(2023, 4, 12))
                .equipment(equipment2)
                .item(item2)
                .quantity(20)
                .costPerUnit(new BigDecimal("500.00"))
                .totalCost(new BigDecimal("10000.00"))
                .remarks("For maintenance")
                .createdAt(LocalDateTime.now())
                .build();

        materialsConsumptionTransactionRepository.saveAll(Arrays.asList(transaction1, transaction2));
    }

    private void initializeOvertimeReports() {
        log.info("Initializing Overtime Reports...");
        Employee employee1 = employeeRepository.findByName("John Doe");
        Employee employee2 = employeeRepository.findByName("Jane Smith");

        OvertimeReport report1 = OvertimeReport.builder()
                .date(LocalDate.of(2023, 4, 15))
                .employee(employee1)
                .presentDays(22)
                .otHours(new BigDecimal("15.5"))
                .remarks("Project deadline")
                .createdAt(LocalDateTime.now())
                .build();

        OvertimeReport report2 = OvertimeReport.builder()
                .date(LocalDate.of(2023, 4, 15))
                .employee(employee2)
                .presentDays(21)
                .otHours(new BigDecimal("12.0"))
                .remarks("Equipment maintenance")
                .createdAt(LocalDateTime.now())
                .build();

        overtimeReportRepository.saveAll(Arrays.asList(report1, report2));
    }

    private void initializePettyCashTransactions() {
        log.info("Initializing Petty Cash Transactions...");
        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        Item item1 = itemRepository.findByCode("MAT-001");
        Item item2 = itemRepository.findByCode("SPR-001");

        PettyCashTransaction transaction1 = PettyCashTransaction.builder()
                .project(project1)
                .reportDate(LocalDate.of(2023, 4, 18))
                .equipment(equipment1)
                .item(item1)
                .quantity(5)
                .rate(new BigDecimal("350.00"))
                .cumulativeTotalAmount(new BigDecimal("1750.00"))
                .amountSpent(new BigDecimal("1750.00"))
                .purposeJustification("Emergency repair")
                .remarks("Approved by site manager")
                .build();

        PettyCashTransaction transaction2 = PettyCashTransaction.builder()
                .project(project2)
                .reportDate(LocalDate.of(2023, 4, 19))
                .equipment(equipment2)
                .item(item2)
                .quantity(3)
                .rate(new BigDecimal("500.00"))
                .cumulativeTotalAmount(new BigDecimal("1500.00"))
                .amountSpent(new BigDecimal("1500.00"))
                .purposeJustification("Urgent maintenance")
                .remarks("Approved by project manager")
                .build();

        pettyCashTransactionRepository.saveAll(Arrays.asList(transaction1, transaction2));
    }

    private void initializeStockStatements() {
        log.info("Initializing Stock Statements...");
        Project project1 = projectRepository.findByName("Highway Construction");
        Project project2 = projectRepository.findByName("Bridge Renovation");

        Equipment equipment1 = equipmentRepository.findByAssetCode("EX-001");
        Equipment equipment2 = equipmentRepository.findByAssetCode("BD-001");

        Item item1 = itemRepository.findByCode("MAT-001");
        Item item2 = itemRepository.findByCode("SPR-001");

        StockStatement statement1 = StockStatement.builder()
                .project(project1)
                .item(item1)
                .equipment(equipment1)
                .month(4)
                .year(2023)
                .balance(new BigDecimal("100.00"))
                .landedRate(new BigDecimal("350.00"))
                .landedValue(new BigDecimal("35000.00"))
                .lastReceiptOn(LocalDate.of(2023, 4, 5))
                .lastIssueOn(LocalDate.of(2023, 4, 10))
                .build();

        StockStatement statement2 = StockStatement.builder()
                .project(project2)
                .item(item2)
                .equipment(equipment2)
                .month(4)
                .year(2023)
                .balance(new BigDecimal("50.00"))
                .landedRate(new BigDecimal("500.00"))
                .landedValue(new BigDecimal("25000.00"))
                .lastReceiptOn(LocalDate.of(2023, 4, 8))
                .lastIssueOn(LocalDate.of(2023, 4, 12))
                .build();

        stockStatementRepository.saveAll(Arrays.asList(statement1, statement2));
    }

    // private void initializeUsers() {
    //     log.info("Initializing Users...");
    //     User user1 = User.builder()
    //             .username("admin")
    //             .password("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG") // password: password
    //             .email("admin@example.com")
    //             .role("ADMIN")
    //             .active(true)
    //             .build();

    //     User user2 = User.builder()
    //             .username("user")
    //             .password("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG") // password: password
    //             .email("user@example.com")
    //             .role("USER")
    //             .active(true)
    //             .build();

    //     userRepository.saveAll(Arrays.asList(user1, user2));
    // }
}