package com.empldata;

import java.util.Date;
import java.util.Objects;

public class Employee {
	private int empId;
    private String empName;
    private Date dob;
    private Date doj;
    private String designation;
    private String department;
	public Employee(int empId, String empName, Date dob, Date doj, String designation, String department) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.dob = dob;
		this.doj = doj;
		this.designation = designation;
		this.department = department;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee employee = (Employee) obj;
        return empName.equals(employee.empName) && dob.equals(employee.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empName, dob);
    }
    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", dob=" + dob +
                ", doj=" + doj +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
