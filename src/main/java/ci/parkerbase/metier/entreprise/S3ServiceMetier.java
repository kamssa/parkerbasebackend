package ci.parkerbase.metier.entreprise;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3ServiceMetier {
	public ByteArrayOutputStream downloadFiles(String depName, String keyName);
	public byte[] downloadFile(String depName, String nomDossier, String keyName);
	public void uploadFile(String depName, String dosName,  String keyName, MultipartFile file);
	public String deleteFile(String depName, String dosName, String keyName);
	public List listFiles();
	public List listFiles(String depName, String nomDossier, String keyName);

	
}
