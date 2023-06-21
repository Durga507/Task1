package com.empltest;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;

import com.empldata.Employee;
import com.empldata.EmployeeRecordsApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;



import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;

public class EmployeeRecordsApplicationTest {


    @Test
    void testDedupEmployeeRecords() {
        // Create test data
        Employee emp1 = new Employee(1, "John Doe", new Date(), new Date(), "Consultant", "HR");
        Employee emp2 = new Employee(2, "Jane Smith", new Date(), new Date(), "Manager", "Development");
        Employee emp3 = new Employee(1, "John Doe", new Date(), new Date(), "Consultant", "HR");

        // Mock the employeeRecords list
        EmployeeRecordsApplication app = new EmployeeRecordsApplication();
        app.addEmployee(emp1);
        app.addEmployee(emp2);
        app.addEmployee(emp3);

        // Perform deduplication
        List<Employee> dedupedRecords = app.dedupEmployeeRecords();

        // Assert the deduped records
        assertEquals(2, dedupedRecords.size());
        assertEquals(emp1, dedupedRecords.get(0));
        assertEquals(emp2, dedupedRecords.get(1));
    }

    @Test
    void testSortEmployeesByNameAndDOJ() {
        // Create test data
        Employee emp1 = new Employee(1, "John Doe", new Date(2022, 5, 1), new Date(2021, 2, 1), "Consultant", "HR");
        Employee emp2 = new Employee(2, "Jane Smith", new Date(2021, 1, 1), new Date(2020, 10, 1), "Manager", "Development");
        Employee emp3 = new Employee(3, "Alex Johnson", new Date(2022, 1, 1), new Date(2021, 12, 1), "Consultant", "Finance");

        // Mock the employeeRecords list
        EmployeeRecordsApplication app = new EmployeeRecordsApplication();
        app.addEmployee(emp1);
        app.addEmployee(emp2);
        app.addEmployee(emp3);

        // Perform sorting
        List<Employee> sortedRecords = app.sortEmployeesByNameAndDOJ();

        // Assert the sorted records
        assertEquals(3, sortedRecords.size());
        assertEquals(emp3, sortedRecords.get(0));
        assertEquals(emp2, sortedRecords.get(1));
        assertEquals(emp1, sortedRecords.get(2));
    }

    @Test
    void testCountEmployeesByDepartment() {
        // Create test data
        Employee emp1 = new Employee(1, "John Doe", new Date(), new Date(), "Consultant", "HR");
        Employee emp2 = new Employee(2, "Jane Smith", new Date(), new Date(), "Manager", "Development");
        Employee emp3 = new Employee(3, "Alex Johnson", new Date(), new Date(), "Consultant", "HR");

        // Mock the employeeRecords list
        EmployeeRecordsApplication app = new EmployeeRecordsApplication();
        app.addEmployee(emp1);
        app.addEmployee(emp2);
        app.addEmployee(emp3);

        // Perform grouping
        Map<String, Long> employeesByDepartment = app.countEmployeesByDepartment();

        // Assert the count
        assertEquals(2, employeesByDepartment.size());
        assertEquals(2, employeesByDepartment.get("HR"));
        assertEquals(1, employeesByDepartment.get("Development"));
    }

    @Test
    void testSearchEmployees() {
        // Create test data
        Employee emp1 = new Employee(1, "John Doe", new Date(), new Date(), "Consultant", "HR");
        Employee emp2 = new Employee(2, "Jane Smith", new Date(), new Date(), "Manager", "Development");
        Employee emp3 = new Employee(3, "Alex Johnson", new Date(), new Date(), "Consultant", "HR");

        // Mock the employeeRecords list
        EmployeeRecordsApplication app = new EmployeeRecordsApplication();
        app.addEmployee(emp1);
        app.addEmployee(emp2);
        app.addEmployee(emp3);

        // Perform search
        List<Employee> searchResults = app.searchEmployees("John Doe", null, "HR");

        // Assert the search results
        assertEquals(1, searchResults.size());
        assertEquals(emp1, searchResults.get(0));
    }

    @Test
    void testReadEmployeeRecordsFromCSV() throws IOException, ParseException {
        // Create a mock CSV file
        String csvFilePath = "test.csv";
        String csvContent = "1,John Doe,2021-01-01,2022-02-02,Consultant,HR\n" +
                "2,Jane Smith,2020-03-03,2023-04-04,Manager,Development";
        TestUtils.createCSVFile(csvFilePath, csvContent);

        // Create the employee records application
        EmployeeRecordsApplication app = new EmployeeRecordsApplication();

        // Perform CSV parsing
        app.readEmployeeRecordsFromCSV(csvFilePath);

        // Verify the records
        List<Employee> employeeRecords = app.dedupEmployeeRecords();
        assertEquals(2, employeeRecords.size());
        assertEquals(1, employeeRecords.get(0).getEmpId());
        assertEquals("John Doe", employeeRecords.get(0).getEmpName());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"), employeeRecords.get(0).getDob());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-02"), employeeRecords.get(0).getDoj());
        assertEquals("Consultant", employeeRecords.get(0).getDesignation());
        assertEquals("HR", employeeRecords.get(0).getDepartment());
        assertEquals(2, employeeRecords.get(1).getEmpId());
        assertEquals("Jane Smith", employeeRecords.get(1).getEmpName());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-03"), employeeRecords.get(1).getDob());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-04"), employeeRecords.get(1).getDoj());
        assertEquals("Manager", employeeRecords.get(1).getDesignation());
        assertEquals("Development", employeeRecords.get(1).getDepartment());

        // Clean up the test file
        TestUtils.deleteFile(csvFilePath);
    }
}

class TestUtils {
    static void createCSVFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }

    static void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

