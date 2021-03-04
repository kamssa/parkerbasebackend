package ci.parkerbase.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.parkerbase.entity.admin.Admin;



@Repository
public interface UserRepository extends JpaRepository<Admin, Long>{
	
}
