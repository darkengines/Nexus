/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core;

/**
 *
 * @author Quicksort
 */
public class PasswordValidator implements IValidator {

    @Override
    public boolean validate(String[] raw) {
	return raw[0].length() > 2;
    }

    @Override
    public String getErrorMessage() {
	return "Password is not valid";
    }
    
}
