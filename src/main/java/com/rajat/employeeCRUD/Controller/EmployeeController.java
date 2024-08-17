package com.rajat.employeeCRUD.Controller;

import com.rajat.employeeCRUD.DTO.PaginatedEmployeeResponseDTO;
import com.rajat.employeeCRUD.Entity.Employee;
import com.rajat.employeeCRUD.Service.EmployeeServiceImplementation;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EmployeeController {

    @RequestMapping("/")
    public String Home(){
        return "Welcome To The Employee Crud Demo Project";
    }

    final EmployeeServiceImplementation employeeService;

    public EmployeeController(EmployeeServiceImplementation employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<String> addOrUpdateEmployee(@RequestBody Employee emp){
        String status = employeeService.createEmployee(emp);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping(path = "/allEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> allEmployee = employeeService.showAllEmployee();
        return new ResponseEntity<>(allEmployee, HttpStatus.OK);
    }

    @GetMapping("/employeeById/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer empId){
        Employee emp = employeeService.getEmployeeById(empId);
        return new ResponseEntity<>(emp,HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteEmployee/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer empId){
        String status = employeeService.deleteEmployeeById(empId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/updateEmployee/{empId}")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee emp, @PathVariable Integer empId){
        String status = employeeService.updateEmployee(empId,emp);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getEmployeeListByName(@PathParam("empName") String empName){
        List<Employee> employees = employeeService.searchEmployeeByName(empName);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/getEmployeeByName")
    public ResponseEntity<List<Employee>> getEmployeeByFullName(@PathParam("empFullName") String empFullName){
        List<Employee> employee = employeeService.searchEmployeeByFullName(empFullName);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/getByAnyField")
    public ResponseEntity<List<Employee>> getByAnyField(@PathParam("empName") String empName,
                                                        @PathParam("empDepartment") String empDepartment,
                                                        @PathParam("empTitle") String empTitle){
        List<Employee> employeeList = employeeService.searchByAnyField(empName, empDepartment, empTitle);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PostMapping("/addMultipleEmployees")
    public ResponseEntity<String> addMultipleEmployees(@RequestBody List<Employee> multipleEmployees){
        String message = employeeService.addMultipleEmployees(multipleEmployees);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/employeesPaginated")
    public ResponseEntity<List<Employee>> getEmployeesWithPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "5") int size){
        Page<Employee> employeesPaginatedResult= employeeService.findAllEmployeeWithPagination(page,size);
        List<Employee> allEmployees = employeesPaginatedResult.getContent();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/employeesDtoPaginated")
    public ResponseEntity<PaginatedEmployeeResponseDTO> getEmployeesWithDTO(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "5") int size){
        Page<Employee> employeesPaginatedResult = employeeService.findAllEmployeeWithPagination(page,size);
        PaginatedEmployeeResponseDTO responseDTO = new PaginatedEmployeeResponseDTO(
                employeesPaginatedResult.getTotalElements(),
                employeesPaginatedResult.getContent()
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/searchEmployeeWithPaginated")
    public ResponseEntity<PaginatedEmployeeResponseDTO> searchEmployeesWithPagination(@RequestParam(value = "searchTerm", required = false) String name,
                                                                                      @RequestParam(value = "searchTerm", required = false) String department,
                                                                                      @RequestParam(value = "searchTerm", required = false) String title,
                                                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                      @RequestParam(value = "size", defaultValue = "5")int size){

        Page<Employee> employeePage= employeeService.searchEmployeesWithPagination(name,department,title,page,size);

        PaginatedEmployeeResponseDTO response = new PaginatedEmployeeResponseDTO(
                employeePage.getTotalElements(),
                employeePage.getContent()
        );
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }



}
