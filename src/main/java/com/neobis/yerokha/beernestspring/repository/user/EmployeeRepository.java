package com.neobis.yerokha.beernestspring.repository.user;

import com.neobis.yerokha.beernestspring.entity.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
