package edu.ntnu.iir.bidata.teamhome.config;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.ntnu.iir.bidata.teamhome.util.NullableOptional;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A custom TypeAdapterFactory for serializing and deserializing {@link NullableOptional} types.
 * This factory handles the serialization and deserialization of NullableOptional objects, which can
 * contain null values.
 *
 * @see NullableOptional
 */
public class NullableOptionalTypeAdapterFactory implements TypeAdapterFactory {

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
    if (!NullableOptional.class.isAssignableFrom(typeToken.getRawType())) {
      return null;
    }

    Type actualType = ((ParameterizedType) typeToken.getType()).getActualTypeArguments()[0];

    @SuppressWarnings("unchecked")
    TypeAdapter<Object> valueAdapter =
        (TypeAdapter<Object>) gson.getAdapter(TypeToken.get(actualType));

    @SuppressWarnings("unchecked")
    TypeAdapter<T> result = (TypeAdapter<T>) new NullableOptionalAdapter<>(valueAdapter);
    return result;
  }

  private static class NullableOptionalAdapter<T> extends TypeAdapter<NullableOptional<T>> {
    private final TypeAdapter<T> valueAdapter;

    public NullableOptionalAdapter(TypeAdapter<T> valueAdapter) {
      this.valueAdapter = valueAdapter;
    }

    @Override
    public void write(JsonWriter out, NullableOptional<T> optional) throws IOException {
      if (optional == null) {
        out.nullValue();
        return;
      }

      optional.ifPresentOrElse(
          value -> {
            try {
              valueAdapter.write(out, value);
            } catch (IOException e) {
              throw new JsonIOException(e);
            }
          },
          () -> {
            try {
              out.nullValue();
            } catch (IOException e) {
              throw new JsonIOException(e);
            }
          });
    }

    @Override
    public NullableOptional<T> read(JsonReader in) throws IOException {
      T value = valueAdapter.read(in);
      return NullableOptional.of(value); // value can be null
    }
  }
}
