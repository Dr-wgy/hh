package com.makenv.model.mc.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JacksonUtil {
  private final static ObjectMapper JSON_MAPPER = new ObjectMapper();
  private final static ObjectMapper CSV_MAPPER = new CsvMapper();
  private final static ObjectMapper YML_MAPPER = new ObjectMapper(new YAMLFactory());
  static final Logger logger;

  static {
    logger = LoggerFactory.getLogger(JacksonUtil.class);
  }


  public static void writeObjToJsonFile(Object object, String file) throws IOException {
    try (Writer writer = Files.newBufferedWriter(Paths.get(file))) {
      JSON_MAPPER.writeValue(writer, object);
    }
  }

  public static String objToJson(Object object) throws JsonProcessingException {
    return JSON_MAPPER.writeValueAsString(object);
  }

  public static <T> T jsonToObj(String json, Class clazz) throws IOException {
    JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    T t = (T) JSON_MAPPER.readValue(json, clazz);
    return t;
  }

  public static <T> T ymlToObj(File ymlFile, Class clazz) throws IOException {
    T t = (T) YML_MAPPER.readValue(ymlFile, clazz);
    return t;
  }

  public static <T> List<T> readFromCsv(File dataFile, Class<T> tClass) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    MappingIterator<T> iterator;
    iterator = CSV_MAPPER.readerFor(tClass).with(schema).readValues(dataFile);
    return iterator.readAll();
  }

  public static <T> List<T> readFromCsv(String content, Class<T> tClass) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    MappingIterator<T> iterator;
    iterator = CSV_MAPPER.readerFor(tClass).with(schema).readValues(content);
    return iterator.readAll();
  }

  public static <T> List<T> readArrayFromCsv(String content, Class<T> tClass) throws IOException {
    CsvMapper mapper = new CsvMapper();
    mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
    MappingIterator<T> it = mapper.readerFor(tClass).readValues(content);
    return it.readAll();
  }

  public static <T> T readFromJsonFile(String file, Class<?> objClass) throws IOException {
    File dataFile = new File(file);
    if (!dataFile.exists()) {
      return null;
    }
    ArrayType type = JSON_MAPPER.getTypeFactory().constructArrayType(objClass);
    return JSON_MAPPER.readValue(dataFile, type);
  }

  public static <T> T readFromJsonFile(String file, Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
    File dataFile = new File(file);
    if (!dataFile.exists()) {
      return null;
    }
    CollectionType type = JSON_MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    return JSON_MAPPER.readValue(dataFile, type);
  }

  public static <T> T readFromJsonFile(String file, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) throws IOException {
    File dataFile = new File(file);
    if (!dataFile.exists()) {
      return null;
    }
    return readFromJsonFile(dataFile, mapClass, keyClass, valueClass);
  }

  public static <T> T readFromJsonFile(File dataFile, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) throws IOException {
    MapType type = JSON_MAPPER.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    return JSON_MAPPER.readValue(dataFile, type);
  }

  public static <T> T jsonToObj(String content, Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
    CollectionType type = JSON_MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    return JSON_MAPPER.readValue(content, type);
  }

  public static <T> T jsonToObj(String content, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) throws IOException {
    MapType type = JSON_MAPPER.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    return JSON_MAPPER.readValue(content, type);
  }

  /**
   * 非深度合并两个对象
   *
   * @param m1
   * @param m2
   * @param <T>
   * @return
   * @throws IOException
   */
  public static <T> T merge(Map m1, Map m2) throws IOException {
    ObjectReader updater = JSON_MAPPER.readerForUpdating(m1);
    String m2Str = objToJson(m2);
    return updater.readValue(m2Str);
  }

  public static <T> T mergeDepth(Map m1, Map m2) throws Exception {
    JsonNode m1JsonNode = (JsonNode) JSON_MAPPER.readTree(JSON_MAPPER.writeValueAsString(m1));
    JsonNode m2JsonNode = (JsonNode) JSON_MAPPER.readTree(JSON_MAPPER.writeValueAsString(m2));
    m1JsonNode = mergeD(m1JsonNode, m2JsonNode);
    return JSON_MAPPER.convertValue(m1JsonNode, (Class<T>) Map.class);
  }

  private static JsonNode mergeD(final JsonNode target, final JsonNode source) {
    if (target instanceof ArrayNode && source instanceof ArrayNode) {
      ((ArrayNode) target).addAll((ArrayNode) source);
      return target;
    } else if (target instanceof ObjectNode && source instanceof ObjectNode) {
      final Iterator<Map.Entry<String, JsonNode>> iterator = source.fields();
      while (iterator.hasNext()) {
        final Map.Entry<String, JsonNode> sourceFieldEntry = iterator.next();
        final JsonNode targetFieldValue = target.get(sourceFieldEntry.getKey());
        if (targetFieldValue != null) {
          final JsonNode newTargetFieldValue = mergeD(targetFieldValue, sourceFieldEntry.getValue());
          ((ObjectNode) target).set(sourceFieldEntry.getKey(), newTargetFieldValue);
        } else {
          ((ObjectNode) target).set(sourceFieldEntry.getKey(), sourceFieldEntry.getValue().deepCopy());
        }
      }
      return target;
    } else {
      return source.deepCopy();
    }
  }
}
