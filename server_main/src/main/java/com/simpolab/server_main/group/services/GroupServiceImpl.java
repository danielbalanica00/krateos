package com.simpolab.server_main.group.services;

import com.simpolab.server_main.db.ElectorDAO;
import com.simpolab.server_main.db.GroupDAO;
import com.simpolab.server_main.elector.domain.Elector;
import com.simpolab.server_main.group.domain.Group;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupDAO groupDAO;
  private final ElectorDAO electorDAO;

  @Override
  public void newGroup(String groupName) {
    try {
      groupDAO.create(groupName);
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Group getGroup(Long id) {
    return groupDAO.get(id);
  }

  @Override
  public List<Group> getGroups() {
    return groupDAO.getAll();
  }

  @Override
  public List<Elector> getElectorsInGroup(Long id) {
    var electors = electorDAO.getAllInGroup(id);

    electors.forEach(elector -> elector.getUser().sanitize());

    return electors;
  }

  @Override
  public void deleteGroup(Long id) {}

  @Override
  public void addElector(Long groupId, Long electorId) {
    try {
      groupDAO.addElector(groupId, electorId);
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void removeElector(Long groupId, Long electorId) {
    groupDAO.removeElector(groupId, electorId);
  }
}
