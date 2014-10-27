package org.uqbarproject.jpa.java8.extras.transaction;

import java.util.function.Supplier;

import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithEntityManager;

public interface TransactionalOps extends WithEntityManager {

   default void withTransaction(Runnable action) {
      withTransaction(() -> {
         action.run();
         return null;
      });
   }

   default <A> A withTransaction(Supplier<A> action) {
      beginTransaction();
      try {
         A result = action.get();
         commitTransaction();
         return result;
      } catch (Throwable e) {
         rollbackTransaction();
         throw e;
      }
   }

   default EntityTransaction getTransaction() {
      return entityManager().getTransaction();
   }

   default EntityTransaction beginTransaction() {
      EntityTransaction tx = getTransaction();

      if (!tx.isActive()) {
         tx.begin();
      }

      return tx;
   }

   default void commitTransaction() {
      EntityTransaction tx = getTransaction();
      if (tx.isActive()) {
         tx.commit();
      }
   }

   default void rollbackTransaction() {
      EntityTransaction tx = getTransaction();
      if (tx.isActive()) {
         tx.rollback();
      }
   }
}
