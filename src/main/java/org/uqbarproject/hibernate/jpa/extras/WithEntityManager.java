package org.uqbarproject.hibernate.jpa.extras;


import javax.persistence.EntityManager;

public interface WithEntityManager {

   EntityManager entityManager();
}
