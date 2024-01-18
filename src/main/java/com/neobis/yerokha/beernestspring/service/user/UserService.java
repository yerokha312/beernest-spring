package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.dto.CreateCustomerDto;
import com.neobis.yerokha.beernestspring.dto.Credentials;
import com.neobis.yerokha.beernestspring.dto.UserDto;
import com.neobis.yerokha.beernestspring.entity.user.Customer;
import com.neobis.yerokha.beernestspring.entity.user.Employee;
import com.neobis.yerokha.beernestspring.entity.user.Person;
import com.neobis.yerokha.beernestspring.exception.CustomerIdDoesNotMatch;
import com.neobis.yerokha.beernestspring.exception.EmailAlreadyTakenException;
import com.neobis.yerokha.beernestspring.exception.InvalidCredentialsException;
import com.neobis.yerokha.beernestspring.exception.InvalidPasswordException;
import com.neobis.yerokha.beernestspring.exception.UserDoesNotExistException;
import com.neobis.yerokha.beernestspring.repository.user.CustomerRepository;
import com.neobis.yerokha.beernestspring.repository.user.EmployeeRepository;
import com.neobis.yerokha.beernestspring.util.CustomUserDetails;
import com.neobis.yerokha.beernestspring.util.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public UserDto registerCustomer(CreateCustomerDto dto) {
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

    public Page<UserDto> getAllCustomerDtos(Pageable pageable) {
        return customerRepository.findAll(pageable).map(CustomerMapper::mapToCustomerDto);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new UserDoesNotExistException("Customer with id: " + id + " not found"));

    }

    public UserDto getCustomerDtoById(Long id) {
        return CustomerMapper.mapToCustomerDto(
                customerRepository.findById(id)
                        .orElseThrow(() ->
                                new UserDoesNotExistException("Customer with id: " + id + " not found.")));
    }

    public UserDto updateCustomer(UserDto userDto) {
        Customer dbCustomer = getCustomerById(userDto.id());

        CustomerMapper.mapToCustomerEntity(userDto, dbCustomer);

        customerRepository.save(dbCustomer);

        return userDto;
    }

    public void setActiveFalse(Long id, Credentials credentials) {
        Customer customer = getCustomerById(id);

        if (!customer.getEmail().equals(credentials.username())) {
            throw new InvalidCredentialsException("Customer's email does not match");
        }


        if (!passwordEncoder.matches(credentials.password(), customer.getPassword())) {
            throw new InvalidPasswordException("Password is incorrect");
        }

        customer.setIsActive(false);

        customerRepository.save(customer);
    }

    public void setActiveTrue(Credentials credentials) {
        Customer customer = getCustomerByEmail(credentials.username());

        if (!passwordEncoder.matches(credentials.password(), customer.getPassword())) {
            throw new InvalidPasswordException("Password is incorrect");
        }

        customer.setIsActive(true);

        customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UserDoesNotExistException("Customer with email: " + email + " not found"));
    }

    public void deleteCustomerById(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserDoesNotExistException("Customer with id: " + id + " not found.");
        }
    }

    public UserDto getCustomerDto(Long id) {
        return CustomerMapper.mapToCustomerDto(customerRepository
                .findById(id).orElseThrow(() ->
                        new UserDoesNotExistException("Customer with id: " + id + " not found.")));
    }

    public UserDto updateProfileInformation(Long id, UserDto userDto) {
        if (!id.equals(userDto.id())) {
            throw new CustomerIdDoesNotMatch("Id must match");
        }
        Customer dbCustomer = getCustomerById(id);
        CustomerMapper.mapToCustomerEntity(userDto, dbCustomer);
        customerRepository.save(dbCustomer);

        return userDto;
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
        return new CustomUserDetails(
                person.getEmail(),
                person.getPassword(),
                getAuthorities(person),
                person.getId()
        );
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
