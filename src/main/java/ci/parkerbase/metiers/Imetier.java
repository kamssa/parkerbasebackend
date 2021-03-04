package ci.parkerbase.metiers;

import java.util.List;

import ci.parkerbase.exception.InvalideParkerBaseException;




public interface Imetier <T,U>{
	
	public T creer(T entity) throws InvalideParkerBaseException;
	
	public T modifier(T entity) throws InvalideParkerBaseException;
	
	public List<T> findAll();
	
	public T findById(U id);

	public boolean supprimer(U id);
	
	public boolean supprimer(List<T> entites);
	
	public boolean existe(U id);
	
	Boolean existsByPseudo(String pseudo);

	Boolean existsByEmail(String email);

}
