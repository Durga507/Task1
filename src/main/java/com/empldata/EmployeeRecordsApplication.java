package com.empldata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class EmployeeRecordsApplication {
	private List<Employee> employeeRecords;

    public EmployeeRecordsApplication() {
        employeeRecords = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeeRecords.add(employee);
    }

    public List<Employee> dedupEmployeeRecords() {
        Set<Employee> dedupedRecords = new HashSet<>(employeeRecords);
        return new ArrayList<>(dedupedRecords);
    }

//    public List<Employee> sortEmployeesByNameAndDOJ() {
//        List<Employee> sortedRecords = new ArrayList<>(employeeRecords);
//        sortedRecords.sort(Comparator.comparing(Employee::getEmpName).thenComparing(Employee::getDOJ));
//        return sortedRecords;
//    }
//
//    public List<Employee> sortEmployeesByNameAndDOJ() {
//        List<Employee> sortedRecords = new ArrayList<>(employeeRecords);
//        Comparator<Employee> byName = Comparator.comparing(Employee::getEmpName);
//        Comparator<Employee> byDOJ = Comparator.comparing(Employee::getDOJ, Comparator.nullsLast(Date::compareTo));
//        sortedRecords.sort(byName.thenComparing(byDOJ));
//        return sortedRecords;
//    }

    public List<Employee> sortEmployeesByNameAndDOJ() {
        List<Employee> sortedRecords = new ArrayList<>(employeeRecords);
        Comparator<Employee> byName = Comparator.comparing(Employee::getEmpName);
        Comparator<Employee> byDOJ = Comparator.comparing(Employee::getDoj, Comparator.nullsLast(Comparator.naturalOrder()));
        sortedRecords.sort(byName.thenComparing(byDOJ));
        return sortedRecords;
    }


    public Map<String, Long> countEmployeesByDepartment() {
        return employeeRecords.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }

    public List<Employee> searchEmployees(String name, String designation, String department) {
        return employeeRecords.stream()
                .filter(employee -> (name == null || employee.getEmpName().equals(name))
                        && (designation == null || employee.getDesignation().equals(designation))
                        && (department == null || employee.getDepartment().equals(department)))
                .collect(Collectors.toList());
    }


	public static void main(String[] args) throws IOException, ParseException{
		// TODO Auto-generated method stub
		EmployeeRecordsApplication app = new EmployeeRecordsApplication();
		app.readEmployeeRecordsFromCSV("C:\\Users\\dbheemun\\eclipse-workspace\\assignments\\EmpData\\src\\main\\resources\\empdata.csv");

        // Dedup employee records
        List<Employee> dedupedRecords = app.dedupEmployeeRecords();
        System.out.println("Deduped Employee Records:");
        dedupedRecords.forEach(System.out::println);

        // Sort employees by name and DOJ
        List<Employee> sortedRecords = app.sortEmployeesByNameAndDOJ();
        System.out.println("Sorted Employee Records:");
        sortedRecords.forEach(System.out::println);

        // Count employees by department
        Map<String, Long> employeesByDepartment = app.countEmployeesByDepartment();
        System.out.println("Employees by Department:");
        employeesByDepartment.forEach((department, count) -> System.out.println(department + ": " + count));
        String searchName = "John Doe";
        String searchDesignation = "Consultant";
        String searchDepartment = "HR";
        List<Employee> searchResults = app.searchEmployees(searchName, searchDesignation, searchDepartment);
        System.out.println("Search Results:");
        searchResults.forEach(System.out::println);
    }
	public void readEmployeeRecordsFromCSV(String filePath) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
        	//boolean isFirstLine = true; 
            String[] line;
            while ((line = reader.readNext()) != null) {
                int empId = Integer.parseInt(line[0]);
                String empName = line[1];
                Date dob = dateFormat.parse(line[2]);
                Date doj = dateFormat.parse(line[3]);
                String designation = line[4];
                String department = line[5];
                Employee employee = new Employee(empId, empName, dob, doj, designation, department);
                addEmployee(employee);
            }
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            e.printStackTrace();
        } catch (IOException e) {
            // Handle I/O exception
            e.printStackTrace();
        } catch (CsvValidationException e) {
            // Handle CSV validation exception
            e.printStackTrace();
        } catch (ParseException e) {
            // Handle date parsing exception
            e.printStackTrace();
        }
	}

}
