/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import com.google.gson.Gson;
import darkengines.core.EmailValidator;
import darkengines.core.FormValidator;
import darkengines.core.json.JSONResponse;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class EmailAvailable extends HttpServlet {

    FormValidator validator = new FormValidator();
    Gson gson;

    public EmailAvailable() {
	super();
	gson = new Gson();
	validator.validators.put("email", new EmailValidator());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
	try {
	    response.setContentType("application/json");
	    String email = request.getParameter("email");

	    Hashtable<String, String> errors = validator.validate(request.getParameterMap());
	    if (errors.size() > 0) {
		JSONResponse jsonResponse = new JSONResponse(1, errors);
		response.getWriter().write(jsonResponse.toString());
	    } else {
		User user = UserModule.getUserRepository().getUserByEmail(email);
		if (user != null) {
		    errors.put("email", "Email is already used");
		    JSONResponse jsonResponse = new JSONResponse(1, errors);
		    response.getWriter().write(jsonResponse.toString());
		} else {
		    JSONResponse jsonResponse = new JSONResponse(0, null);
		    response.getWriter().write(jsonResponse.toString());
		}

	    }
	} catch (Exception e) {
	    Logger.getLogger(EmailAvailable.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
	processRequest(request, response);
    }

    private class Result {

	private boolean available;

	public Result(boolean available) {
	    this.available = available;
	}
    }
}
