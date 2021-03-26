package ci.parkerbase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import ci.parkerbase.entity.entreprise.Dossier;

public interface DossierRepository extends JpaRepository<Dossier, Long>{
	@Query("select d from Dossier d where d.departement.id=?1")
	List<Dossier> getDossierByIdDep(long id);
	@Query("select d from Dossier d where d.id=?1")
	Dossier getDossierById(long id);
	Optional<Dossier> findByLibelle(String libelle);

}
