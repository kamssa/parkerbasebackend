package ci.parkerbase.metier.doc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.InfoDocRepository;
import ci.parkerbase.entity.entreprise.InfoDoc;





@Service
public class InfoDocMetierImpl implements InfoDocMetier {
    @Autowired
    private InfoDocRepository documentRepo;

	@Override
	public InfoDoc creer(InfoDoc entity) {
		return documentRepo.save(entity);
	}

	@Override
	public InfoDoc modifier(InfoDoc entity) {
		return documentRepo.save(entity);
	}

	@Override
	public List<InfoDoc> findAll() {
		return documentRepo.findAll();
	}

	@Override
	public InfoDoc findById(Long id) {
		return documentRepo.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		documentRepo.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<InfoDoc> entites) {
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

	@Override
	public InfoDoc findByLibelle(String libelle) {
		return documentRepo.findByLibelle(libelle).get();
	}

	@Override
	public InfoDoc findByNomDoc(String nomDoc) {
		return documentRepo.findByNomDoc(nomDoc).get();
	}

	@Override
	public List<InfoDoc> chercherInfoDocParMc(String mc, Long id) {
		return documentRepo.chercherInfoDocParMc(mc, id);
	}
	
	
}
