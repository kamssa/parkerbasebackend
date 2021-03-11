package ci.parkerbase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ci.parkerbase.entity.entreprise.InfoDoc;



@Repository
public interface InfoDocRepository extends JpaRepository<InfoDoc, Long>{
	Optional<InfoDoc> findByLibelle(String libelle);
	Optional<InfoDoc> findByNomDoc(String nomDoc);
	@Query("select infoDoc from InfoDoc infoDoc where infoDoc.description LIKE %?1% AND infoDoc.departement.id=?2")
	List<InfoDoc> chercherInfoDocParMc(String description, long id);
	@Query("select infoDoc from InfoDoc infoDoc where infoDoc.description LIKE %?1% AND infoDoc.idEntreprise=?2")
	List<InfoDoc> chercherInfoDocParEntrepriseMc(String description, long id);
}
