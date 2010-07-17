/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/lgpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.gmvc.server.util;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Hibernate ayarlarinin ve session yonetiminin yapildigi siniftir.
 *  
 * @author mdpinar
 * 
 */
public final class DBUtils {

	private static SessionFactory sessionFactory = null;
	private static final Logger log = Logger.getLogger(DBUtils.class);

	/**
	 * Model siniflarin Client tarafina cevriminde kullaniliyor,
	 * GWT' nin yapisi geregi, Server nesneleri Client tarafindan taninamaz,
	 * Bu Sinif yardimi ile cevrim islemi yapilabiliyor
	 * 
	 */
	private static Mapper mapper = new DozerBeanMapper();

	/**
	 * Ornegi alinamaz
	 * 
	 */
	private DBUtils() {
		;
	}

	/**
	 * Ilk yuklenim
	 * 
	 */
	public static void init(Class<?>...classes) {

		if (classes == null) {
			log.fatal("ORM classes are null!");
		} else  if (sessionFactory == null) {
			try {

				AnnotationConfiguration acfg = new AnnotationConfiguration();
				for (Class<?> cls: classes) {
					acfg.addAnnotatedClass(cls);
				}
				sessionFactory = acfg.configure().buildSessionFactory();

				/*
				 * Baglantiyi test ediyor
				 */
				try {
					Transaction tx = getSession().beginTransaction();
					if (tx.isActive()) tx.rollback();
					log.info("Database connection successful.");
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Database connection error!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		} else {
			log.warn("Session Factory is null!");
		}

	}

	/**
	 * Uygulama sonlandirildiginda cagrilacak
	 * 
	 */
	public static void closeSessionFactory() {
		sessionFactory.close();
		log.info("Database connection closed.");
	}

	/**
	 * Yeni bir db oturumu doner
	 * 
	 * @return Session
	 */
	public static Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.openSession();
		} catch (Exception e) {
			log.error("Session getting error!");
		}
		return session;
	}

	/**
	 * CRUD servislerinde modellerin Server dan Client a cevrilmesini saglar
	 * 
	 * @return Mapper
	 */
	public static Mapper getMapper() {
		return mapper;
	}

	/**
	 * Rapor motoruna lazim olan geleneksel connection u doner
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		return getSession().connection();
	}
	
}
