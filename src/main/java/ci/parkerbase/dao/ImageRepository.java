package ci.parkerbase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.parkerbase.entity.entreprise.Image;



public interface ImageRepository extends JpaRepository<Image, Long> {

}
