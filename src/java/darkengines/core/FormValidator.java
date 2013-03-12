/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author Quicksort
 */
public class FormValidator {
    public Hashtable<String, IValidator> validators;
    public FormValidator() {
	validators = new Hashtable<String, IValidator>();
    }
    public Hashtable<String, String> validate(Map<String, String[]> fields) {
	Hashtable<String, String> result = new Hashtable<String, String>();
	for (String key: fields.keySet()) {
	    if (!validators.get(key).validate(fields.get(key))) {
		result.put(key, validators.get(key).getErrorMessage());
	    }
	}
	return result;
    }
}
