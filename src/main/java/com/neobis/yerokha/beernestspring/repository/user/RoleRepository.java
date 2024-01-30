package com.neobis.yerokha.beernestspring.repository.user;

import com.neobis.yerokha.beernestspring.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByAuthority(String authority);
}
