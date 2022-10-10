package com.github.flbulgarelli.jpa.extras;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.junit.jupiter.api.Test;
import com.github.flbulgarelli.jpa.extras.test.PersistenceTest;

public class PersistenceTestTest implements PersistenceTest, WithSimplePersistenceUnit {

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
