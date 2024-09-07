package com.rajat.employeeCRUD.Repository;

import com.rajat.employeeCRUD.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Serializable> {

//    jpa provide common queries like these, we just have to specify the variable on which we want to find
    // findBy is given by jpa and EmployeeName is the variable name on which we want to find
    List<Employee> findByEmployeeName(String employeeName);

    List<Employee> findByEmployeeNameLike(String employeeName);

    List<Employee> findByEmployeeDepartmentLike(String employeeDepartment);

    List<Employee> findByEmployeeTitleLike(String employeeTitle);

    List<Employee> findByEmployeeNameLikeOrEmployeeDepartmentLikeOrEmployeeTitleLike(
            String employeeName, String employeeDepartment, String employeeTitle);

    // Method to find employees with pagination
//    Page<Employee> findAll(Pageable pageable);

    // Method to search by name, department, or title with pagination
    Page<Employee> findByEmployeeNameLikeOrEmployeeDepartmentLikeOrEmployeeTitleLike(
            String name, String department, String title, Pageable pageable);

    // JPQL:  Java Persistence Query Language
    // allows us to write our custom query similar to sql query, eg:
    @Query("SELECT e from Employee e WHERE " +
            "LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR "+
            "LOWER(e.employeeDepartment) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR "+
            "LOWER(e.employeeTitle) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    Page<Employee> searchEmployees(String searchKey, Pageable pageable);


}
