package com.rajat.employeeCRUD.Service;

import com.rajat.employeeCRUD.Entity.Employee;
import org.springframework.data.domain.Page;


import java.util.List;

public interface EmployeeService {
    public String createEmployee(Employee emp);

    public String updateEmployee(Integer empId, Employee emp);

    public List<Employee> showAllEmployee();

    public Employee getEmployeeById(int empId);

    public String deleteEmployeeById(int empId);

    // if we pass empty string then all employees will be shown
    public List<Employee> searchEmployeeByName(String empName);

    public List<Employee> searchEmployeeByFullName(String empName);

    public List<Employee> searchByAnyField(String employeeName, String employeeDepartment, String employeeTitle);

    public String addMultipleEmployees(List<Employee> bulkEmployees);

    public Page<Employee> findAllEmployeeWithPagination(int page, int size);

    public Page<Employee> searchEmployeesWithPagination(String name,String department, String title, int page, int size);
}
