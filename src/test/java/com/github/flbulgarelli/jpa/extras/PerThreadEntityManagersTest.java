package com.github.flbulgarelli.jpa.extras;

import javax.persistence.EntityManager;

import static com.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit.PER_THREAD_ENTITY_MANAGER_ACCESS;
import static org.junit.jupiter.api.Assertions.*;

import com.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerThreadEntityManagersTest {

  private PerThreadEntityManagerAccess access = PER_THREAD_ENTITY_MANAGER_ACCESS;

  @BeforeEach
  public void disposeEntityManager() {
    access.dispose();
  }

  @Test
  public void entityManagerIsNotInitiallyAttached() {
    assertFalse(access.isAttached());
  }
  
  @Test
  public void entityManagerIsAttachedOnDemand() {
    assertNotNull(access.get());
    assertTrue(access.isAttached());
  }
  
  @Test
  public void theSameEntityManagerIsConsistentlyReturned() {
    assertSame(access.get(), access.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfDisposed() {
    EntityManager e1 = access.get();
    
    access.dispose();
    
    assertNotSame(e1, access.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfExternallyClosed() {
    EntityManager e1 = access.get();
    
    e1.close();
    
    assertNotSame(e1, access.get());
  }  
}
