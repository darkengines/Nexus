/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core;

/**
 *
 * @author Quicksort
 */
public class DisplayNameValidator implements IValidator{

    @Override
    public boolean validate(String[] raw) {
	int length = raw[0].length();
	return length > 2 && length < 32 && raw[0].matches("[A-Za-z0-9 _.-]*");
    }

    @Override
    public String getErrorMessage() {
	return "Display name is not valid";
    }
    
}
