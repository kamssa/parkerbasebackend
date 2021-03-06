package ci.parkerbase.metier.entreprise;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import ci.parkerbase.entity.entreprise.Departement;

@Service
public class S3ServiceImpl implements S3ServiceMetier{
private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);
	
	@Autowired
	private AmazonS3 s3client;
 
	@Value("${gkz.s3.bucket}")
	private String bucketName;
	@Autowired
	private IDepartementMetier departementMetier;
	@Override
	public ByteArrayOutputStream downloadFiles(String depName,String keyName) {
		try {
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName+"/"+ depName, keyName));
            
            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            
            return baos;
		} catch (IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
        } catch (AmazonServiceException ase) {
        	logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			throw ase;
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
		
		return null;
	}

	@Override
	public void uploadFile(String depName, String keyName, MultipartFile file) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			System.out.println("Voir:" + file.getOriginalFilename());
			 Departement dep = departementMetier.findDepartementByLibelle(depName);
			  String entreName = dep.getEntreprise().getNom();
              s3client.putObject(bucketName+ "/"+ entreName + "/"+ depName, keyName, file.getInputStream(), metadata);
            
		} catch(IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			throw ase;
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }
		
	}

	@Override
	public List listFiles() {
		
		  ListObjectsRequest listObjectsRequest = 
	              new ListObjectsRequest()
	                    .withBucketName(bucketName);
	                    //.withPrefix("test" + "/");
			
			List keys = new ArrayList<>();
			
			ObjectListing objects = s3client.listObjects(listObjectsRequest);
                          
			
			while (true) {
				List<S3ObjectSummary> summaries = objects.getObjectSummaries();
				if (summaries.size() < 1) {
					break;
				}
				
				for (S3ObjectSummary item : summaries) {
		            if (!item.getKey().endsWith("/"))
		            	keys.add(item.getKey());
		        }
				
				objects = s3client.listNextBatchOfObjects(objects);
			}
			
			return keys;
		}
	private File convertMutilpartFileToFie(MultipartFile file) {
		File converedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(converedFile)){
		fos.write(file.getBytes());	
		} catch (Exception e) {
		e.getMessage();
		}
		
		return converedFile;
	}

	@Override
	public byte[] downloadFile(String depName, String keyName) {
		Departement dep = departementMetier.findDepartementByLibelle(depName);
	    String entreName = dep.getEntreprise().getNom();
		S3Object s3Object = s3client.getObject(bucketName+ "/"+ entreName + "/"+ depName, keyName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		byte[] content = null;
		try {
			 content = IOUtils.toByteArray(inputStream);
			//return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public String deleteFile(String depName, String keyName) {
		Departement dep = departementMetier.findDepartementByLibelle(depName);
	    String entreName = dep.getEntreprise().getNom();
		s3client.deleteObject(bucketName+ "/"+ entreName + "/"+ depName, keyName);
		return keyName + "élément supprimé";
	}
}
