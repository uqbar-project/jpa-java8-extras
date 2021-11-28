package com.github.flbulgarelli.jpa.extras;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.github.flbulgarelli.jpa.extras.test.AbstractPersistenceTest;

public class AbstractPersistenceTestTest extends AbstractPersistenceTest
    implements WithGlobalEntityManager {

  @Test
  public void transactionIsActive() {
    assertTrue(getTransaction().isActive());
  }
}
