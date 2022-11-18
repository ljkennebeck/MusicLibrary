package musiclibrary.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import musiclibrary.beans.Song;



/**
 * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
 * CIS175-Fall 2022
 * Nov 16, 2022
 */


public class SongHelper {

	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("MusicLibrary");
	
	public void insertSong(Song s) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Song> showAllSongs() {
		EntityManager em = emfactory.createEntityManager();
		List<Song> allSongs = em.createQuery("SELECT i FROM Song i").getResultList();
		return allSongs;
	}
	
	public void deleteSong(Song toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Song> typedQuery = em.createQuery("SELECT s FROM Song s WHERE s.title = :selectedTitle AND s.artist = :selectedArtist", Song.class);
		
		// Substitute parameter with actual data from the toDelete song
		typedQuery.setParameter("selectedTitle", toDelete.getTitle());
		typedQuery.setParameter("selectedArtist", toDelete.getArtist());
		
		// Retrieve only one result
		typedQuery.setMaxResults(1);
		
		// Get result and save into a new Song
		Song result = typedQuery.getSingleResult();
		
		// Remove it
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Song> searchForSongByTitle(String songTitle) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Song> typedQuery = em.createQuery("SELECT s FROM Song s WHERE s.title = :selectedTitle", Song.class);
		typedQuery.setParameter("selectedTitle", songTitle);
		
		List<Song> foundSongs = typedQuery.getResultList();
		em.close();
		
		return foundSongs;
	}
	
	public List<Song> searchForSongByArtist(String songArtist) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Song> typedQuery = em.createQuery("SELECT s FROM Song s WHERE s.artist = :selectedArtist", Song.class);
		typedQuery.setParameter("selectedArtist", songArtist);
		
		List<Song> foundSongs = typedQuery.getResultList();
		em.close();
		
		return foundSongs;
	}
	
	public List<Song> searchForSongByTitleAndArtist(String title, String artist){
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Song> typedQuery = em.createQuery("SELECT s FROM Song WHERE s.title = :selectedTitle AND s.artist = :selectedArtist", Song.class);
		
		typedQuery.setParameter("selectedTitle", title);
		typedQuery.setParameter("selectedArtist", artist);
		
		List<Song> foundSongs = typedQuery.getResultList();
		em.close();
		
		return foundSongs;
				
	}
	
	public Song searchForSongById(int idToEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Song found = em.find(Song.class, idToEdit);
		em.close();
		
		return found;
	}
	
	public void updateSong(Song toEdit) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(toEdit);
		em.getTransaction().commit();
		em.close();
	}
	
	public void cleanUp() {
		emfactory.close();
	}
	
	
}
