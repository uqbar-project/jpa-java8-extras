package org.uqbarproject.jpa.java8.extras;


import javax.persistence.EntityManager;

public interface WithEntityManager {

  EntityManager entityManager();
}
