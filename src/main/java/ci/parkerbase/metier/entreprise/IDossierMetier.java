package ci.parkerbase.metier.entreprise;

import java.util.List;

import ci.parkerbase.entity.entreprise.Dossier;
import ci.parkerbase.metiers.Imetier;

public interface IDossierMetier extends Imetier<Dossier, Long>{
	List<Dossier> getDossierByIdDep(long id);
	Dossier getDossierById(long id);
}
