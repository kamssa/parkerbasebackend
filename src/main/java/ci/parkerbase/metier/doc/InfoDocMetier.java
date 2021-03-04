package ci.parkerbase.metier.doc;

import java.util.List;

import ci.parkerbase.entity.entreprise.InfoDoc;
import ci.parkerbase.metiers.Imetier;

public interface InfoDocMetier extends Imetier<InfoDoc, Long> {
	InfoDoc findByLibelle(String libelle);
	InfoDoc findByNomDoc(String nomDoc);
	List<InfoDoc> chercherInfoDocParMc(String mc);
	
	}
