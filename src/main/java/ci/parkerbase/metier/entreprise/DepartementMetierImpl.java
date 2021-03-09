package ci.parkerbase.metier.entreprise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.DepartementRepository;
import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.exception.InvalideParkerBaseException;

@Service
public class DepartementMetierImpl implements IDepartementMetier{
@Autowired
private DepartementRepository departementRepository;
	@Override
	public Departement creer(Departement entity) throws InvalideParkerBaseException {
		return departementRepository.save(entity);
	}

	@Override
	public Departement modifier(Departement entity) throws InvalideParkerBaseException {
		// TODO Auto-generated method stub
		return departementRepository.save(entity);
	}

	@Override
	public List<Departement> findAll() {
		// TODO Auto-generated method stub
		return departementRepository.findAll();
	}

	@Override
	public Departement findById(Long id) {
		// TODO Auto-generated method stub
		return departementRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		departementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Departement> entites) {
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
	public List<Departement> getDepByIdEntreprise(Long id) {
		// TODO Auto-generated method stub
		return departementRepository.getDepByIdEntreprise(id);
	}

	@Override
	public Departement findDepartementByLibelle(String libelle) {
		return departementRepository.getDepByLibelle(libelle);
	}

	
}
