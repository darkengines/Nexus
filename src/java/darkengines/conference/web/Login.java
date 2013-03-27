/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import com.google.gson.Gson;
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

public class Login extends HttpServlet {

    FormValidator validator = new FormValidator();
    Gson gson;

    public Login() {
	super();
	gson = new Gson();
	validator = new FormValidator();
	validator.validators.put("email", new EmailValidator());
	validator.validators.put("password", new PasswordValidator());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
	try {
	    response.setContentType("application/json");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");


	    Hashtable<String, String> errors = validator.validate(request.getParameterMap());

	    if (errors.size() > 0) {
		JSONResponse jsonResponse = new JSONResponse(1, errors);
		response.getWriter().write(jsonResponse.toString());
	    } else {
		User user = UserModule.getUserRepository().getUserByCredentials(email, password);
		if (user == null) {
		    errors.put("email", "Bad credentials");
		    JSONResponse jsonResponse = new JSONResponse(1, errors);
		    response.getWriter().write(jsonResponse.toString());
		} else {
		    Session session = new Session();
		    session.setUserId(user.getId());
		    session.setUuid(UUID.randomUUID());
		    session = SessionModule.getSessionRepository().insertSession(session);
		    response.getWriter().write(new JSONResponse(0, session).toString());
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
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
