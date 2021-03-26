package ci.parkerbase.metier.entreprise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.DossierRepository;
import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.entity.entreprise.Dossier;
import ci.parkerbase.exception.InvalideParkerBaseException;

@Service
public class DossierMetierImpl implements IDossierMetier{
@Autowired
private DossierRepository dossierRepository;
	@Override
	public Dossier creer(Dossier entity) throws InvalideParkerBaseException {
		if ((entity.getLibelle().equals(null)) || (entity.getLibelle() == "")) {
			throw new InvalideParkerBaseException("Le libelle ne peut etre null");
		}
		
		Optional<Dossier> dos = null;

        dos = dossierRepository.findByLibelle(entity.getLibelle());
		if (dos.isPresent()) {
			throw new InvalideParkerBaseException("Ce libelle est deja utilise");
		}

		return dossierRepository.save(entity);
	}

	@Override
	public Dossier modifier(Dossier entity) throws InvalideParkerBaseException {
		return dossierRepository.save(entity);
	}

	@Override
	public List<Dossier> findAll() {
		return dossierRepository.findAll();
	}

	@Override
	public Dossier findById(Long id) {
		return dossierRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
    dossierRepository.deleteById(id);
	return true;
	}

	@Override
	public boolean supprimer(List<Dossier> entites) {
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
	public List<Dossier> getDossierByIdDep(long id) {
		// TODO Auto-generated method stub
		return dossierRepository.getDossierByIdDep(id);
	}

	@Override
	public Dossier getDossierById(long id) {
		// TODO Auto-generated method stub
		return dossierRepository.getDossierById(id);
	}

}
