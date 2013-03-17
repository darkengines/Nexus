/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.friendship;

import darkengines.user.*;

/**
 *
 * @author Quicksort
 */
public class Friendship {
    private long id;
    private long owner;
    private long target;
    
    public Friendship() {
	
    }
    
    public Friendship(long owner, long target) {
	this.owner = owner;
	this.target = target;
    }
    
    public Friendship(long id, long owner, long target) {
	this.id = id;
	this.owner = owner;
	this.target = target;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getOwner() {
	return owner;
    }

    public void setOwner(long owner) {
	this.owner = owner;
    }

    public long getTarget() {
	return target;
    }

    public void setTarget(long target) {
	this.target = target;
    }
    
}
