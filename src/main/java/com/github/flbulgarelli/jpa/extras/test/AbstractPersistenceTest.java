package com.github.flbulgarelli.jpa.extras.test;

import com.github.flbulgarelli.jpa.extras.EntityManagerOps;
import com.github.flbulgarelli.jpa.extras.transaction.TransactionalOps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractPersistenceTest implements
        TransactionalOps, EntityManagerOps {

  @BeforeEach
  public void setup() {
    beginTransaction();
  }

  @AfterEach
  public void tearDown() {
    rollbackTransaction();
  }

}
