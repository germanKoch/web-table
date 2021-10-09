package ru.bugprod.webtable.repository.mock;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.data.DatasetMetadata;
import ru.bugprod.webtable.model.data.Field;
import ru.bugprod.webtable.model.data.Struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Component
public class DatahubMock {

    private List<DatasetMetadata> data = new ArrayList<>();

    public DatahubMock() {
        data.add(new DatasetMetadata(
                getList(
                        new Field("field_foo", "string"),
                        new Field("field_bar", "boolean")
                ), "record", "SampleHiveSchema", "com.linkedin.dataset", "Sample Hive dataset"
        ));
        data.add(new DatasetMetadata(
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
        data.add(new DatasetMetadata(
                getList(
                        new Field("user_name", "string"),
                        new Field("timestamp", "number"),
                        new Field("user_id", "string"),
                        new Field("browser_id", "string"),
                        new Field("session_id", "string"),
                        new Field("deletion_reason", "string")
                ), "record", "fct_users_deleted", "com.linkedin.dataset", "Sample users dataset"
        ));
        data.add(new DatasetMetadata(
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
        data.add(new DatasetMetadata(
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
        data.add(new DatasetMetadata(
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
        data.add(new DatasetMetadata(
                getList(
                        new Field("name", "string"),
                        new Field("average_height", "number"),
                        new Field("age", "number")
                ), "record", "GardenTreesDataset", "com.linkedin.dataset", "GardenTreesDataset dataset"
        ));
        data.add(new DatasetMetadata(
                getList(
                        new Field("name", "string"),
                        new Field("name", "number"),
                        new Struct(
                                "students_info",
                                "struct",
                                getList(
                                        new Field("students_num", "number"),
                                        new Field("average_rate", "number")
                                )
                        ),
                        new Struct(
                                "teachers_info",
                                "struct",
                                getList(
                                        new Field("teachers_num", "number"),
                                        new Field("average_salary", "number")
                                )
                        )
                ), "record", "UniversDataset", "com.linkedin.dataset", "UniversDataset dataset"
        ));
    }

    private List<Field> getList(Field... fields) {
        return new ArrayList<>(Arrays.asList(fields));
    }


}
