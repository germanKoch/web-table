package ru.bugprod.webtable.repository.mock;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.data.Row;
import ru.bugprod.webtable.model.data.Sample;
import ru.bugprod.webtable.model.data.Value;
import ru.bugprod.webtable.model.metadata.DatasetMetadata;
import ru.bugprod.webtable.model.metadata.Field;
import ru.bugprod.webtable.model.metadata.Struct;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
public class DatahubMock {

    private List<DatasetMetadata> metadata = new ArrayList<>();
    private Map<String, Sample> data = new HashMap<>();

    public DatahubMock() {
        metadata.add(new DatasetMetadata(
                getList(
                        new Field("field_foo", "string"),
                        new Field("field_bar", "boolean")
                ), "record", "SampleHiveSchema", "com.linkedin.dataset", "Sample Hive dataset"
        ));
        data.put("SampleHiveSchema", new Sample("SampleHiveSchema", getList(
                new Row(getList(
                        new Value("field_foo", "foo1"),
                        new Value("field_bar", true)
                )),
                new Row(getList(
                        new Value("field_foo", "foo2"),
                        new Value("field_bar", false)
                )),
                new Row(getList(
                        new Value("field_foo", "foo3"),
                        new Value("field_bar", true)
                )),
                new Row(getList(
                        new Value("field_foo", "foo4"),
                        new Value("field_bar", false)
                ))
        )));

        metadata.add(new DatasetMetadata(
                getList(
                        new Struct(
                                "shipment_info",
                                "struct",
                                getList(
                                        new Field("date", "date"),
                                        new Field("target", "string"),
                                        new Field("destination", "string")
                                )
                        ),
                        new Struct(
                                "route_data",
                                "struct",
                                getList(
                                        new Field("lat", "number"),
                                        new Field("lng", "number")
                                )
                        )
                ), "record", "SampleHdfsDataset", "com.linkedin.dataset", "Sample Hdfs dataset"
        ));
        data.put("SampleHdfsDataset", new Sample("SampleHdfsDataset", getList(
                new Row(getList(
                        new Value("shipment_info", Map.of("date", LocalDate.now(), "target", "test1", "destination", "city1")),
                        new Value("route_data", Map.of("lat", 1001, "lng", 2001))
                )),
                new Row(getList(
                        new Value("shipment_info", Map.of("date", LocalDate.now(), "target", "test2", "destination", "city3")),
                        new Value("route_data", Map.of("lat", 1002, "lng", 2002))
                )),
                new Row(getList(
                        new Value("shipment_info", Map.of("date", LocalDate.now(), "target", "test3", "destination", "city4")),
                        new Value("route_data", Map.of("lat", 1003, "lng", 2003))
                )),
                new Row(getList(
                        new Value("shipment_info", Map.of("date", LocalDate.now(), "target", "test1", "destination", "city1")),
                        new Value("route_data", Map.of("lat", 1004, "lng", 2004))
                ))
        )));


        metadata.add(new DatasetMetadata(
                getList(
                        new Field("user_name", "string"),
                        new Field("timestamp", "number"),
                        new Field("user_id", "string"),
                        new Field("browser_id", "string"),
                        new Field("session_id", "string"),
                        new Field("deletion_reason", "string")
                ), "record", "fct_users_deleted", "com.linkedin.dataset", "Sample users dataset"
        ));
        data.put("fct_users_deleted", new Sample("fct_users_deleted", getList(
                new Row(getList(
                        new Value("user_name", "German Kochnev"),
                        new Value("timestamp", Instant.now().getEpochSecond()),
                        new Value("user_id", "1"),
                        new Value("browser_id", "бразуер1"),
                        new Value("session_id", "айдишник1"),
                        new Value("deletion_reason", "Прост1")

                )),
                new Row(getList(
                        new Value("user_name", "Borzunov Igor"),
                        new Value("timestamp", Instant.now().getEpochSecond()),
                        new Value("user_id", "1"),
                        new Value("browser_id", "бразуер2"),
                        new Value("session_id", "айдишник2"),
                        new Value("deletion_reason", "Прост2")

                )),
                new Row(getList(
                        new Value("user_name", "Gareev Arslan"),
                        new Value("timestamp", Instant.now().getEpochSecond()),
                        new Value("user_id", "1"),
                        new Value("browser_id", "бразуер3"),
                        new Value("session_id", "айдишник3"),
                        new Value("deletion_reason", "Прост3")

                )),
                new Row(getList(
                        new Value("user_name", "Yakunin Arakdii"),
                        new Value("timestamp", Instant.now().getEpochSecond()),
                        new Value("user_id", "1"),
                        new Value("browser_id", "бразуер4"),
                        new Value("session_id", "айдишник4"),
                        new Value("deletion_reason", "Прост4")

                ))
        )));


        metadata.add(new DatasetMetadata(
                getList(
                        new Field("name", "string"),
                        new Field("price", "number"),
                        new Field("code", "string"),
                        new Struct(
                                "market",
                                "struct",
                                getList(
                                        new Field("name", "string"),
                                        new Field("city", "string"),
                                        new Field("branch_code", "string")
                                )
                        ),
                        new Field("supply_date", "date")
                ), "record", "ProductsSample", "com.linkedin.dataset", "ProductsSample dataset"
        ));
        data.put("ProductsSample", new Sample("ProductsSample", getList(
                new Row(getList(
                        new Value("name", "Мука"),
                        new Value("price", 123),
                        new Value("code", "112312"),
                        new Value("market", Map.of("name", "Магнит2", "city", "Казань", "branch_code", "2004"))

                )),
                new Row(getList(
                        new Value("name", "Масло"),
                        new Value("price", 1231),
                        new Value("code", "1123122"),
                        new Value("market", Map.of("name", "Магнит", "city", "Москва", "branch_code", "2003"))

                )),
                new Row(getList(
                        new Value("name", "Орех"),
                        new Value("price", 1234),
                        new Value("code", "1123123"),
                        new Value("market", Map.of("name", "Магнит1", "city", "Питер", "branch_code", "2002"))

                )),
                new Row(getList(
                        new Value("name", "Сыр"),
                        new Value("price", 1232),
                        new Value("code", "1123124"),
                        new Value("market", Map.of("name", "Магнит", "city", "Новгород", "branch_code", "2001"))

                ))
        )));

        metadata.add(new DatasetMetadata(
                getList(
                        new Field("level", "string"),
                        new Struct(
                                "coordinates",
                                "struct",
                                getList(
                                        new Field("class", "string"),
                                        new Field("string_number", "number"),
                                        new Field("date", "date")
                                )
                        )
                ), "record", "LogSample", "com.linkedin.dataset", "LogSample dataset"
        ));


        metadata.add(new DatasetMetadata(
                getList(
                        new Field("name", "string"),
                        new Field("population", "number"),
                        new Struct(
                                "capital",
                                "struct",
                                getList(
                                        new Field("name", "string"),
                                        new Struct("coordinates", "struct",
                                                getList(new Field("lat", "number"),
                                                        new Field("lng", "number"))
                                        )
                                )
                        )
                ), "record", "CountriesDataset", "com.linkedin.dataset", "CountriesDataset dataset"
        ));


    }

    private List<Field> getList(Field... fields) {
        return new ArrayList<>(Arrays.asList(fields));
    }

    private List<Row> getList(Row... fields) {
        return new ArrayList<>(Arrays.asList(fields));
    }

    private List<Value> getList(Value... fields) {
        return new ArrayList<>(Arrays.asList(fields));
    }

}
