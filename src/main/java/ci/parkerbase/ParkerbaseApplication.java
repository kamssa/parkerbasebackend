package ci.parkerbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ci.parkerbase.dao.RoleRepository;
import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;


@SpringBootApplication
public class ParkerbaseApplication implements CommandLineRunner{
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(ParkerbaseApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		/*this.roleRepository.save(new Role(RoleName.ROLE_ADMIN));
		this.roleRepository.save(new Role(RoleName.ROLE_EMPLOYE));
		this.roleRepository.save(new Role(RoleName.ROLE_MANAGER));
		this.roleRepository.save(new Role(RoleName.ROLE_USER));*/
		
	}


}
