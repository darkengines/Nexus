/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.channel;

/**
 *
 * @author Quicksort
 */
public class ChannelModule {
    private static ChannelRepository channelRepository = null;
    private static ChannelParticipantRepository channelParticipantRepository = null;
    private static ChannelInvitationRepository channelInvitationRepository = null;
    
    public static ChannelRepository getChannelRepository() {
	if (channelRepository == null) {
	    channelRepository = new ChannelRepository();
	}
	return channelRepository;
    }

    public static ChannelParticipantRepository getChannelParticipantRepository() {
	if (channelParticipantRepository == null) {
	    channelParticipantRepository = new ChannelParticipantRepository();
	}
	return channelParticipantRepository;
    }

    public static ChannelInvitationRepository getChannelInvitationRepository() {
        if (channelInvitationRepository == null) {
	    channelInvitationRepository = new ChannelInvitationRepository();
	}
	return channelInvitationRepository;
    }
}
