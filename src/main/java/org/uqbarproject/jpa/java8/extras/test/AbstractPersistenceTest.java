package org.uqbarproject.jpa.java8.extras.test;

import org.junit.After;
import org.junit.Before;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class AbstractPersistenceTest implements
		WithGlobalEntityManager, TransactionalOps, EntityManagerOps {

	@Before
	public void setup() {
		beginTransaction();
	}

	@After
	public void tearDown() {
		rollbackTransaction();
	}

}
