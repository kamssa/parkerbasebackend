package ci.parkerbase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.parkerbase.entity.admin.Admin;
import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.shared.Personne;

public interface PersonneReposiory extends JpaRepository<Personne, Long> {
	// recupere une personne par son nom
	Optional<Personne> findByNom(String nom);

	// liste des personne de la base a partir de id
	List<Personne> findByIdIn(List<Long> userIds);

	Optional<Personne> findByEmail(String login);

	// verifier si une personne existe a partir de son login
	Boolean existsByEmail(String email);

	

}
