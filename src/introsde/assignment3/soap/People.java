package introsde.assignment3.soap;
import introsde.document.model.*;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
	
	// 1
	@WebMethod(operationName="readPersonList")
    @WebResult(name="personList") 
    public List<Person> readPersonList();
	
	// 2
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="id") Long id);
    
    // 3
    @WebMethod(operationName="updatePerson")
    @WebResult(name="personId") 
    public Person updatePerson(@WebParam(name="p") Person p);

    // 4
    @WebMethod(operationName="createPerson")
    @WebResult(name="create") 
    public Person createPerson(@WebParam(name="p") Person p);

    // 5
    @WebMethod(operationName="deletePerson")
    @WebResult(name="delete") 
    public void deletePerson(@WebParam(name="id") Long id);

    // 6
    @WebMethod(operationName="readPersonPreferences1")
    @WebResult(name="readPersonPreferences") 
	public List<Preference> readPersonPreferences1(Long id, String activity_type);

    // 7
    @WebMethod(operationName="readPreferences")
    @WebResult(name="readPreferences") 
	public List<Preference> readPreferences();
    
    // 8
    @WebMethod(operationName="readPersonPreferences2")
    @WebResult(name="readPreferences") 
	public Preference readPersonPreferences2(Long id, Long activity_id);

    // 9
    @WebMethod(operationName="savePersonPreferences")
    @WebResult(name="savePersonPreferences") 
	public void savePersonPreferences(Long id, Preference activity);

    // 10
    @WebMethod(operationName="updatePersonPreferences")
    @WebResult(name="updatePersonPreferences") 
	void updatePersonPreferences(Long id, Preference activity);

    // 11 (extra)
    @WebMethod(operationName="evaluatePersonPreferences")
    @WebResult(name="evaluatePersonPreferences") 
    public Preference evaluatePersonPreferences(Long id, Preference activity, int value);
    
    // 12 (extra)
    @WebMethod(operationName="getBestPersonPreference")
    @WebResult(name="getBestPersonPreference") 
    public List<Preference> getBestPersonPreference(Long id);
}