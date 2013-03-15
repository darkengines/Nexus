/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.core.DisplayNameValidator;
import darkengines.core.EmailValidator;
import darkengines.core.FormValidator;
import darkengines.core.PasswordValidator;
import darkengines.core.json.JSONResponse;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import darkengines.session.Session;
import darkengines.session.SessionModule;
import java.util.UUID;

/**
 *
 * @author Quicksort
 */
public class Join extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, UnsupportedEncodingException, ClassNotFoundException, NamingException, SQLException, Exception {
        response.setContentType("application/json");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String displayName = request.getParameter("display_name");


	FormValidator validator = new FormValidator();
	validator.validators.put("email", new EmailValidator());
	validator.validators.put("password", new PasswordValidator());
	validator.validators.put("display_name", new DisplayNameValidator());

	Hashtable<String, String> errors = validator.validate(request.getParameterMap());
	if (errors.size() > 0) {
	    Gson gson = new Gson();
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


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, UnsupportedEncodingException {
	try {
	    processRequest(request, response);
	} catch (Exception e) {
	    response.getWriter().write(e.getMessage());
	}
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, UnsupportedEncodingException {
	try {
	    processRequest(request, response);
	} catch (Exception e) {
	    response.getWriter().write(e.getMessage());
	}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>
}
