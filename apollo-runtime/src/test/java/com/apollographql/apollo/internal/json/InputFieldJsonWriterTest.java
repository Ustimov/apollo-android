package com.apollographql.apollo.internal.json;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.ScalarType;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.CustomTypeAdapter;
import com.apollographql.apollo.api.CustomTypeValue;
import com.apollographql.apollo.api.ScalarTypeAdapters;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import okio.Buffer;

import static com.google.common.truth.Truth.assertThat;

public class InputFieldJsonWriterTest {
  private Buffer jsonBuffer;
  private JsonWriter jsonWriter;
  private InputFieldJsonWriter inputFieldJsonWriter;

  @Before
  public void setUp() throws IOException {
    jsonBuffer = new Buffer();
    jsonWriter = JsonWriter.of(jsonBuffer);
    jsonWriter.setSerializeNulls(true);
    jsonWriter.beginObject();

    inputFieldJsonWriter = new InputFieldJsonWriter(jsonWriter,
        new ScalarTypeAdapters(Collections.<ScalarType, CustomTypeAdapter>emptyMap()));
  }

  @Test
  public void writeString() throws IOException {
    inputFieldJsonWriter.writeString("someField", "someValue");
    inputFieldJsonWriter.writeString("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":\"someValue\",\"someNullField\":null");
  }

  @Test
  public void writeInt() throws IOException {
    inputFieldJsonWriter.writeInt("someField", 1);
    inputFieldJsonWriter.writeInt("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":1,\"someNullField\":null");
  }

  @Test
  public void writeLong() throws IOException {
    inputFieldJsonWriter.writeLong("someField", 10L);
    inputFieldJsonWriter.writeLong("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":10,\"someNullField\":null");
  }

  @Test
  public void writeDouble() throws IOException {
    inputFieldJsonWriter.writeDouble("someField", 1.01);
    inputFieldJsonWriter.writeDouble("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":1.01,\"someNullField\":null");
  }

  @Test
  public void writeNumber() throws IOException {
    inputFieldJsonWriter.writeNumber("someField", BigDecimal.valueOf(1.001));
    inputFieldJsonWriter.writeNumber("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":1.001,\"someNullField\":null");
  }

  @Test
  public void writeBoolean() throws IOException {
    inputFieldJsonWriter.writeBoolean("someField", true);
    inputFieldJsonWriter.writeBoolean("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":true,\"someNullField\":null");
  }

  @Test
  public void writeObject() throws IOException {
    inputFieldJsonWriter.writeObject("someField", new InputFieldMarshaller() {
      @Override public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("someField", "someValue");
      }
    });
    inputFieldJsonWriter.writeObject("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":{\"someField\":\"someValue\"},\"someNullField\":null");
  }

  @Test
  public void writeList() throws IOException {
    inputFieldJsonWriter.writeList("someField", new InputFieldWriter.ListWriter() {
      @Override public void write(@NotNull InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
        listItemWriter.writeString("someValue");
      }
    });
    inputFieldJsonWriter.writeList("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":[\"someValue\"],\"someNullField\":null");
  }

  @Test
  public void writeCustomBoolean() throws IOException {
    Map<ScalarType, CustomTypeAdapter> customTypeAdapters = new HashMap<>();
    customTypeAdapters.put(new MockCustomScalarType(CustomTypeValue.GraphQLBoolean.class), new MockCustomTypeAdapter() {
      @NotNull @Override public CustomTypeValue encode(@NotNull Object value) {
        return new CustomTypeValue.GraphQLBoolean((Boolean) value);
      }
    });
    inputFieldJsonWriter = new InputFieldJsonWriter(jsonWriter, new ScalarTypeAdapters(customTypeAdapters));
    inputFieldJsonWriter.writeCustom("someField", new MockCustomScalarType(CustomTypeValue.GraphQLBoolean.class), true);
    inputFieldJsonWriter.writeCustom("someNullField", new MockCustomScalarType(CustomTypeValue.GraphQLBoolean.class), null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":true,\"someNullField\":null");
  }

  @Test
  public void writeCustomNumber() throws IOException {
    Map<ScalarType, CustomTypeAdapter> customTypeAdapters = new HashMap<>();
    customTypeAdapters.put(new MockCustomScalarType(CustomTypeValue.GraphQLNumber.class), new MockCustomTypeAdapter() {
      @NotNull @Override public CustomTypeValue encode(@NotNull Object value) {
        return new CustomTypeValue.GraphQLNumber((Number) value);
      }
    });
    inputFieldJsonWriter = new InputFieldJsonWriter(jsonWriter, new ScalarTypeAdapters(customTypeAdapters));
    inputFieldJsonWriter.writeCustom("someField", new MockCustomScalarType(CustomTypeValue.GraphQLNumber.class), BigDecimal.valueOf(100.1));
    inputFieldJsonWriter.writeCustom("someNullField", new MockCustomScalarType(CustomTypeValue.GraphQLNumber.class), null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":100.1,\"someNullField\":null");
  }

  @Test
  public void writeCustomString() throws IOException {
    Map<ScalarType, CustomTypeAdapter> customTypeAdapters = new HashMap<>();
    customTypeAdapters.put(new MockCustomScalarType(CustomTypeValue.GraphQLString.class), new MockCustomTypeAdapter() {
      @NotNull @Override public CustomTypeValue encode(@NotNull Object value) {
        return new CustomTypeValue.GraphQLString((String) value);
      }
    });
    inputFieldJsonWriter = new InputFieldJsonWriter(jsonWriter, new ScalarTypeAdapters(customTypeAdapters));
    inputFieldJsonWriter.writeCustom("someField", new MockCustomScalarType(CustomTypeValue.GraphQLString.class), "someValue");
    inputFieldJsonWriter.writeCustom("someNullField", new MockCustomScalarType(CustomTypeValue.GraphQLString.class), null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":\"someValue\",\"someNullField\":null");
  }

  @Test
  public void writeCustomJsonObject() throws IOException {
    final Map<String, Object> value = new UnmodifiableMapBuilder<String, Object>()
        .put("stringField", "string")
        .put("booleanField", true)
        .put("numberField", new BigDecimal(100))
        .put("listField", Arrays.asList(
            "string",
            true,
            new BigDecimal(100),
            new UnmodifiableMapBuilder<String, Object>()
                .put("stringField", "string")
                .put("numberField", new BigDecimal(100))
                .put("booleanField", true)
                .put("listField", Arrays.asList(1, 2, 3))
                .build()
            )
        )
        .put("objectField", new UnmodifiableMapBuilder<String, Object>()
            .put("stringField", "string")
            .put("numberField", new BigDecimal(100))
            .put("booleanField", true)
            .put("listField", Arrays.asList(1, 2, 3))
            .build()
        )
        .build();

    inputFieldJsonWriter.writeCustom("someField", new MockCustomScalarType(Map.class), value);
    inputFieldJsonWriter.writeCustom("someNullField", new MockCustomScalarType(Map.class), null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":{\"objectField\":{\"numberField\":100,\"booleanField\":true,\"listField\":[1,2,3],\"stringField\":\"string\"},\"numberField\":100,\"booleanField\":true,\"listField\":[\"string\",true,100,{\"numberField\":100,\"booleanField\":true,\"listField\":[1,2,3],\"stringField\":\"string\"}],\"stringField\":\"string\"},\"someNullField\":null");
  }

  @Test
  public void writeCustomList() throws IOException {
    final List<Object> value = Arrays.asList(
        "string",
        true,
        new BigDecimal(100),
        new UnmodifiableMapBuilder<String, Object>()
            .put("stringField", "string")
            .put("numberField", new BigDecimal(100))
            .put("booleanField", true)
            .put("listField", Arrays.asList(1, 2, 3))
            .build()
    );

    inputFieldJsonWriter.writeCustom("someField", new MockCustomScalarType(List.class), value);
    inputFieldJsonWriter.writeCustom("someNullField", new MockCustomScalarType(List.class), null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":[\"string\",true,100,{\"numberField\":100,\"booleanField\":true,\"listField\":[1,2,3],\"stringField\":\"string\"}],\"someNullField\":null");
  }

  @Test
  public void writeListOfList() throws IOException {
    inputFieldJsonWriter.writeList("someField", new InputFieldWriter.ListWriter() {
      @Override public void write(@NotNull InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
        listItemWriter.writeList(new InputFieldWriter.ListWriter() {
          @Override public void write(@NotNull InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
            listItemWriter.writeString("someValue");
          }
        });
      }
    });
    inputFieldJsonWriter.writeList("someNullField", null);
    assertThat(jsonBuffer.readUtf8()).isEqualTo("{\"someField\":[[\"someValue\"]],\"someNullField\":null");
  }

  private class MockCustomScalarType implements ScalarType {
    final Class clazz;

    MockCustomScalarType(Class clazz) {
      this.clazz = clazz;
    }

    @Override public String typeName() {
      return clazz.getSimpleName();
    }

    @Override public Class javaType() {
      return clazz;
    }

    @Override public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof MockCustomScalarType)) return false;

      MockCustomScalarType that = (MockCustomScalarType) o;

      return clazz.equals(that.clazz);
    }

    @Override public int hashCode() {
      return clazz.hashCode();
    }
  }

  private abstract class MockCustomTypeAdapter implements CustomTypeAdapter {
    @Override public Object decode(@NotNull CustomTypeValue value) {
      throw new UnsupportedOperationException();
    }
  }
}
