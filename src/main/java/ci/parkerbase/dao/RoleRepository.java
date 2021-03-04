package ci.parkerbase.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;



public interface RoleRepository  extends JpaRepository<Role, Long>{
	Optional<Role> findByName(RoleName roleName);
}
