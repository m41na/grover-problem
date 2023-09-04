package works.hop.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import javax.sql.DataSource

@TestPropertySource("classpath:test-database.properties")
class RoverRepositorySpec extends Specification {

    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:jdbc/create-schema.sql")
            .addScript("classpath:jdbc/initial-data.sql")
            .build()

    RoverRepository repo = new RoverRepository(jdbcTemplate: new JdbcTemplate(dataSource: dataSource))

    def "create rover entity when no record already exists"() {
        given: "given initial values"
        var plateau = "8 9"
        var position = "1 2 N"

        when: "create function for repository is called"
        UUID roverId = repo.create(null, plateau, position)

        then: "non-null uuid value is returned"
        roverId != null
    }

    def "create rover entity when some record already exists"() {
        given: "given initial values"
        var id = UUID.fromString('718cd3b0-30a0-4d55-90a7-5bfafcb8bbb9')
        var plateau = "8 9"
        var position = "1 2 N"

        when: "create function for repository is called"
        repo.create(id, plateau, position)

        then: "integrity exception should be thrown"
        thrown(RuntimeException)
    }

    def "fetch an existing record using an id"() {
        given: "the id of an existing rover"
        var id = UUID.fromString('718cd3b0-30a0-4d55-90a7-5bfafcb8bbb9')

        when: "fetch function is called with the id"
        var rover = repo.fetch(id)

        then: "rover result is not null"
        rover != null
        rover.mx == 13
        rover.my == 13
        rover.position() == "0 0 W"
    }

    def "fetch list of existing rover ids"() {
        when: "existing function is called on repo"
        List<UUID> ids = repo.existing()

        then: "the exists rovers"
        !ids.isEmpty()
    }

    def "update an existing rover"() {
        given: "given existing rover id"
        var id = UUID.fromString('718cd3b0-30a0-4d55-90a7-5bfafcb8bbb9')

        when: "rover position is updated"
        repo.update(id, "4 4 S")

        and: "rover is fetched again"
        var rover = repo.fetch(id)

        then:
        rover != null
        rover.mx == 13
        rover.my == 13
        rover.position() == "4 4 S"
    }

    def "remove a rover"() {
        given: "given existing rover id"
        var id = UUID.fromString('718cd3b0-30a0-4d55-90a7-5bfafcb8bbb9')

        when: "list of existing rover ids is fetched"
        List<UUID> beforeDelete = repo.existing()

        and: "rover is removed"
        repo.remove(id)

        and: "list of existing rover ids is fetched again"
        List<UUID> afterDelete = repo.existing()

        then: "id exists is 'before' list but does not exist is 'after' list"
        beforeDelete.contains(id)
        !afterDelete.contains(id)
    }
}
