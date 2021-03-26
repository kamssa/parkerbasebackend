package ci.parkerbase.metier.entreprise;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ci.parkerbase.dao.ManagerRepository;
import ci.parkerbase.dao.PersonneReposiory;
import ci.parkerbase.dao.RoleRepository;
import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.entreprise.Manager;
import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;
import ci.parkerbase.exception.InvalideParkerBaseException;

@Service
public class ManagerMetierImpl implements IManagerMetier {
@Autowired
private ManagerRepository managerRepository; 
@Autowired
private PersonneReposiory personneReposiory; 
@Autowired
private RoleRepository roleRepository;
@Autowired
PasswordEncoder passwordEncoder;
	@Override
	public Manager creer(Manager entity) throws InvalideParkerBaseException {
		
		return managerRepository.save(entity);
	}

	@Override
	public Manager modifier(Manager modif) throws InvalideParkerBaseException {
		 String nomComplet = modif.getNom() + " " + modif.getPrenom();
		 modif.setNomComplet(nomComplet); 
		modif.setPassword(passwordEncoder.encode(modif.getPassword()));
         Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER).get();
         modif.setRoles(Collections.singleton(userRole));
         return managerRepository.save(modif);
	}

	@Override
	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	@Override
	public Manager findById(Long id) {
		return managerRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		managerRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Manager> entites) {
		return false;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	@Override
	public Boolean existsByPseudo(String pseudo) {
		return null;
	}

	@Override
	public Boolean existsByEmail(String email) {
		return null;
	}
	// verifier les passwords encode
		@Override
		public boolean matches(String login, String oldPassword) {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			Manager manage = managerRepository.getManageByEmail(login);
			return encoder.matches(oldPassword, manage.getPassword());
		}
}
