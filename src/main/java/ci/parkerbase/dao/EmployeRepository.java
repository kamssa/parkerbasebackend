package ci.parkerbase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.shared.Personne;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
	Optional<Employe> findByEmail(String login);
	@Query("select empl from Employe empl where empl.departement.entreprise.id=?1")
	List<Employe> getEmployeByIdEntreprise(long id);
}
