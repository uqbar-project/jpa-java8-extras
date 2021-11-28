package com.github.flbulgarelli.jpa.extras;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.Test;
import com.github.flbulgarelli.jpa.extras.test.AbstractPersistenceTest;

public class AbstractPersistenceTestTest extends AbstractPersistenceTest
    implements WithSimplePersistenceUnit {

  @Test
  public void transactionIsActive() {
    assertTrue(getTransaction().isActive());
  }

  @Test
  public void entityManagerIsAttached() {
    assertTrue(perThreadEntityManagerAccess().isAttached());
  }

  @Test
  public void accessIsActive() {
    assertTrue(perThreadEntityManagerAccess().isActive());
  }
}
