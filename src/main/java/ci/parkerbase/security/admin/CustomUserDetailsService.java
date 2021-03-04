package ci.parkerbase.security.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ci.parkerbase.dao.PersonneReposiory;
import ci.parkerbase.entity.shared.Personne;



@Service
public class CustomUserDetailsService implements UserDetailsService{
	

	    @Autowired
	    PersonneReposiory personneReposiory;

	    @Override
	    @Transactional
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {
	        // Let people login with either username or email
	        Personne p = personneReposiory.findByEmail(email)
	                .orElseThrow(() -> 
	                        new UsernameNotFoundException("Utilisateur non disponible : " + email)
	        );

	        return UserPrincipal.create(p);
	    }

	    // This method is used by JWTAuthenticationFilter
	    @Transactional
	    public UserDetails loadUserById(Long id) {
	        Personne p = personneReposiory.findById(id).orElseThrow(
	            () -> new UsernameNotFoundException("Utilisateur non disponible avec id : " + id)
	        );

	        return UserPrincipal.create(p);
	    }
}
