package ci.parkerbase.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.entity.entreprise.Employe;
import ci.parkerbase.entity.entreprise.Entreprise;
import ci.parkerbase.exception.InvalideParkerBaseException;
import ci.parkerbase.metier.entreprise.IEntrepriseMetier;
import ci.parkerbase.models.Reponse;
import ci.parkerbase.utilitaire.Static;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class EntrepriseController {
	@Autowired
	private IEntrepriseMetier entrepriseMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper employe par identifiant
	private Reponse<Entreprise> getEntrepriseByIdEntreprise(Long id) {
		Entreprise entreprise = null;

		try {
			entreprise = entrepriseMetier.findById(id);
			if (entreprise == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("entreprise n'existe pas", id));
				new Reponse<Entreprise>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Entreprise>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Entreprise>(0, null, entreprise);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une entreprise  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/entreprise")
	public String creer(@RequestBody Entreprise entreprise) throws JsonProcessingException {
		Reponse<Entreprise> reponse;
		System.out.println(entreprise);
		try {

			Entreprise t1 = entrepriseMetier.creer(entreprise);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Entreprise>(0, messages, t1);

		} catch (InvalideParkerBaseException e) {

			reponse = new Reponse<Entreprise>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/entreprise")
	public String update(@RequestBody Entreprise  modif) throws JsonProcessingException {

		Reponse<Entreprise> reponse = null;
		Reponse<Entreprise> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getEntrepriseByIdEntreprise(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Entreprise employe = entrepriseMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", employe.getId()));
				reponse = new Reponse<Entreprise>(0, messages, employe);
			} catch (InvalideParkerBaseException e) {

				reponse = new Reponse<Entreprise>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Entreprise n'existe pas"));
			reponse = new Reponse<Entreprise>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	
	// obtenir une location par son identifiant
	@GetMapping("/entreprise/{id}")
	public String getEntrepriseById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<Entreprise> reponse;
		try {
			Entreprise db = entrepriseMetier.findById(id);
			reponse = new Reponse<Entreprise>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer un achat
	@DeleteMapping("/entreprise/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, entrepriseMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	// get all Entreprise
			@GetMapping("/entreprise")
			public String findAll() throws JsonProcessingException {
				Reponse<List<Entreprise>> reponse;
				try {
					List<Entreprise> pers = entrepriseMetier.findAll();
					if (!pers.isEmpty()) {
						reponse = new Reponse<List<Entreprise>>(0, null, pers);
					} else {
						List<String> messages = new ArrayList<>();
						messages.add("Pas de Employe enregistrées");
						reponse = new Reponse<List<Entreprise>>(1, messages, new ArrayList<>());
					}
	
				} catch (Exception e) {
					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
	
			}
}
