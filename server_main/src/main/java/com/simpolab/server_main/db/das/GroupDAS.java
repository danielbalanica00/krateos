package com.simpolab.server_main.db.das;

import com.simpolab.server_main.db.GroupDAO;
import com.simpolab.server_main.group.domain.Group;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GroupDAS implements GroupDAO {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<Group> groupRowMapper = (rs, rowNum) ->
    new Group(rs.getLong("id"), rs.getString("name"));

  @Override
  public void create(String name) throws SQLException {
    try {
      String query = "INSERT IGNORE INTO voting_group (name) VALUES (?)";
      jdbcTemplate.update(query, name);
    } catch (Exception e) {
      log.error("Failed to create new group");
      throw new SQLException("Failed to create new elector", e);
    }
    log.info("Group created successfully");
  }

  @Override
  public Group get(Long id) {
    String query = "SELECT * FROM voting_group WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(query, groupRowMapper, id);
    } catch (Exception e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  @Override
  public List<Group> getAll() {
    String query = "SELECT * FROM voting_group";
    try {
      return jdbcTemplate.query(query, groupRowMapper);
    } catch (Exception e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  @Override
  public void delete(Long id) {}

  @Override
  public void addElector(Long groupId, Long electorId) throws SQLException {
    try {
      String query = "INSERT IGNORE INTO elector_group (elector_id, voting_group_id) VALUES (?, ?)";
      jdbcTemplate.update(query, electorId, groupId);
    } catch (Exception e) {
      log.error("Failed to create new group");
      throw new SQLException("Failed to add elector to group", e);
    }
    log.info("Elector added to the group successfully");
  }

  @Override
  public void removeElector(Long groupId, Long electorId) {
    var query = "DELETE FROM elector_group WHERE elector_id = ? AND voting_group_id = ?";
    try {
      jdbcTemplate.update(query, electorId, groupId);
      log.info("Elector {} removed from group {} successfully", electorId, groupId);
    } catch (Exception e) {
      log.error("Failed to remove elector {} from group {}", electorId, groupId, e);
    }
  }
}
