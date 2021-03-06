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
		// TODO Auto-generated method stub
		return documentRepo.save(entity);
	}

	@Override
	public InfoDoc modifier(InfoDoc entity) {
		// TODO Auto-generated method stub
		return documentRepo.save(entity);
	}

	@Override
	public List<InfoDoc> findAll() {
		// TODO Auto-generated method stub
		return documentRepo.findAll();
	}

	@Override
	public InfoDoc findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<InfoDoc> entites) {
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
	public InfoDoc findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return documentRepo.findByLibelle(libelle).get();
	}

	@Override
	public InfoDoc findByNomDoc(String nomDoc) {
		// TODO Auto-generated method stub
		return documentRepo.findByNomDoc(nomDoc).get();
	}

	@Override
	public List<InfoDoc> chercherInfoDocParMc(String mc) {
		// TODO Auto-generated method stub
		return documentRepo.chercherInfoDocParMc(mc);
	}
	
	
}
