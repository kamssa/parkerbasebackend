package ci.parkerbase.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.parkerbase.entity.entreprise.Departement;
import ci.parkerbase.entity.entreprise.Dossier;
import ci.parkerbase.entity.entreprise.InfoDoc;
import ci.parkerbase.exception.InvalideParkerBaseException;
import ci.parkerbase.metier.doc.ImageMetier;
import ci.parkerbase.metier.doc.InfoDocMetier;
import ci.parkerbase.metier.entreprise.IDepartementMetier;
import ci.parkerbase.metier.entreprise.IDossierMetier;
import ci.parkerbase.metier.entreprise.S3ServiceMetier;
import ci.parkerbase.models.Reponse;
import ci.parkerbase.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class InfoDocController {
	@Autowired
	InfoDocMetier documentMetier;
	@Autowired
	IDepartementMetier departementMetier;
	@Autowired
	IDossierMetier dossierMetier;
	@Autowired
	ImageMetier imageMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	@Value("${dir.images}")
	private String archives;
	@Autowired
	S3ServiceMetier s3Services;

	private Reponse<InfoDoc> getInfoDocById(final Long id) {
		InfoDoc document = null;
		try {
			document = documentMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<InfoDoc>(1, Static.getErreursForException(e), null);
		}
		if (document == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le document n'exste pas"));
			return new Reponse<InfoDoc>(2, messages, null);
		}
		return new Reponse<InfoDoc>(0, null, document);

	}
	// creer une document

	@PostMapping("/infoDoc")
	public String create(@RequestBody InfoDoc document) throws JsonProcessingException {
		Reponse<InfoDoc> reponse = null;

		try {
			InfoDoc doc = documentMetier.creer(document);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Element crée avec succes", doc.getLibelle()));
			reponse = new Reponse<InfoDoc>(0, messages, doc);
		} catch (Exception e) {

			reponse = new Reponse<InfoDoc>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/infoDoc")
	public String update(@RequestBody InfoDoc  modif) throws JsonProcessingException {

		Reponse<InfoDoc> reponse = null;
		Reponse<InfoDoc> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getInfoDocById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				InfoDoc infoDoc = documentMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", infoDoc.getId()));
				reponse = new Reponse<InfoDoc>(0, messages, infoDoc);
			} catch (InvalideParkerBaseException e) {

				reponse = new Reponse<InfoDoc>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Entreprise n'existe pas"));
			reponse = new Reponse<InfoDoc>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
//////// ramener tous les documents//////////////////////////////////////////////////
	@GetMapping("/infoDoc")
	public String findAll() throws JsonProcessingException {

		Reponse<List<InfoDoc>> reponse;
		try {
			List<InfoDoc> documents = documentMetier.findAll();

			if (!documents.isEmpty()) {
				reponse = new Reponse<List<InfoDoc>>(0, null, documents);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de infoDoc enregistrés");
				reponse = new Reponse<List<InfoDoc>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<InfoDoc>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// obtenir un infoDoc par son identifiant
	@GetMapping("/infoDoc/{id}")
	public String getDocById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<InfoDoc> reponse;
		try {
			InfoDoc db = documentMetier.findById(id);
			reponse = new Reponse<InfoDoc>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// obtenir un infoDoc par son identifiant
		@GetMapping("/infoDocParDep/{id}")
		public String getDocByIdDep(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<InfoDoc>> reponse;
			try {
				List<InfoDoc> pers = documentMetier.getInfoDocParDep(id);
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<InfoDoc>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de infodoc enregistrés");
					reponse = new Reponse<List<InfoDoc>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un infoDoc par son identifiant
		@GetMapping("/infoDocParEntreprise/{id}")
		public String getDocByIdEntr(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<InfoDoc>> reponse;
			try {
				List<InfoDoc> pers = documentMetier.getInfoDocParEntr(id);
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<InfoDoc>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de infodoc enregistrés");
					reponse = new Reponse<List<InfoDoc>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
	@DeleteMapping("/infoDoc/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));
			InfoDoc db = documentMetier.findById(id);
			s3Services.deleteFile(db.getDepartement().getLibelle(), db.getNomDoc());
			reponse = new Reponse<Boolean>(0, messages, documentMetier.supprimer(id));
              
		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

////////rechercher un infoDoc par mot cle
	@GetMapping("/rechemc")
	public String chercherTravauxByMc(@RequestParam(value = "mc") String mc, 
			@RequestParam(value = "id") Long id) throws JsonProcessingException {

		Reponse<List<InfoDoc>> reponse;
		try {
			List<InfoDoc> infoDoc = documentMetier.chercherInfoDocParMc(mc, id);

			if (!infoDoc.isEmpty()) {
				reponse = new Reponse<List<InfoDoc>>(0, null, infoDoc);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de infoDoc info enregistrés");
				reponse = new Reponse<List<InfoDoc>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<InfoDoc>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
////////rechercher un infoDoc par mot cle
@GetMapping("/recheParentreprisemc")
public String chercherDocParByMc(@RequestParam(value = "mc") String mc, 
		@RequestParam(value = "id") Long id) throws JsonProcessingException {

	Reponse<List<InfoDoc>> reponse;
	try {
		List<InfoDoc> infoDoc = documentMetier.chercherInfoDocParEntrepriseMc(mc, id);

		if (!infoDoc.isEmpty()) {
			reponse = new Reponse<List<InfoDoc>>(0, null, infoDoc);
		} else {
			List<String> messages = new ArrayList<>();
			messages.add("Pas de infoDoc info enregistrés");
			reponse = new Reponse<List<InfoDoc>>(2, messages, new ArrayList<>());
		}

	} catch (Exception e) {
		reponse = new Reponse<List<InfoDoc>>(1, Static.getErreursForException(e), new ArrayList<>());
	}
	return jsonMapper.writeValueAsString(reponse);

}

	
	@PostMapping("/file/upload")
	public String uploadMultipartFile(@RequestParam Long id, @RequestParam("file") MultipartFile file) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		System.out.println("Voir id:" + id);
		Dossier dossier = dossierMetier.findById(id);
		String nomDossier = dossier.getLibelle();
        Departement dep = dossier.getDepartement();
        String nomDep = dep.getLibelle();
		String keyName = file.getOriginalFilename();
		System.out.println("Voir ce qui se passe:" + keyName);
        s3Services.uploadFile(nomDep,nomDossier, keyName, file);
		List<String> messages = new ArrayList<>();
		messages.add(String.format("Upload Successfully" + keyName));
		reponse = new Reponse<ResponseEntity<?>>(0, messages, null);

		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/file/download")
	public ResponseEntity<ByteArrayResource> downloadFile(
			@RequestParam Long id,
			@RequestParam String keyname) {
		    System.out.println("voir dossier retourné: "+id);
		    System.out.println("voir dossier retourné: "+ keyname);
		    Dossier dossier = dossierMetier.getDossierById(id);
		    System.out.println("voir dossier retourné: "+ dossier);
		    String nomDossier = dossier.getLibelle();
            Departement dep = dossier.getDepartement();
            String depName = dep.getLibelle();
            byte [] data = s3Services.downloadFile(depName, nomDossier, keyname);
            ByteArrayResource resource = new ByteArrayResource(data);
		    return ResponseEntity.ok()
					.contentLength(data.length)
					.header("content-type", "application/octet-stream")
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyname + "\"")
					.body(resource);
		    
						
	}
	
	private MediaType contentType(String keyname) {
		String[] arr = keyname.split("\\.");
		String type = arr[arr.length-1];
		switch(type) {
			case "txt": return MediaType.TEXT_PLAIN;
			case "png": return MediaType.IMAGE_PNG;
			case "jpg": return MediaType.IMAGE_JPEG;
			default: return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
	@DeleteMapping("/file/delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String depName, @PathVariable String keyname){
		return new ResponseEntity<>(s3Services.deleteFile(depName, keyname),HttpStatus.OK);
	}
	// recuperer les images du site
	@GetMapping(value = "/getImage/{version}/{idE}/{idD}/{nomDoc}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosTravaux(@PathVariable Long version, @PathVariable Long idE, @PathVariable Long idD,
			@PathVariable String nomDoc) throws FileNotFoundException, IOException {
		String dossier = archives + "/" + "archives" + "/" + idE + "/" + idD + "/" + nomDoc;
		File f = new File(dossier);

		byte[] img = IOUtils.toByteArray(new FileInputStream(f));
		System.out.println(img);
		System.out.println(f);

		return img;
	}

}
