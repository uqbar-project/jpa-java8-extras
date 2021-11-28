package com.github.flbulgarelli.jpa.extras;

import javax.persistence.EntityManager;

public interface WithGlobalEntityManager extends WithEntityManager {

  default EntityManager entityManager() {
    return PerThreadEntityManagers.get();
  }
}
