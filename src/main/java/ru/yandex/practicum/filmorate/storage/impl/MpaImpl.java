package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class MpaImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getMpaById(int MpaId) {
        String sqlQuery = "select * " +
                "from MPA " +
                "where mpa_id = ?";
        final List<Mpa> mpa = jdbcTemplate.query(sqlQuery, MpaImpl::makeMpa, MpaId);
        if (mpa.size() != 1) {
            return null;
        }
        return mpa.get(0);
    }


    static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("MPA_ID"),
                rs.getString("MPA_NAME")
        );
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sqlQuery = "select * " +
                "from MPA ";
        List <Mpa> listAllMpa = jdbcTemplate.query(sqlQuery, MpaImpl::makeMpa);
        return  listAllMpa;
    }

}
