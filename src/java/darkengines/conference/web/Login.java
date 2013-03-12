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
import darkengines.core.service.exception.PublicError;
import darkengines.core.service.exception.PublicException;
import darkengines.session.Session;
import darkengines.session.SessionModule;
import darkengines.user.BadCredentialsException;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class Login extends HttpServlet {

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
	Gson gson = new Gson();
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	FormValidator validator = new FormValidator();
	validator.validators.put("email", new EmailValidator());
	validator.validators.put("password", new PasswordValidator());

	Hashtable<String, String> errors = validator.validate(request.getParameterMap());

	if (errors.size() > 0) {
	    JSONResponse jsonResponse = new JSONResponse(1, gson.toJson(errors));
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




	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
	} catch (NamingException ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
	} catch (SQLException ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
	} catch (Exception ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
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




	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
	} catch (NamingException ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
	} catch (SQLException ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
	} catch (Exception ex) {
	    Logger.getLogger(Login.class
		    .getName()).log(Level.SEVERE, null, ex);
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
