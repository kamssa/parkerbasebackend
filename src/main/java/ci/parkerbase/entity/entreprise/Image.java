package ci.parkerbase.entity.entreprise;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	private String nomDoc;
	private Long infoDocId;
    private String path;
    
    
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Image(Long infoDocId, String path) {
		super();
		this.infoDocId = infoDocId;
		this.path = path;
	}

	public Long getInfoDocId() {
		return infoDocId;
	}
	public void setInfoDocId(Long infoDocId) {
		this.infoDocId = infoDocId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}

	public String getNomDoc() {
		return nomDoc;
	}

	public void setNomDoc(String nomDoc) {
		this.nomDoc = nomDoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infoDocId == null) ? 0 : infoDocId.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoDocId == null) {
			if (other.infoDocId != null)
				return false;
		} else if (!infoDocId.equals(other.infoDocId))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", version=" + version + ", infoDocId=" + infoDocId + ", path=" + path + "]";
	}
    
}
