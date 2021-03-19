package ci.parkerbase.metier.doc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import ci.parkerbase.entity.entreprise.InfoDoc;
import ci.parkerbase.metiers.Imetier;

public interface InfoDocMetier extends Imetier<InfoDoc, Long> {
	InfoDoc findByLibelle(String libelle);
	InfoDoc findByNomDoc(String nomDoc);
	List<InfoDoc> chercherInfoDocParMc(String mc, Long id);
	List<InfoDoc> chercherInfoDocParEntrepriseMc(String mc, long id);
	List<InfoDoc> getInfoDocParDep(long id);
	List<InfoDoc> getInfoDocParEntr(long id);
	
	}
