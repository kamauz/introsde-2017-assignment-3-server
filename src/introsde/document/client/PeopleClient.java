package introsde.document.client;

import introsde.assignment3.soap.People;
import introsde.document.model.Person;
import introsde.document.model.Preference;
import introsde.document.model.Type;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class PeopleClient{
	
	public static void populateDb(Service service) {
		People people = service.getPort(People.class);
		
		Person p = new Person();
		p.setName("Name1");
		p.setLastname("Lastname1");
		p.setBirthdate("01/01/2001");
		p.setActivity(new ArrayList<Preference>());
		
		Preference pref = new Preference();
		pref.setName("Activity1");
		pref.setDescription("Description1");
		pref.setPlace("Place1");
		pref.setStartdate("04/05/2005");
		pref.setType(new Type("Type1"));
		pref.setRate(2);
		p.getActivity().add(pref);
		
		Preference pref2 = new Preference();
		pref2.setName("Activity2");
		pref2.setDescription("Description2");
		pref2.setPlace("Place2");
		pref2.setStartdate("04/10/2005");
		pref2.setType(new Type("Type2"));
		pref2.setRate(2);
		
		p.getActivity().add(pref2);
		p.getActivity().add(pref);
		
		people.createPerson(p);
	}
	
	public static void printPreference(Preference p) {
		if (p == null) {
			return;
		}
		System.out.println("=> Activity: "+p.getName()+"  "+p.getDescription()+"  "+p.getPlace()+"  "+p.getStartdate()+
				"  "+p.getType()+"  "+p.getRate());
	}
	
	public static void printPerson(Person element) {
		System.out.println(element.getIdPerson()+"  "+element.getName()+"  "+element.getLastname() +
    			"  "+element.getBirthdate());
    	for (Preference p : element.getActivity()) {
    		printPreference(p);
    	}
	}
	
    public static void main(String[] args) throws Exception {
        //URL url = new URL("http://localhost:6902/people?wsdl");
    	URL url = new URL("http://sde3usa.herokuapp.com/people?wsdl");
    	
        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soap.assignment3.introsde/", "PeopleService");
        Service service = Service.create(url, qname);
        
        People people = service.getPort(People.class);
        if(people.readPersonList().size()<4) {
        	populateDb(service);
        }
        //populateDb(service);
        
        // 1 
        System.out.println("## EXERCISE 1) readPerson");
        List<Person> pList = people.readPersonList();
        System.out.println("Result ==> ");
        for (Person element : pList) {
        	printPerson(element);
        	System.out.println("");
        }
        
        // 2
        System.out.println("## EXERCISE 2) readPerson(Long id)");
        Person p = people.readPerson(pList.get(0).getIdPerson());
        printPerson(p);
        System.out.println("");
        
        // 3
        System.out.println("## EXERCISE 3) updatePerson(Person p)");
        String prev = p.getName();
        p.setName("Modified Name");
        Person p2 = people.updatePerson(p);
        if (p2.getName().equals("Modified Name")) {
        	System.out.println("=> modified successfully");
        	p.setName(prev);
        	p = people.updatePerson(p);
        } else {
        	System.out.println("=> error ");
        }
        System.out.println("");
        
        // 4
        System.out.println("## EXERCISE 4) createPerson(Person p)");
        p2 = new Person();
		p2.setName("createdName");
		p2.setLastname("Lastname1");
		p2.setBirthdate("01/01/2001");
		p2.setActivity(new ArrayList<Preference>());
		
		Preference pref = new Preference();
		pref.setName("Activity1");
		pref.setDescription("Description1");
		pref.setPlace("Place1");
		pref.setStartdate("04/05/2005");
		pref.setType(new Type("Type1"));
		p2.getActivity().add(pref);
        
		Person p3 = people.createPerson(p2);
		if (p3!=null) {
			System.out.println("=> created successfully");
		} else {
			System.out.println("=> error");
		}
		System.out.println("");
		
		// 5
        System.out.println("## EXERCISE 5) deletePerson(Long id)");
        long id = pList.get(0).getIdPerson();
        people.deletePerson(id);
        Person p4 = people.readPerson(id);
        if (p4 == null) {
        	System.out.println("=> deleted successfully");
        } else {
        	System.out.println("=> error");
        }
        System.out.println("");
        
        // 6
        System.out.println("## EXERCISE 6) readPersonPreferences(Long id, String activity_type)");
        pList = people.readPersonList();
        id = pList.get(0).getIdPerson();
        if (pList.get(0).getActivity().get(0).getType() != null) {
	        String type = pList.get(0).getActivity().get(0).getType().getTypeName();
	        List<Preference> p5 = people.readPersonPreferences1(id, type);
	        for (Preference element : p5) {
	        	printPreference(element);
	        }
	        System.out.println("=> works");
        } else {
        	System.out.println("=> error");
        }
        System.out.println("");
        
        // 7
        System.out.println("## EXERCISE 7) readPreferences()");
        List<Preference> listP = people.readPreferences();
        if (listP == null) {
        	System.out.println("=> error");
        } else {
	        for (Preference pl : listP) {
	        	printPreference(pl);
	        }
	        System.out.println("=> works");
	    }
        System.out.println("");
        
        // 8
        System.out.println("## EXERCISE 8) readPersonPreferences(Long id, Long activity_id)");
        id = pList.get(0).getIdPerson();
        Long activity_id = (long)pList.get(0).getActivity().get(0).getIdActivity();
        Preference pref2 = people.readPersonPreferences2(id, activity_id);
        if (pref2!=null) {
        	printPreference(pref2);
        	System.out.println("==> works");
        } else {
        	System.out.println("==> error");
        }
        System.out.println("");
        
        // 9
        System.out.println("## EXERCISE 9) savePersonPreferences(Long id, Activity activity)");
        id = pList.get(0).getIdPerson();
        Preference pTmp = new Preference();
        
        pTmp.setName("NewPref");
        pTmp.setDescription("NewPref_description");
        pTmp.setPlace("NewPref_place");
        pTmp.setStartdate("date");
        pTmp.setType(new Type("NewPref_type"));
        
        System.out.println(id+"   "+pTmp);
        people.savePersonPreferences(id, pTmp);
        List<Preference> p8test = people.readPersonPreferences1(id,pTmp.getType().getTypeName());
        boolean ok=false;
        System.out.println("Length: "+p8test.size());
        for (Preference pTest : p8test) {
        	System.out.println(pTest.getIdActivity()+"  "+pTest.getName()+"  "+pTest.getDescription()+"  "+
        			pTest.getPlace()+"  "+pTest.getStartdate()+"  "+pTest.getType());
	        if (pTest.getName().equals(pTmp.getName()) && pTest.getStartdate().equals(pTmp.getStartdate()) && 
	        		pTest.getPlace().equals(pTmp.getPlace())) {
	        	System.out.println("==> preference created successfully");
	        	ok=true;
	        	break;
	        }
        }
        if(ok == false) {
        	System.out.println("==> error");
        }
        
        System.out.println("");
        
        // 10
        System.out.println("## EXERCISE 10) updatePersonPreferences(Long id, Activity activity)");
        pList = people.readPersonList();
        System.out.println(pList.get(0).getIdPerson());
        Preference p10 = pList.get(0).getActivity().get(0);
        p10.setName("p10");
        people.updatePersonPreferences(pList.get(0).getIdPerson(), p10);
        Preference pp = people.readPersonPreferences2(pList.get(0).getIdPerson(),p10.getIdActivity());
        if (pp != null && pp.getName().equals("p10")) {
        	printPreference(pp);
        	System.out.println("==> updated successfully");
        } else {
        	System.out.println("==> error");
        }
        System.out.println("");
        
        // 11 (extra)
        System.out.println("## EXERCISE 11) evaluatePersonPreferences(Long id, Preference activity, int value)");
        printPreference(p10);
        System.out.println(pList.get(0).getIdPerson());
        Preference p11 = people.evaluatePersonPreferences(pList.get(0).getIdPerson(), p10, 6);
        printPreference(p11);
        if (p11 != null && p11.getRate() != null && p11.getRate().intValue() == 6) {
        	System.out.println("==> evaluated successfully");
        } else {
        	System.out.println("==> error");
        }
    }
}