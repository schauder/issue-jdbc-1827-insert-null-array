package be.testcontainertest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJdbcRepositories(considerNestedRepositories = true)
@ContextConfiguration(classes = {
		SomeTableRepository.class
})
@Sql(executionPhase = BEFORE_TEST_METHOD, value = {
		"classpath:0_init.sql",
		"classpath:1_load.sql"
})
@Sql(executionPhase = AFTER_TEST_METHOD, value = "classpath:2_clean.sql")
class ByteTest extends MssqlContainerBaseTest {

	@Autowired
	NamedParameterJdbcOperations template;

	@Autowired
	private SomeTableRepository someTableRepository;

	@Test
	void testByteArrayNotNull() {
		final SomeTable record = new SomeTable(null, "abc".getBytes());
		someTableRepository.save(record);
	}

	@Test
	void testByteArrayNull() {
		final SomeTable record = new SomeTable(null, null);
		someTableRepository.save(record);
	}

	@Test
	void testByteArrayEmpty() {
		final SomeTable record = new SomeTable(null, new byte[]{});
		someTableRepository.save(record);
	}

	@Test
	void templateNullArray() {
		template.update(
				"INSERT INTO \"SOME_TABLE\" (\"CONTENT\") VALUES (:CONTENT)",
				new MapSqlParameterSource("CONTENT", null)
		);
	}

}