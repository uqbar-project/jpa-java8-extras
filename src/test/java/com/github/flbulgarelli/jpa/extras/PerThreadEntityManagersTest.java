package com.github.flbulgarelli.jpa.extras;

import javax.persistence.EntityManager;

import static com.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit.SIMPLE_PERSISTENCE_UNIT_NAME;
import static org.junit.jupiter.api.Assertions.*;

import com.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerThreadEntityManagersTest {

  private PerThreadEntityManagerAccess access;

  @BeforeEach
  public void disposeEntityManager() {
    access = new PerThreadEntityManagerAccess(SIMPLE_PERSISTENCE_UNIT_NAME);
  }

  @Test
  public void entityManagerCanBeConfiguredBeforeInitialization() {
    assertDoesNotThrow(
        () -> access.setProperty("hibernate.connection.url", "jdbc:h2:mem:test"));
  }

  @Test
  public void entityManagerCannotBeConfiguredAfterInitialization() {
    access.dispose();

    assertThrows(IllegalStateException.class,
        () -> access.setProperty("hibernate.connection.url", "jdbc:h2:mem:test"));
  }

  @Test
  public void entityManagerIsNotInitiallyAttached() {
    access.dispose();

    assertFalse(access.isAttached());
  }
  
  @Test
  public void entityManagerIsAttachedOnDemand() {
    access.dispose();

    assertNotNull(access.get());
    assertTrue(access.isAttached());
  }
  
  @Test
  public void theSameEntityManagerIsConsistentlyReturned() {
    access.dispose();

    assertSame(access.get(), access.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfDisposed() {
    access.dispose();

    EntityManager e1 = access.get();
    
    access.dispose();
    
    assertNotSame(e1, access.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfExternallyClosed() {
    access.dispose();

    EntityManager e1 = access.get();
    
    e1.close();
    
    assertNotSame(e1, access.get());
  }  
}
