package com.rajat.employeeCRUD.DTO;

import com.rajat.employeeCRUD.Entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedEmployeeResponseDTO {
    private long totalElements;
    private List<Employee> content;
}
