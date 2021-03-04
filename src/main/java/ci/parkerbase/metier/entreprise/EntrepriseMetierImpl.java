package ci.parkerbase.metier.entreprise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.EntrepriseRepository;
import ci.parkerbase.entity.entreprise.Entreprise;
import ci.parkerbase.exception.InvalideParkerBaseException;

@Service
public class EntrepriseMetierImpl implements IEntrepriseMetier{
@Autowired
private EntrepriseRepository entrepriseRepository;
	@Override
	public Entreprise creer(Entreprise entity) throws InvalideParkerBaseException {
		// TODO Auto-generated method stub
		return entrepriseRepository.save(entity);
	}

	@Override
	public Entreprise modifier(Entreprise entity) throws InvalideParkerBaseException {
		// TODO Auto-generated method stub
		return entrepriseRepository.save(entity);
	}

	@Override
	public List<Entreprise> findAll() {
		// TODO Auto-generated method stub
		return entrepriseRepository.findAll();
	}

	@Override
	public Entreprise findById(Long id) {
		// TODO Auto-generated method stub
		return entrepriseRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Entreprise> entites) {
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

}
