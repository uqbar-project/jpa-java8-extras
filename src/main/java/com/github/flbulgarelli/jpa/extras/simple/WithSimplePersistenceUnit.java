package com.github.flbulgarelli.jpa.extras.simple;

import com.github.flbulgarelli.jpa.extras.EntityManagerOps;
import com.github.flbulgarelli.jpa.extras.TransactionalOps;
import com.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerAccess;
import com.github.flbulgarelli.jpa.extras.perthread.WithPerThreadEntityManager;

/**
 * This mixin-style interface allows implementors
 * to have simple, rich-interfaced, per-thread access to an entity manager
 * created from a persistence unit named "simple-persistence-unit".
 */
public interface WithSimplePersistenceUnit extends WithPerThreadEntityManager, EntityManagerOps, TransactionalOps {

  String SIMPLE_PERSISTENCE_UNIT_NAME = "simple-persistence-unit";
  PerThreadEntityManagerAccess PER_THREAD_ENTITY_MANAGER_ACCESS = new PerThreadEntityManagerAccess(SIMPLE_PERSISTENCE_UNIT_NAME);

  @Override
  default PerThreadEntityManagerAccess perThreadEntityManagerAccess() {
    return PER_THREAD_ENTITY_MANAGER_ACCESS;
  }

  /**
   * Disposes the current entity manager and
   * prevents new entity managers to be allocated globally.
   * <p>
   * This method does not close
   * other active managers, so they should be closed before or data may be lost
   *
   * @see PerThreadEntityManagerAccess#shutdown()
   */
  static void shutdown() {
    dispose();
    PER_THREAD_ENTITY_MANAGER_ACCESS.shutdown();
  }

  /**
   * Disposes the current entity manager attached to the current thread, if any.
   * As an additional validation, it will fail if transaction is active
   *
   * @see PerThreadEntityManagerAccess#dispose()
   */
  static void dispose() {
    if (PER_THREAD_ENTITY_MANAGER_ACCESS.isAttached()) {
      if (PER_THREAD_ENTITY_MANAGER_ACCESS.get().getTransaction().isActive()) {
        throw new IllegalStateException("Can not dispose entity manager if a transaction is active. Ensure it has been already terminated");
      }
      PER_THREAD_ENTITY_MANAGER_ACCESS.dispose();
    }
  }
}