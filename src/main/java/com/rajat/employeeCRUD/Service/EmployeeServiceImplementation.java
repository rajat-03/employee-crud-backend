package com.rajat.employeeCRUD.Service;

import com.rajat.employeeCRUD.Entity.Employee;
import com.rajat.employeeCRUD.Repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService{

    final EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String createEmployee(Employee emp) {
        employeeRepository.save(emp);
        return "Employee added Successfully";
    }

    @Override
    public List<Employee> showAllEmployee() {
        return employeeRepository.findAll();
//        return employeeRepository.findByEmployeeNameLike("%" + "" + "%");
    }

    @Override
    public Employee getEmployeeById(int empId) {
        Optional<Employee> findEmployee = employeeRepository.findById(empId);
        return findEmployee.orElse(null);
    }

    @Override
    public String deleteEmployeeById(int empId) {
        if(employeeRepository.existsById(empId))
        {
            employeeRepository.deleteById(empId);
            return "Record Deleted";
        }
        return "Record Not Found!!";
    }

    @Override
    public String updateEmployee(Integer empId,Employee emp){
        Optional<Employee> existingEmployee = employeeRepository.findById(empId);
        if(existingEmployee.isPresent()){
            Employee updatedEmployee = existingEmployee.get();
            updatedEmployee.setEmployeeName(emp.getEmployeeName());
            updatedEmployee.setEmployeeEmail(emp.getEmployeeEmail());
            updatedEmployee.setEmployeeTitle(emp.getEmployeeTitle());
            updatedEmployee.setEmployeeDepartment(emp.getEmployeeDepartment());

            employeeRepository.save(updatedEmployee);
            return "Employee record updated successfully.";
        }
        return "Employee not found with ID: " + empId;
    }

    @Override
    public List<Employee> searchEmployeeByName(String empName){
        return employeeRepository.findByEmployeeNameLike("%" + empName +"%");
    }

    @Override
    public List<Employee> searchEmployeeByFullName(String empName) {
        return employeeRepository.findByEmployeeName(empName);
    }

    @Override
    public List<Employee> searchByAnyField(String name, String department, String title) {
        if (name != null && department != null && title != null) {
            return employeeRepository.findByEmployeeNameLikeOrEmployeeDepartmentLikeOrEmployeeTitleLike(
                    "%" + name + "%","%" +department+"%", "%"+title+"%");
        } else if (name != null) {
            return employeeRepository.findByEmployeeNameLike("%" + name + "%");
        } else if (department != null) {
            return employeeRepository.findByEmployeeDepartmentLike("%"+department+"%");
        } else if (title != null) {
            return employeeRepository.findByEmployeeTitleLike("%"+title+"%");
        } else {
            return employeeRepository.findAll();
        }
    }

    @Override
    public String addMultipleEmployees(List<Employee> bulkEmployees) {
        employeeRepository.saveAll(bulkEmployees);
        return "All Employees Added Successfully!!!";
    }

    @Override
    public Page<Employee> findAllEmployeeWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> searchEmployeesWithPagination(String name, String department,String title, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        if (name != null && department != null && title != null) {
            return employeeRepository.findByEmployeeNameLikeOrEmployeeDepartmentLikeOrEmployeeTitleLike(
                    "%" + name + "%","%" +department+"%", "%"+title+"%", pageable);
        }
        return employeeRepository.findByEmployeeNameLikeOrEmployeeDepartmentLikeOrEmployeeTitleLike(
                name,department,title,pageable);
    }


}
