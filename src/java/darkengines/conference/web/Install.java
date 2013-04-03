/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.channel.ChannelModule;
import darkengines.friendship.FriendshipModule;
import darkengines.session.SessionModule;
import darkengines.user.UserModule;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class Install extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
	try {
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    UserModule.getUserRepository().install();
	    SessionModule.getSessionRepository().install();
	    FriendshipModule.getFriendshipRepository().install();
            ChannelModule.getChannelRepository().install();
            ChannelModule.getChannelParticipantRepository().install();
            ChannelModule.getChannelInvitationRepository().install();
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
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
