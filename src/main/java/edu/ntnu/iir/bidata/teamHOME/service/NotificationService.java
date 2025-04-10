package edu.ntnu.iir.bidata.teamhome.service;

import edu.ntnu.iir.bidata.teamhome.entitywrapper.TaskInfo;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceIdentifierObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.ResourceObject;
import edu.ntnu.iir.bidata.teamhome.response.meta.Action;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.RecurrenceResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.ResidentsResource;
import edu.ntnu.iir.bidata.teamhome.response.resourceobject.TasksResource;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.CompoundDocumentMeta;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelMeta;
import edu.ntnu.iir.bidata.teamhome.response.toplevel.TopLevelMetaIdentifyer;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for sending notifications to clients. This service uses WebSocket to send messages to
 * clients.
 */
@Service
public class NotificationService {
  private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
  private SimpMessagingTemplate template;

  @Autowired
  public NotificationService(SimpMessagingTemplate template) {
    this.template = template;
  }

  /**
   * Notify the client about the creation of a resident.
   *
   * @param homeId The ID of the home.
   * @param residentResource The resident resource object.
   */
  @Async
  public void notifyResidentCreation(
      String homeId, ResidentsResource residentResource, String clientId) {
    this.template.convertAndSend(
        "/topic/homes/" + homeId + "/residents",
        new TopLevelMeta(residentResource, Action.ADD, clientId));
  }

  /**
   * Notify the client about a task.
   *
   * @param taskId The ID of the task.
   * @param action The action performed on the task (ADD, REMOVE, UPDATE).
   */
  private void notifyTask(int taskId, Action action, String clientId) {
    TaskInfo taskInfo;
    try {
      taskInfo = MysqlService.getInstance().getTaskInfo(taskId);
    } catch (SQLException e) {
      logger.error("Error while getting task info", e);
      return;
    }

    this.template.convertAndSend(
        "/topic/homes/" + taskInfo.getHomeId() + "/tasks",
        new TopLevelMeta(TasksResource.fromEntity(taskInfo.getTask()), action, clientId));
  }

  /**
   * Notify the client about the creation of a task.
   *
   * @param taskId The ID of the task.
   */
  @Async
  public void notifyTaskCreation(int taskId, String clientId) {
    notifyTask(taskId, Action.ADD, clientId);
  }

  /**
   * Notify the client about the removal of a task.
   *
   * @param taskId The ID of the task.
   * @param homeId The ID of the home.
   */
  @Async
  public void notifyTaskRemoval(String homeId, int taskId, String clientId) {
    this.template.convertAndSend(
        "/topic/homes/" + homeId + "/tasks",
        new TopLevelMetaIdentifyer(
            new ResourceIdentifierObject("tasks", Integer.toString(taskId)),
            Action.REMOVE,
            clientId));
  }

  /**
   * Notify the client about the update of a task.
   *
   * @param taskId The ID of the task.
   */
  @Async
  public void notifyTaskUpdate(int taskId, String clientId) {
    notifyTask(taskId, Action.UPDATE, clientId);
  }

  /**
   * Notify the client about the creation of a recurrence.
   *
   * @param taskId The ID of the task.
   */
  @Async
  public void notifyRecurrenceCreation(int taskId, String clientId) {
    TaskInfo taskInfo;
    try {
      taskInfo = MysqlService.getInstance().getTaskInfo(taskId);
    } catch (SQLException e) {
      logger.error("Error while getting task info", e);
      return;
    }
    List<ResourceObject<?>> included = List.of(
        RecurrenceResource.fromEntity(taskInfo.getRecurrence()));

    this.template.convertAndSend(
        "/topic/homes/" + taskInfo.getHomeId() + "/tasks",
        new CompoundDocumentMeta(
            TasksResource.fromEntity(taskInfo.getTask()), included, Action.UPDATE, clientId));
  }
}
