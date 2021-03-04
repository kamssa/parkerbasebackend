package ci.parkerbase.metier.doc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.parkerbase.dao.ImageRepository;
import ci.parkerbase.entity.entreprise.Image;



@Service
public class ImageMetierImpl implements ImageMetier {
@Autowired
private ImageRepository imageRepository;

@Override
public Image creer(Image entity) {
	// TODO Auto-generated method stub
	return imageRepository.save(entity);
}

@Override
public Image modifier(Image entity) {
	// TODO Auto-generated method stub
	return imageRepository.save(entity);
}

@Override
public List<Image> findAll() {
	// TODO Auto-generated method stub
	return imageRepository.findAll();
}

@Override
public Image findById(Long id) {
	// TODO Auto-generated method stub
	return imageRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean supprimer(List<Image> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Boolean existsByPseudo(String pseudo) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Boolean existsByEmail(String email) {
	// TODO Auto-generated method stub
	return null;
}
	
}
