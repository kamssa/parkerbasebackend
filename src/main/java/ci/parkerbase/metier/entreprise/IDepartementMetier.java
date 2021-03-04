package ci.parkerbase.metier.entreprise;

import java.util.List;

import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.metiers.Imetier;

public interface IDepartementMetier extends Imetier<Departement, Long>{
	List<Departement> getDepByIdEntreprise(Long id);

}
