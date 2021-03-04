package ci.parkerbase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.parkerbase.entity.entreprise.Manager;

public interface ManagerRepository  extends JpaRepository<Manager, Long>{

}
