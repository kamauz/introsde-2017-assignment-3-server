package introsde.assignment3.soap;
import introsde.document.model.*;

import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment3.soap.People", serviceName="PeopleService")
public class PeopleImpl implements People {
	
	// 1
	@Override
	public List<Person> readPersonList() {
		return Person.getAll();
	}

	//2
	@Override
	public Person readPerson(Long id) {
		System.out.println("---> Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getName());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
	}
	
	// 3
	@Override
	public Person updatePerson(Person person) {
		Person p = Person.updatePerson(person);
	    return p;
	}

	// 4
	@Override
	public Person createPerson(Person person) {
		return Person.savePerson(person);
	}
	
	// 5
	@Override
    public void deletePerson(Long id) {
        Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
        }
    }
	
	// 6
	@Override
	public List<Preference> readPersonPreferences1(Long id, String activity_type) {
		return Preference.getAllFromUserGivenType(id, activity_type);
	}
	
	
	// 7
	@Override
	public List<Preference> readPreferences() {
		return Preference.getAll();
	}
	
	// 8
	@Override
	public Preference readPersonPreferences2(Long id, Long activity_id) {
		return Preference.getUserActivityGivenId(id,activity_id);
	}
	
	// 9
	@Override
	public void savePersonPreferences(Long id, Preference activity) {
		Preference.savePersonActivity(id, activity);
	}
	
	// 10
	@Override
	public void updatePersonPreferences(Long id, Preference activity) {
		Preference.savePersonActivity(id, activity);
	}

	// 11 (extra)
	@Override
	public Preference evaluatePersonPreferences(Long id, Preference activity, int value) {
		Preference.evaluatePersonPreferences(id, activity, value);
		return null;
	}

	// 12 (extra)
	@Override
	public List<Preference> getBestPersonPreference(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}