//package musiclibrary.controller;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.NoResultException;
//import javax.persistence.Persistence;
//import javax.persistence.TypedQuery;
//
//import musiclibrary.beans.Playlist;
//
//
///**
// * @authors Viktoriia Denys, Kaitlyn Briggs & Logan Kennebeck - vdenys, krbriggs & ljkennebeck1
// * CIS175-Fall 2022
// * Nov 16, 2022
// */
//public class PlaylistHelper {
//
//		static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("MusicPlaylist");
//		
//		public void insertPlaylist(Playlist p) {
//			EntityManager em = emfactory.createEntityManager();
//			em.getTransaction().begin();
//			em.persist(p);
//			em.getTransaction().commit();
//			em.close();
//		}
//		
//		public List<Playlist> showAllPlaylists() {
//			EntityManager em = emfactory.createEntityManager();
//			List<Playlist> allPlaylists = em.createQuery("SELECT p FROM Playlist p").getResultList();
//			return allPlaylists;
//		}
//		
//		public Playlist findPlaylist(String lookupName) {
//			EntityManager em = emfactory.createEntityManager();
//			em.getTransaction().begin();
//			TypedQuery<Playlist> typedQuery = em.createQuery("SELECT p FROM Playlist p WHERE p.playlistName =:selectedName", Playlist.class);
//			typedQuery.setParameter("selectedName", lookupName);
//			
//			// Set max results to 1
//			typedQuery.setMaxResults(1);
//			
//			Playlist foundPlaylist;
//			try {
//				foundPlaylist = typedQuery.getSingleResult();
//			}
//			catch(NoResultException e) {
//				foundPlaylist = new Playlist(lookupName);
//			}
//			em.close();
//			
//			return foundPlaylist;
//		}
//	}
//
