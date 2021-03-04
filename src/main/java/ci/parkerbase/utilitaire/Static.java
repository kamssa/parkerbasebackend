package ci.parkerbase.utilitaire;

import java.util.ArrayList;
import java.util.List;

public class Static {

	public Static() {
		super();
		
	}
public static List<String> getErreursForException(Exception exception){
	Throwable cause= exception;
	List<String> erreurs = new ArrayList<>();
	while (cause!=null) {
		String message =  cause.getMessage();
		if (message!=null) {
			message=message.trim();
			if (message.length()!=0) {
				erreurs.add(message);
			}
		}
	cause=	cause.getCause();
	}
	return erreurs;
}
}
