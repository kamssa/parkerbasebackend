package ci.parkerbase.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.parkerbase.entity.entreprise.Manager;

public interface ManagerRepository  extends JpaRepository<Manager, Long>{
	@Query("select manage from Manager manage where manage.email=?1")
	Manager getManageByEmail(String email);
}
