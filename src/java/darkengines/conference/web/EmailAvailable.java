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
import java.io.IOException;
import java.io.PrintWriter;
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
public class EmailAvailable extends HttpServlet {

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
	    throws ServletException, IOException {
	response.setContentType("application/json");
	String email = request.getParameter("email");


	FormValidator validator = new FormValidator();
	validator.validators.put("email", new EmailValidator());

	Hashtable<String, String> errors = validator.validate(request.getParameterMap());
	if (errors.size() > 0) {
	    Gson gson = new Gson();
	    JSONResponse jsonResponse = new JSONResponse(1, errors);
	    response.getWriter().write(jsonResponse.toString());
	} else {
		    try {
			User user = UserModule.getUserRepository().getUserByEmail(email);
			if (user != null) {
			    errors.put("email", "Email is already used");
			    JSONResponse jsonResponse = new JSONResponse(1, errors);
			    response.getWriter().write(jsonResponse.toString());
			} else {
			    JSONResponse jsonResponse = new JSONResponse(0, null);
			    response.getWriter().write(jsonResponse.toString());
			}
		    } catch (UnsupportedEncodingException ex) {
			Logger.getLogger(EmailAvailable.class.getName()).log(Level.SEVERE, null, ex);
		    } catch (ClassNotFoundException ex) {
			Logger.getLogger(EmailAvailable.class.getName()).log(Level.SEVERE, null, ex);
		    } catch (NamingException ex) {
			Logger.getLogger(EmailAvailable.class.getName()).log(Level.SEVERE, null, ex);
		    } catch (SQLException ex) {
			Logger.getLogger(EmailAvailable.class.getName()).log(Level.SEVERE, null, ex);
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
	    throws ServletException, IOException {
	processRequest(request, response);
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
	    throws ServletException, IOException {
	processRequest(request, response);
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
    private class Result {
	private boolean available;
	public Result(boolean available) {
	    this.available = available;
	}
    }
}
