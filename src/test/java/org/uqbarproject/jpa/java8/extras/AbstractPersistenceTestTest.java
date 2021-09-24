package org.uqbarproject.jpa.java8.extras;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class AbstractPersistenceTestTest extends AbstractPersistenceTest
    implements WithGlobalEntityManager {

  @Test
  public void transactionIsActive() {
    assertTrue(getTransaction().isActive());
  }
}
