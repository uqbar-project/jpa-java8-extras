package com.github.flbulgarelli.jpa.extras.test;

import com.github.flbulgarelli.jpa.extras.EntityManagerOps;
import com.github.flbulgarelli.jpa.extras.TransactionalOps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public interface PersistenceTest extends TransactionalOps, EntityManagerOps {

  @BeforeEach
  default void setupTransaction() {
    beginTransaction();
  }

  @AfterEach
  default void tearDownTransaction() {
    rollbackTransaction();
  }

}
