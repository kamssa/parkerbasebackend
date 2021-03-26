package ci.parkerbase.metier.entreprise;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ci.parkerbase.dao.ManagerRepository;
import ci.parkerbase.entity.entreprise.Manager;
import ci.parkerbase.exception.InvalideParkerBaseException;

@Service
public class ManagerMetierImpl implements IManagerMetier {
@Autowired
private ManagerRepository managerRepository; 
	@Override
	public Manager creer(Manager entity) throws InvalideParkerBaseException {
		
		return managerRepository.save(entity);
	}

	@Override
	public Manager modifier(Manager entity) throws InvalideParkerBaseException {
		return managerRepository.save(entity);
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

}
