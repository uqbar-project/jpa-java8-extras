package com.github.flbulgarelli.jpa.extras.simple;

import com.github.flbulgarelli.jpa.extras.EntityManagerOps;
import com.github.flbulgarelli.jpa.extras.PerThreadEntityManagers;
import com.github.flbulgarelli.jpa.extras.transaction.TransactionalOps;
import com.github.flbulgarelli.jpa.extras.WithGlobalEntityManager;

public interface SimpleEntityManagerAccess extends WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  /**
   * Disposes the current entity manager and
   * prevents new entity managers to be allocated globally.
   * <p>
   * This method does not close
   * other active managers, so they should be closed before or data may be lost
   *
   * @see PerThreadEntityManagers#shutdown()
   */
  public static void shutdown() {
    dispose();
    PerThreadEntityManagers.shutdown();
  }

  /**
   * Disposes the current entity manager attached to the current thread, if any.
   * As an additional validation, it will fail if transaction is active
   *
   * @see PerThreadEntityManagers#dispose()
   */
  public static void dispose() {
    if (PerThreadEntityManagers.isAttached()) {
      if (PerThreadEntityManagers.get().getTransaction().isActive()) {
        throw new IllegalStateException("Can not dispose entitiy manager if a transaction is active. Ensure it has been already terminated");
      }
      PerThreadEntityManagers.dispose();
    }
  }

}
