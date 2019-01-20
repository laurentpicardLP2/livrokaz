package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


///**
// * The persistent class for the User database table.
// * 
// */
//@Entity
//@NamedQuery(name="User.findAll", query="SELECT u FROM UserInfo u")
//public class UserInfo implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//
//	public UserInfo(){
//		
//	}
//	
//
//
//
//}