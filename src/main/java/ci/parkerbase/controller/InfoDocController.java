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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.core.Path;

import ci.parkerbase.entity.entreprise.InfoDoc;
import ci.parkerbase.metier.doc.ImageMetier;
import ci.parkerbase.metier.doc.InfoDocMetier;
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

	@DeleteMapping("/infoDoc/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, documentMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

////////rechercher un infoDoc par mot cle
	@GetMapping("/rechemc")
	public String chercherTravauxByMc(@RequestParam(value = "mc") String mc) throws JsonProcessingException {

		Reponse<List<InfoDoc>> reponse;
		try {
			List<InfoDoc> infoDoc = documentMetier.chercherInfoDocParMc(mc);

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

	// uploader plusieurs images
	/*
	 * @PostMapping("/image") public String creerImage(@RequestParam(name =
	 * "image_doc") MultipartFile file) throws Exception { Reponse<InfoDoc> reponse
	 * = null; Reponse<InfoDoc> reponseParLibelle; // recuperer le libelle à partir
	 * du nom de la photo String nomDoc = file.getOriginalFilename();
	 * System.out.println("voir le libelle:" + nomDoc); InfoDoc b =
	 * documentMetier.findByNomDoc(nomDoc);
	 * 
	 * System.out.println("voir infoDoc:"+b); String path =
	 * "http://localhost:8080/getImage/" + b.getVersion()+ "/" + b.getIdEntreprise()
	 * + "/" + b.getDepartement().getId()+"/"+ nomDoc; String dossier = archives +
	 * "archives" + "/"+ b.getIdEntreprise() +"/"+ b.getDepartement().getId()+"/";
	 * 
	 * File rep = new File(dossier);
	 * 
	 * if (!file.isEmpty()) { if (!rep.exists() && !rep.isDirectory()) {
	 * rep.mkdir(); } } try { // enregistrer le chemin dans la photo
	 * b.setPathImage(path); System.out.println(path); System.out.println(dossier);
	 * file.transferTo(new File(dossier + file.getOriginalFilename())); List<String>
	 * messages = new ArrayList<>();
	 * messages.add(String.format("%s (image ajouter avec succes)",
	 * b.getLibelle())); reponse = new Reponse<InfoDoc>(0, messages,
	 * documentMetier.modifier(b));
	 * 
	 * } catch (Exception e) {
	 * 
	 * reponse = new Reponse<InfoDoc>(1, Static.getErreursForException(e), null); }
	 * 
	 * return jsonMapper.writeValueAsString(reponse); }
	 * 
	 */
	@PostMapping("/file/upload")
	public String uploadMultipartFile(@RequestParam Long id, @RequestParam("file") MultipartFile file) {
		Reponse<InfoDoc> reponse = null;

		String keyName = file.getOriginalFilename();
		System.out.println("Voir ce qui se passe:" + keyName);
		s3Services.uploadFile(keyName, file);
		List<String> messages = new ArrayList<>();
		messages.add(String.format("Upload Successfully" + keyName));
		reponse = new Reponse<InfoDoc>(0, messages, null);
		return "Upload Successfully -> KeyName = " + keyName;
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
