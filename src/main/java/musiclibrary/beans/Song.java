package musiclibrary.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */
@Entity
@Embeddable
@Data
@Table(name="songs")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Song implements Serializable{
	
	// Variables
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="TITLE")
	private String title;
	@Column(name="ARTIST")
	private String artist;
	@Column(name="GENRE")
	private String genre;

}