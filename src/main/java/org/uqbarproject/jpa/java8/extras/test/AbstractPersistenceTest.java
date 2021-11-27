package org.uqbarproject.jpa.java8.extras.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class AbstractPersistenceTest implements
		TransactionalOps, EntityManagerOps {

	@BeforeEach
	public void setup() {
		beginTransaction();
	}

	@AfterEach
	public void tearDown() {
		rollbackTransaction();
	}

}
