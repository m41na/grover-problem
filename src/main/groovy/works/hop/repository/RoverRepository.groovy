package works.hop.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import works.hop.mars.state.Rover

import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

@Repository
@PropertySource("classpath:database.properties")
class RoverRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    UUID create(UUID roverId, String plateau, String position) {
        if (roverId == null) {
            String sql = "insert into tbl_rovers (plateau, position) values (?, ?)"
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                def ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, plateau)
                ps.setString(2, position)
                return ps
            }, keyHolder)

            return (UUID) keyHolder.getKeys()["ROVER_ID"]
        } else {
            String sql = "insert into tbl_rovers (rover_id, plateau, position) values (?, ?, ?)"
            jdbcTemplate.update(sql, { ps ->
                ps.setObject(1, roverId)
                ps.setString(2, plateau)
                ps.setString(3, position)
            })
            return roverId
        }
    }

    List<UUID> existing() {
        return jdbcTemplate.queryForList("select rover_id from tbl_rovers", UUID.class)
    }

    Rover fetch(UUID roverId) {
        return jdbcTemplate.query("select * from tbl_rovers where rover_id = ?", ps -> {
            ps.setObject(1, roverId)
        }, new ResultSetExtractor<Rover>() {

            @Override
            Rover extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return new Rover(rs.getString("plateau"), rs.getString("position"))
                }
                return null
            }
        })
    }

    void update(UUID roverId, String position){
        jdbcTemplate.update("update tbl_rovers set position = ? where rover_id = ?", { ps ->
            ps.setObject(2, roverId)
            ps.setString(1, position)
        })
    }

    void remove(UUID roverId) {
        jdbcTemplate.update("delete from tbl_rovers where rover_id = ?", { ps ->
            ps.setObject(1, roverId)
        })
    }
}
