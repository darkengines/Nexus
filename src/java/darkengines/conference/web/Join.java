/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import com.google.gson.Gson;
import darkengines.core.DisplayNameValidator;
import darkengines.core.EmailValidator;
import darkengines.core.FormValidator;
import darkengines.core.PasswordValidator;
import darkengines.core.json.JSONResponse;
import darkengines.session.Session;
import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.util.Hashtable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class Join extends HttpServlet {

    FormValidator validator = new FormValidator();
    Gson gson;

    public Join() {
	super();
	gson = new Gson();
	validator.validators.put("email", new EmailValidator());
	validator.validators.put("password", new PasswordValidator());
	validator.validators.put("display_name", new DisplayNameValidator());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
	try {
	    response.setContentType("application/json");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String displayName = request.getParameter("display_name");
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
		    user = new User();
		    user.setEmail(email);
		    user.setPassword(password);
		    user.setDisplayName(displayName);
		    UserModule.getUserRepository().insertUser(user);
		    Session session = new Session();
		    session.setUserId(user.getId());
		    session.setUuid(UUID.randomUUID());
		    SessionModule.getSessionRepository().insertSession(session);
		    JSONResponse jsonResponse = new JSONResponse(0, session);
		    response.getWriter().write(jsonResponse.toString());
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(Join.class.getName()).log(Level.SEVERE, null, e);
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
}
