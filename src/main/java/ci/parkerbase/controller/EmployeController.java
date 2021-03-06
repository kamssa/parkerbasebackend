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
import ci.parkerbase.entity.shared.Personne;
import ci.parkerbase.entity.shared.Role;
import ci.parkerbase.entity.shared.RoleName;
import ci.parkerbase.exception.InvalideParkerBaseException;
import ci.parkerbase.metier.admin.IPersonneMetier;
import ci.parkerbase.metier.admin.IRoleMetier;
import ci.parkerbase.metier.entreprise.IEmployeMetier;
import ci.parkerbase.models.JwtAuthenticationResponse;
import ci.parkerbase.models.Reponse;
import ci.parkerbase.security.admin.JwtTokenProvider;
import ci.parkerbase.utilitaire.Static;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class EmployeController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IPersonneMetier personneMetier;

	@Autowired
	IRoleMetier roleMetier;
	@Autowired
	IEmployeMetier employeMetier;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuper employe par identifiant
		private Reponse<Employe> getEmployeById(Long id) {
			Employe employe = null;

			try {
				employe = employeMetier.findById(id);
				if (employe == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("employe n'existe pas", id));
					new Reponse<Employe>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Employe>(0, null, employe);
		}

	
	@PostMapping("/signinEmploye")
	public String authenticateUser(@Valid @RequestBody Personne loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
		return jsonMapper.writeValueAsString(reponse);

	}

	@PostMapping("/signupEmpl")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createUser(@RequestBody Employe signUpRequest) throws Exception {
		System.out.println("Voir le type de la personne recuperée:" + signUpRequest.getType());

		Reponse<Employe> reponse = null;
		Employe employe = null;
		try {

			Role userRole = roleMetier.findByName(RoleName.ROLE_EMPLOYE).get();
			signUpRequest.setRoles(Collections.singleton(userRole));

			employe = employeMetier.creer(signUpRequest);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  a été créé avec succès", employe.getId()));
			reponse = new Reponse<Employe>(0, messages, employe);

		} catch (InvalideParkerBaseException e) {
			reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/employe")
	public String update(@RequestBody Employe  modif) throws JsonProcessingException {
		System.out.println("modif recupere1:"+ modif);
		Reponse<Employe> reponse = null;
		Reponse<Employe> reponsePersModif = null;
		reponsePersModif = getEmployeById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				
				Employe empl = employeMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", empl.getId()));
				reponse = new Reponse<Employe>(0, messages, empl);
			} catch (InvalideParkerBaseException e) {

				reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Employe n'existe pas"));
			reponse = new Reponse<Employe>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir un employe par son id
	@GetMapping("/employe/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<Personne> reponse;
		try {
			Personne db = employeMetier.findById(id);
			reponse = new Reponse<Personne>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/getEmployeByidEntreprise/{id}")
	public String getEmplEntreprise(@PathVariable Long id) throws JsonProcessingException {
		Reponse<List<Employe>> reponse;
		try {
			List<Employe> empl = employeMetier.getDepByIdEntreprise(id);
			if (!empl.isEmpty()) {
				reponse = new Reponse<List<Employe>>(0, null, empl);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de Employe enregistrés");
				reponse = new Reponse<List<Employe>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// get all employe
	@GetMapping("/employe")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Employe>> reponse;
		try {
			List<Employe> pers = employeMetier.findAll();
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Employe>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de departement enregistrés");
				reponse = new Reponse<List<Employe>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/employe/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, employeMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
}
