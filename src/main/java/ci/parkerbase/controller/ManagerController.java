package ci.parkerbase.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.entreprise.Manager;
import ci.parkerbase.entity.shared.Personne;
import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;
import ci.parkerbase.exception.InvalideParkerBaseException;
import ci.parkerbase.metier.admin.IPersonneMetier;
import ci.parkerbase.metier.admin.IRoleMetier;
import ci.parkerbase.metier.entreprise.IManagerMetier;
import ci.parkerbase.models.JwtAuthenticationResponse;
import ci.parkerbase.models.Reponse;
import ci.parkerbase.security.admin.JwtTokenProvider;
import ci.parkerbase.utilitaire.Static;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class ManagerController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IPersonneMetier personneMetier;

	@Autowired
	IRoleMetier roleMetier;
	@Autowired
	private IManagerMetier managerMetier;

	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;

	@PostMapping("/signinManage")
	public String authenticateUser(@Valid @RequestBody Personne loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
		return jsonMapper.writeValueAsString(reponse);

	}

	@PostMapping("/signupManage")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String creaeUser(@RequestBody Personne signUpRequest) throws Exception {
		System.out.println("Voir le type de la personne recuperée:" + signUpRequest.getType());

		Reponse<Personne> reponse = null;
		Personne personne = null;
		try {

			Role userRole = roleMetier.findByName(RoleName.ROLE_MANAGER).get();
			signUpRequest.setRoles(Collections.singleton(userRole));

			personne = personneMetier.creer(signUpRequest);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  a été créé avec succès", personne.getId()));
			reponse = new Reponse<Personne>(0, messages, personne);

		} catch (InvalideParkerBaseException e) {
			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/manager")
	public String update(@RequestBody Personne  modif) throws JsonProcessingException {
		System.out.println("modif recupere1:"+ modif);
		Reponse<Personne> reponse = null;
		Reponse<Personne> reponsePersModif = null;
		Personne p = personneMetier.findById(modif.getId());
		if (p != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				
				Personne manager = personneMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", manager.getId()));
				reponse = new Reponse<Personne>(0, messages, manager);
			} catch (InvalideParkerBaseException e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Employe n'existe pas"));
			reponse = new Reponse<Personne>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir un manager par son id
		@GetMapping("/manager/{id}")
		public String getById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Manager> reponse;
			try {
				Manager db = managerMetier.findById(id);
				reponse = new Reponse<Manager>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un manager par son email
				@GetMapping("/personne/{email}")
				public String getById(@PathVariable String email) throws JsonProcessingException {
					Reponse<Personne> reponse;
					try {
						Personne personne = personneMetier.findByEmail(email).get();
						reponse = new Reponse<Personne>(0, null, personne);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
				// obtenir une personne par son id
				@GetMapping("/getPersonneById/{id}")
				public String getPersonneById(@PathVariable Long id) throws JsonProcessingException {
					Reponse<Personne> reponse;
					try {
						Personne personne = personneMetier.findById(id);
						reponse = new Reponse<Personne>(0, null, personne);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
				// obtenir une personne par son id
				@GetMapping("/getManageBId/{id}")
				public String getManageBId(@PathVariable Long id) throws JsonProcessingException {
					Reponse<Manager> reponse;
					try {
						Manager personne = managerMetier.findById(id);
						reponse = new Reponse<Manager>(0, null, personne);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
				// get all employe
				@GetMapping("/manager")
				public String findAll() throws JsonProcessingException {
					Reponse<List<Manager>> reponse;
					try {
						List<Manager> pers = managerMetier.findAll();
						if (!pers.isEmpty()) {
							reponse = new Reponse<List<Manager>>(0, null, pers);
						} else {
							List<String> messages = new ArrayList<>();
							messages.add("Pas de departement enregistrés");
							reponse = new Reponse<List<Manager>>(1, messages, new ArrayList<>());
						}

					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}

				@DeleteMapping("/manager/{id}")
				public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Boolean> reponse = null;

					try {

						List<String> messages = new ArrayList<>();
						messages.add(String.format(" %s  a ete supprime", true));

						reponse = new Reponse<Boolean>(0, messages, personneMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
					}

					return jsonMapper.writeValueAsString(reponse);
				}
}
