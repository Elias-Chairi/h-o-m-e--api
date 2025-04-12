package edu.ntnu.iir.bidata.teamhome.config;

import com.google.gson.Gson;
import java.io.EOFException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * WebSocket configuration class.
 * This class configures the WebSocket message broker and the STOMP endpoints.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

  private final Gson gson;

  public WebSocketConfig(Gson gson) {
    this.gson = gson;
  }

  /**
   * Configure the message converters.
   * This method adds a GsonMessageConverter to the list of message converters.
   */
  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    messageConverters.clear();
    messageConverters.add(new GsonMessageConverter(gson));
    return false;
  }

  /**
   * Configure the message broker.
   * "/topic" is the message broker that clients can subscribe to.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
  }

  /**
   * Register the "/websocket" endpoint.
   * This endpoint is used to establish a WebSocket connection.
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket");
  }

  /**
   * Configure the WebSocket transport.
   * This method allows for customizing the WebSocket transport settings.
   */
  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setDecoratorFactories(new WebSocketHandlerDecoratorFactory() {
      @Override
      public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
          @Override
          public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            if (exception instanceof EOFException) {
              logger.info("Client disconnected: " + session.getId());
              return;
            }
            super.handleTransportError(session, exception);
          }
        };
      }
    });
  }
}
