package ci.parkerbase.metier.admin;

import java.util.Optional;

import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;
import ci.parkerbase.metiers.Imetier;



public interface IRoleMetier  extends Imetier<Role, Long>{
	Optional<Role> findByName(RoleName roleName);

}
