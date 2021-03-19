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
import ci.parkerbase.entity.entreprise.Dossier;
import ci.parkerbase.exception.InvalideParkerBaseException;
import ci.parkerbase.metier.entreprise.IDepartementMetier;
import ci.parkerbase.metier.entreprise.IDossierMetier;
import ci.parkerbase.models.Reponse;
import ci.parkerbase.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DossierController {
	@Autowired
	private IDossierMetier dossierMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuperer Departement par identifiant
	private Reponse<Dossier> getDossierById(Long id) {
		Dossier dossier = null;

		try {
			dossier = dossierMetier.findById(id);
			if (dossier == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Dossier n'existe pas", id));
				new Reponse<Dossier>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Dossier>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Dossier>(0, null, dossier);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un dossier  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/dossier")
	public String creer(@RequestBody Dossier dossier) throws JsonProcessingException {
		Reponse<Dossier> reponse;
		System.out.println(dossier);
		try {

			Dossier d = dossierMetier.creer(dossier);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Dossier>(0, messages, d);

		} catch (InvalideParkerBaseException e) {

			reponse = new Reponse<Dossier>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/dossier")
	public String update(@RequestBody Dossier  modif) throws JsonProcessingException {

		Reponse<Dossier> reponse = null;
		Reponse<Dossier> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getDossierById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Dossier d = dossierMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", d.getId()));
				reponse = new Reponse<Dossier>(0, messages, d);
			} catch (InvalideParkerBaseException e) {

				reponse = new Reponse<Dossier>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Dossier n'existe pas"));
			reponse = new Reponse<Dossier>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// get all dossier
		@GetMapping("/dossier")
		public String findAll() throws JsonProcessingException {
			Reponse<List<Dossier>> reponse;
			try {
				List<Dossier> pers = dossierMetier.findAll();
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Dossier>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de departement enregistrés");
					reponse = new Reponse<List<Dossier>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un dossier par son identifiant
		@GetMapping("/dossier/{id}")
		public String getDossiersById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Dossier> reponse;
			try {
				Dossier db = dossierMetier.findById(id);
				reponse = new Reponse<Dossier>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		@GetMapping("/getDossierByidDep/{id}")
		public String getDepByDep(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<Dossier>> reponse;
			try {
				List<Dossier> pers = dossierMetier.getDossierByIdDep(id);
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Dossier>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de Dossier enregistrés");
					reponse = new Reponse<List<Dossier>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		@DeleteMapping("/dossier/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s  a ete supprime", true));

				reponse = new Reponse<Boolean>(0, messages, dossierMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
}
