package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.CustomerDto;
import com.neobis.yerokha.beernestspring.entity.user.ContactInfo;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.entity.user.Employee;
import com.neobis.yerokha.beernestspring.entity.user.Person;
import com.neobis.yerokha.beernestspring.exception.EmailAlreadyTakenException;
import com.neobis.yerokha.beernestspring.exception.InvalidPasswordException;
import com.neobis.yerokha.beernestspring.exception.UserDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.repository.user.EmployeeRepository;
import com.neobis.yerokha.beernestspring.util.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public static final int PAGE_SIZE = 10;

    @Autowired
    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDto registerCustomer(CreateCustomerDto dto) {
        Customer customer = CustomerMapper.mapToCustomerEntity(dto);
        customer.setRegistrationTime(LocalDateTime.now());
        customer.setAuthorities(roleService.getUserRole());
        customer.setPassword(passwordEncoder.encode(dto.password()));


        try {
            return CustomerMapper.mapToCustomerDto(customerRepository.save(customer));
        } catch (Exception e) {
            throw new EmailAlreadyTakenException("The email provided is already taken");
        }
    }

    public Page<CustomerDto> getAllCustomerDtos(Pageable pageable) {
        return customerRepository.findAll(pageable).map(CustomerMapper::mapToCustomerDto);
    }

    private Customer getCustomerById(Long id) {

        return customerRepository.findById(id).orElseThrow(() ->
                new UserDoesNotExistException("Customer with id: " + id + " not found"));

    }

    public CustomerDto getCustomerDtoById(Long id) {
        return CustomerMapper.mapToCustomerDto(
                customerRepository.findById(id)
                        .orElseThrow(() ->
                                new UserDoesNotExistException("Customer with id: " + id + " not found.")));
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer dbCustomer = getCustomerById(customerDto.id());

        CustomerMapper.mapToCustomerEntity(customerDto, dbCustomer);

        customerRepository.save(dbCustomer);

        return customerDto;
    }

    public ContactInfo addContacts(Long id, ContactInfo contactInfo) {
        Customer customer = getCustomerById(id);
        customer.getContactInfo().add(contactInfo);
        customerRepository.save(customer);
        return contactInfo;
    }

    public void setActiveFalse(Map<String, String> body) {
        Customer customer = getCustomerByEmail(body.get("email"));

        if (!Objects.equals(customer.getPassword(), body.get("password"))) {
            throw new InvalidPasswordException("Password is incorrect");
        }

        customer.setIsActive(false);

        customerRepository.save(customer);
    }

    private Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UserDoesNotExistException("User with email: " + email + " not found"));
    }

    public void setActiveTrue(Map<String, String> body) {
        Customer customer = getCustomerByEmail(body.get("email"));

        if (!Objects.equals(customer.getPassword(), body.get("password"))) {
            throw new InvalidPasswordException("Password is incorrect");
        }

        customer.setIsActive(true);

        customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserDoesNotExistException("Customer with id: " + id + " not found.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(username);

        if (customer.isPresent()) {
            return buildUserDetails(customer.get());
        }

        Optional<Employee> employee = employeeRepository.findByEmail(username);

        if (employee.isPresent()) {
            return buildUserDetails(employee.get());
        }

        throw new UsernameNotFoundException("User not found with username: " + username);

    }

    private UserDetails buildUserDetails(Person person) {
        return User.withUsername(person.getEmail())
                .password(person.getPassword())
                .authorities(getAuthorities(person))
                .build();
    }

    private Set<GrantedAuthority> getAuthorities(Person person) {
        if (person instanceof Employee) {
            return ((Employee) person).getAuthorities().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toSet());
        }

        if (person instanceof Customer) {
            return ((Customer) person).getAuthorities().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toSet());
        }

        throw new IllegalArgumentException("Unsupported person type: " + person.getClass());
    }
}
