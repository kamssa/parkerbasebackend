package ci.parkerbase.metier.admin;

import java.util.Optional;

import ci.parkerbase.entity.shared.Personne;
import ci.parkerbase.metiers.Imetier;

public interface IPersonneMetier extends Imetier<Personne, Long>{
	Optional<Personne> findByEmail(String login);
}
