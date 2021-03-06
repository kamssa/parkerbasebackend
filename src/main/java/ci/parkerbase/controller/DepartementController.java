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
import ci.parkerbase.exception.InvalideParkerBaseException;
import ci.parkerbase.metier.entreprise.IDepartementMetier;
import ci.parkerbase.models.Reponse;
import ci.parkerbase.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DepartementController {
	@Autowired
	private IDepartementMetier departementMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper Departement par identifiant
	private Reponse<Departement> getDepartementById(Long id) {
		Departement departement = null;

		try {
			departement = departementMetier.findById(id);
			if (departement == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Departement n'existe pas", id));
				new Reponse<Departement>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Departement>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Departement>(0, null, departement);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un departement  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/departement")
	public String creer(@RequestBody Departement departement) throws JsonProcessingException {
		Reponse<Departement> reponse;
		System.out.println(departement);
		try {

			Departement d = departementMetier.creer(departement);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Departement>(0, messages, d);

		} catch (InvalideParkerBaseException e) {

			reponse = new Reponse<Departement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/departement")
	public String update(@RequestBody Departement  modif) throws JsonProcessingException {

		Reponse<Departement> reponse = null;
		Reponse<Departement> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getDepartementById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Departement dep = departementMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", dep.getId()));
				reponse = new Reponse<Departement>(0, messages, dep);
			} catch (InvalideParkerBaseException e) {

				reponse = new Reponse<Departement>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Entreprise n'existe pas"));
			reponse = new Reponse<Departement>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// get all departement
		@GetMapping("/departement")
		public String findAll() throws JsonProcessingException {
			Reponse<List<Departement>> reponse;
			try {
				List<Departement> pers = departementMetier.findAll();
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Departement>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de departement enregistrés");
					reponse = new Reponse<List<Departement>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un departement par son identifiant
		@GetMapping("/departement/{id}")
		public String getEntrepriseById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Departement> reponse;
			try {
				Departement db = departementMetier.findById(id);
				reponse = new Reponse<Departement>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		@GetMapping("/getDepartementByidEntreprise/{id}")
		public String getDepByEntreprise(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<Departement>> reponse;
			try {
				List<Departement> pers = departementMetier.getDepByIdEntreprise(id);
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Departement>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de departement enregistrés");
					reponse = new Reponse<List<Departement>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		@DeleteMapping("/departement/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s  a ete supprime", true));

				reponse = new Reponse<Boolean>(0, messages, departementMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
}
