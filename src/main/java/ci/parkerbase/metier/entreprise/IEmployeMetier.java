package ci.parkerbase.metier.entreprise;

import java.util.List;

import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.metiers.Imetier;

public interface IEmployeMetier extends Imetier<Employe, Long>{
	List<Employe> getDepByIdEntreprise(Long id);
}
