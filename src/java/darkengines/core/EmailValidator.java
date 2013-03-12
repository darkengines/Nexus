/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core;

/**
 *
 * @author Quicksort
 */
public class EmailValidator implements IValidator {

    @Override
    public boolean validate(String[] raw) {
	return raw[0].matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$");
    }
    public String getErrorMessage() {
	return "Email is not valid";
    }
}
