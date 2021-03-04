package ci.parkerbase.metier.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.RoleRepository;
import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;




@Service
public class RoleMetierImpl implements IRoleMetier{
@Autowired
private RoleRepository roleRepository;

@Override
public Role creer(Role entity) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Role modifier(Role entity) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Role> findAll() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Role findById(Long id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean supprimer(List<Role> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Boolean existsByPseudo(String pseudo) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Boolean existsByEmail(String email) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Optional<Role> findByName(RoleName roleName) {
	// TODO Auto-generated method stub
	return roleRepository.findByName(roleName);
}
	
}
