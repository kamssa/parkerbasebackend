package ci.parkerbase.metier.entreprise;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.EmployeRepository;
import ci.parkerbase.dao.RoleRepository;
import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.shared.Personne;
import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;
import ci.parkerbase.exception.InvalideParkerBaseException;

@Service
public class EmployeMetierImpl implements IEmployeMetier{
@Autowired
private EmployeRepository employeRepository;
@Autowired
private RoleRepository roleRepository;
@Autowired
PasswordEncoder passwordEncoder;

	@Override
	public Employe creer(Employe entity) throws InvalideParkerBaseException {
		System.out.println("personne a enregistrer" + ":" + entity);
		if ((entity.getEmail().equals(null)) || (entity.getEmail() == "")) {
			throw new InvalideParkerBaseException("Le email ne peut etre null");
		}
		
		Optional<Employe> pers = null;

		pers = employeRepository.findByEmail(entity.getEmail());
		if (pers.isPresent()) {
			throw new InvalideParkerBaseException("Ce mail est deja utilise");
		}

      	entity.setPassword(passwordEncoder.encode(entity.getPassword()));
      	
		
		return employeRepository.save(entity);
	}

	@Override
	public Employe modifier(Employe modif) throws InvalideParkerBaseException {
		 String nomComplet = modif.getNom() + " " + modif.getPrenom();
		 modif.setNomComplet(nomComplet);
	     modif.setPassword(passwordEncoder.encode(modif.getPassword()));
         Employe empl=  employeRepository.findById(modif.getId()).get();
         Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYE).get();
         modif.setRoles(Collections.singleton(userRole));
         return employeRepository.save(modif);
}

	@Override
	public List<Employe> findAll() {
		// TODO Auto-generated method stub
		return employeRepository.findAll();
	}

	@Override
	public Employe findById(Long id) {
		// TODO Auto-generated method stub
		return employeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		employeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Employe> entites) {
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
	public List<Employe> getDepByIdEntreprise(Long id) {
		
		return employeRepository.getEmployeByIdEntreprise(id);
	}

	
}
