package org.uqbarproject.jpa.java8.extras;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerThreadEntityManagersTest {

  @BeforeEach
  public void closeEntityManager() {
    PerThreadEntityManagers.dispose();
  }

  @Test
  public void entityManagerIsNotInitiallyAttached() {
    assertFalse(PerThreadEntityManagers.isAttached());
  }
  
  @Test
  public void entityManagerIsAttachedOnDemand() {
    assertNotNull(PerThreadEntityManagers.get());
    assertTrue(PerThreadEntityManagers.isAttached());
  }
  
  @Test
  public void theSameEntityManagerIsConsistentlyReturned() {
    assertSame(PerThreadEntityManagers.get(), PerThreadEntityManagers.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfDisposed() {
    EntityManager e1 = PerThreadEntityManagers.get();
    
    PerThreadEntityManagers.dispose();
    
    assertNotSame(e1, PerThreadEntityManagers.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfExternallyClosed() {
    EntityManager e1 = PerThreadEntityManagers.get();
    
    e1.close();
    
    assertNotSame(e1, PerThreadEntityManagers.get());
  }  
}
