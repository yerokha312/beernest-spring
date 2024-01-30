package com.neobis.yerokha.beernestspring.service.user;

import com.neobis.yerokha.beernestspring.entity.user.Role;
import com.neobis.yerokha.beernestspring.repository.user.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getUserRole() {
        try {
            return Collections.singleton(roleRepository.findByAuthority("CUSTOMER"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Role CUSTOMER does not exist");
        }
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByAuthority(roleName);
    }
}
