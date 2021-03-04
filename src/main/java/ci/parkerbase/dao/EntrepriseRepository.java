package ci.parkerbase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.parkerbase.entity.entreprise.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>{

}
