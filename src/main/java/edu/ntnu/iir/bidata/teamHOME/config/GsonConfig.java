package edu.ntnu.iir.bidata.teamhome.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObject;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToMany;
import edu.ntnu.iir.bidata.teamhome.response.jsonapi.RelationshipObjectToOne;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/** Configuration class for Gson. */
@Configuration
public class GsonConfig {

  /**
   * Custom adapter for serializing and deserializing LocalDate objects. Formats LocalDate objects
   * as "yyyy-mm-dd".
   */
  private class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      return LocalDate.parse(json.getAsString(), formatter);
    }

    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(date.format(formatter));
    }
  }

  /**
   * Custom adapter for serializing and deserializing RelationshipObject objects. Handles
   * {@link RelationshipObjectToOne} and {@link RelationshipObjectToMany} types.
   */
  private class RelationshipObjectAdapter implements JsonDeserializer<RelationshipObject> {
    @Override
    public RelationshipObject deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      JsonObject object = json.getAsJsonObject();
      JsonElement data = object.get("data");
      if (data.isJsonNull()) {
        return new RelationshipObjectToOne(null);
      } else if (data.isJsonArray()) {
        return context.deserialize(object, RelationshipObjectToMany.class);
      } else if (data.isJsonObject()) {
        return context.deserialize(object, RelationshipObjectToOne.class);
      } else {
        throw new IllegalArgumentException("Invalid relationship object");
      }
    }
  }

  /**
   * Gson bean. Used for JSON serialization.
   *
   * @return Gson bean
   */
  @Bean
  public Gson gson() {
    return new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .registerTypeAdapter(RelationshipObject.class, new RelationshipObjectAdapter())
        .registerTypeAdapterFactory(new NullableOptionalTypeAdapterFactory())
        .create();
  }

  /**
   * GsonHttpMessageConverter bean. Ensures that Gson is used for JSON serialization.
   *
   * @param gson Gson bean
   * @return GsonHttpMessageConverter bean
   */
  @Bean
  public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
    GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
    converter.setGson(gson);
    return converter;
  }
}
