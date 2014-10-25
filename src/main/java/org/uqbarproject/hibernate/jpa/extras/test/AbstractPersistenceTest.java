package org.uqbarproject.hibernate.jpa.extras.test;

import org.junit.After;
import org.junit.Before;
import org.uqbarproject.hibernate.jpa.extras.EntityManagerOps;
import org.uqbarproject.hibernate.jpa.extras.WithGlobalEntityManager;
import org.uqbarproject.hibernate.jpa.extras.transaction.TransactionalOps;

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
