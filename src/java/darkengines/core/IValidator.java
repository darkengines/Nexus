/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core;

/**
 *
 * @author Quicksort
 */
public interface IValidator {
    boolean validate(String[] raw);
    String getErrorMessage();
}
