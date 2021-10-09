package ru.bugprod.webtable.repository.mock;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.data.Row;
import ru.bugprod.webtable.model.data.Sample;
import ru.bugprod.webtable.model.data.Value;
import ru.bugprod.webtable.model.metadata.DatasetMetadata;
import ru.bugprod.webtable.model.metadata.Field;
import ru.bugprod.webtable.model.metadata.Struct;

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
                        new Value("route_data",  Map.of("lat", 1001, "lng", 2001))
                )),
                new Row(getList(
                        new Value("shipment_info",  Map.of("date", LocalDate.now(), "target", "test2", "destination", "city3")),
                        new Value("route_data",  Map.of("lat", 1002, "lng", 2002))
                )),
                new Row(getList(
                        new Value("shipment_info",  Map.of("date", LocalDate.now(), "target", "test3", "destination", "city4")),
                        new Value("route_data",  Map.of("lat", 1003, "lng", 2003))
                )),
                new Row(getList(
                        new Value("shipment_info",  Map.of("date", LocalDate.now(), "target", "test1", "destination", "city1")),
                        new Value("route_data",  Map.of("lat", 1004, "lng", 2004))
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
