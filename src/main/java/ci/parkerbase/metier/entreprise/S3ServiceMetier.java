package ci.parkerbase.metier.entreprise;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3ServiceMetier {
	public ByteArrayOutputStream downloadFiles(String depName, String keyName);
	public byte[] downloadFile(String depName, String keyName);
	public void uploadFile(String depName, String keyName, MultipartFile file);
	public String deleteFile(String depName, String keyName);
	public List listFiles();
	
}
