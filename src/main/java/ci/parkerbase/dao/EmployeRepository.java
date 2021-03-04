package ci.parkerbase.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.shared.Personne;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
	Optional<Employe> findByEmail(String login);
}
