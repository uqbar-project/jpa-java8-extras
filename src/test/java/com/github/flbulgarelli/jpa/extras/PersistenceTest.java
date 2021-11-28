package com.github.flbulgarelli.jpa.extras;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.junit.jupiter.api.Test;
import com.github.flbulgarelli.jpa.extras.test.AbstractPersistenceTest;

public class PersistenceTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
  @Test
  void persistableIsInserted() {
    Persistable persistable = new Persistable();

    assertNull(persistable.getId());
    entityManager().persist(persistable);
    assertNotNull(persistable.getId());
  }

  @Entity
  @Table(name = "Persistables")
  private static class Persistable {
    @Id
    @GeneratedValue
    private Long id;
    private String aString;

    public Long getId() {
      return id;
    }
  }
}
