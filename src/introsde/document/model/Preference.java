package introsde.document.model;

import introsde.document.dao.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@Entity  // indicates that this class is an entity to persist in DB
@XmlRootElement
@Table(name="\"Preference\"") // to whate table must be persisted
@NamedQueries({
	
	@NamedQuery(name="Preference.getUserActivityGivenId",
			query="SELECT a FROM Preference a WHERE a.person=:Person AND a=:Preference"),
	
	@NamedQuery(name="Preference.getAll", query="SELECT p FROM Preference p"),
	
	@NamedQuery(name="Preference.getAllFromUserGivenType",
			query="SELECT a FROM Preference a WHERE a.person=:Person AND a.activityType=:Type"),

	@NamedQuery(name="Preference.getAllActivitiesGivenId",
			query="SELECT a FROM Preference a WHERE a.person=:Person AND a.activityType=:Type AND a=:Preference"),
	
	@NamedQuery(name="Activity.filterForDates",
			query="SELECT a FROM Preference a WHERE a.person=:Person AND a.startdate > :Before AND a.startdate < :After")
})
public class Preference implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="\"idActivity\"") // maps the following attribute to a column
	@XmlAttribute(name="id")
    private Long idActivity;
	@Column(name="\"name\"")
	private String name; // in kg
	@Column(name="\"description\"")
	private String description; // in m
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="\"activityType\"", referencedColumnName="\"type\"", insertable=true)
	private Type activityType;
	@Column(name="\"place\"")
	private String place;
	@Column(name="\"startdate\"")
	private String startdate;
	@Column(name="\"rate\"")
	private Integer rate;
	@ManyToOne
	@JoinColumn(name="\"idPerson\"",referencedColumnName="\"idPerson\"")
	private Person person;

	public Preference(String name, String description, String place, Type type, String startdate, Integer rate) {
		this.name = name;
		this.description = description;
		this.place = place;
		setType(type);
		this.startdate = startdate;
		this.rate = rate;
	}

	public Preference() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public Long getIdActivity() {
		return idActivity;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	public Type getType() {
		return this.activityType;
	}

	public void setType(Type type) {
		
		EntityManager em = PersonDao.instance.createEntityManager();
		Type t = em.find(Type.class, type.getTypeName());
    	if (t == null) {
    		EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(type);
            tx.commit();
    	}
    	this.activityType = type;
    	PersonDao.instance.closeConnections(em);
	}
	
	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public static Preference getUserActivityGivenId(Long id, Long activity_id) {
		EntityManager em = PersonDao.instance.createEntityManager();
		Person p = em.find(Person.class, id);
		Preference a = em.find(Preference.class, activity_id);
		
        Preference element = em.createNamedQuery("Preference.getUserActivityGivenId", Preference.class)
        	.setParameter("Person", p)
        	.setParameter("Preference", a)
            .getSingleResult();
        PersonDao.instance.closeConnections(em);
        return element;
	}
	
	public static List<Preference> getAllFromUserGivenType(Long id, String type) {
		EntityManager em = PersonDao.instance.createEntityManager();
		Person p = em.find(Person.class, id);
		Type t = em.find(Type.class, type);
		
        List<Preference> list = em.createNamedQuery("Preference.getAllFromUserGivenType", Preference.class)
        	.setParameter("Person", p)
        	.setParameter("Type", t)
            .getResultList();
        PersonDao.instance.closeConnections(em);
        return list;
        
	}
	
	public static Preference evaluatePersonPreferences(Long id, Preference activity, int value) {
		
		try {
			EntityManager em = PersonDao.instance.createEntityManager();
			Person p = em.find(Person.class, id);
			
			Preference element = em.createNamedQuery("Preference.getUserActivityGivenId", Preference.class)
		        	.setParameter("Person", p)
		        	.setParameter("Preference", activity)
		            .getSingleResult();
			
			EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        element.setRate(value);
	        em.merge(element);
			tx.commit();
			
			PersonDao.instance.closeConnections(em);
			return element;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Preference> getAll() {
		EntityManager em = PersonDao.instance.createEntityManager();
        List<Preference> list = em.createNamedQuery("Preference.getAll", Preference.class)
            .getResultList();
        PersonDao.instance.closeConnections(em);
        return list;
	}
	
	
	public static Preference updateActivity(Long id, Preference p) {
		EntityManager em = PersonDao.instance.createEntityManager();
		Person person = em.find(Person.class, id);
		Preference a = em.find(Preference.class, p);
		
		Preference element = em.createNamedQuery("Activity.getAllActivitiesGivenId", Preference.class)
	        	.setParameter("Person", person)
	        	.setParameter("Preference", a)
	            .getSingleResult();
	    
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(element);
		tx.commit();
		
		em.persist(element);
		
		PersonDao.instance.closeConnections(em);
		return element;
	}
	
	public static Preference updateActivityType(int id, Type newType, Type type, int activity_id) {
		EntityManager em = PersonDao.instance.createEntityManager();
		Person p = em.find(Person.class, id);
		Type t = em.find(Type.class, type.getTypeName());
		Preference a = em.find(Preference.class, activity_id);
		
		Preference element = em.createNamedQuery("Activity.getAllActivitiesGivenId", Preference.class)
	        	.setParameter("Person", p)
	        	.setParameter("Type", t)
	        	.setParameter("Activity", a)
	            .getSingleResult();
	    
		EntityTransaction tx = em.getTransaction();
        tx.begin();
        element.setType(newType);
		tx.commit();
		
		em.persist(element);
		
		PersonDao.instance.closeConnections(em);
		return element;
	}
	
	public static void savePersonActivity(Long id, Preference p) {
		try {
			EntityManager em = PersonDao.instance.createEntityManager();
			Person person = em.find(Person.class, id);
			System.out.println(person.getName()+"   "+p.getName());
			if (person==null || p==null) {
				PersonDao.instance.closeConnections(em);
				return;
			}
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        p.setPerson(person);
	        em.merge(p);
	        tx.commit();
	        PersonDao.instance.closeConnections(em);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Preference> getAllActivitiesGivenId(int id, String type, int act_id) {
		try {
			EntityManager em = PersonDao.instance.createEntityManager();
			System.out.println(id+" "+type+" "+act_id);
			Person p = em.find(Person.class, id);
			Type t = em.find(Type.class, type);
			Preference a = em.find(Preference.class, act_id);
			
	        List<Preference> element = em.createNamedQuery("Activity.getAllActivitiesGivenId", Preference.class)
	        	.setParameter("Person", p)
	        	.setParameter("Type", t)
	        	.setParameter("Activity", a)
	            .getResultList();
	        
	        PersonDao.instance.closeConnections(em);
	        return element;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
	}
		
}