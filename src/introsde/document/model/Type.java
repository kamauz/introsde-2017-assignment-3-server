package introsde.document.model;

import introsde.document.dao.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="\"Type\"") // to whate table must be persisted
@NamedQuery(name="Type.findAll", query="SELECT t FROM Type t")
@XmlRootElement(name="activity_type")
public class Type implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"type\"")
	private String type;

	@OneToMany(mappedBy="activityType")
	private List<Preference> activities;

	public Type() {
	}
	
	public Type(String type) {
		this.type = type;
	}

	@XmlValue
	public String getTypeName() {
		return type;
	}

	public void setTypeName(String type) {
		this.type = type;
	}
	
	public static List<Type> getAll() {
		EntityManager em = PersonDao.instance.createEntityManager();
        List<Type> list = em.createNamedQuery("Type.findAll", Type.class)
            .getResultList();
        PersonDao.instance.closeConnections(em);
        return list;
	}

	@XmlTransient
	public List<Preference> getActivities() {
		return activities;
	}

	public void setActivities(List<Preference> activities) {
		this.activities = activities;
	}
}