package ru.bugprod.webtable.repository.mock;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.data.Field;
import ru.bugprod.webtable.model.data.Struct;
import ru.bugprod.webtable.model.data.TableMetadata;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class DatahubMock {

    private List<TableMetadata> data = new ArrayList<>();

    public DatahubMock() {
        data.add(new TableMetadata(
                List.of(
                        new Field("field_foo", "string"),
                        new Field("field_bar", "boolean")
                ), "record", "SampleHiveSchema", "com.linkedin.dataset", "Sample Hive dataset"
        ));
        data.add(new TableMetadata(
                List.of(
                        new Struct(
                                "shipment_info",
                                "struct",
                                List.of(
                                        new Field("date", "date"),
                                        new Field("target", "string"),
                                        new Field("destination", "string")
                                )
                        ),
                        new Struct(
                                "field_bar",
                                "boolean",
                                List.of(
                                        new Field("lat", "number"),
                                        new Field("lng", "number")
                                )
                        )
                ), "record", "SampleHdfsDataset", "com.linkedin.dataset", "Sample Hdfs dataset"
        ));
        data.add(new TableMetadata(
                List.of(
                        new Field("user_name", "string"),
                        new Field("timestamp", "number"),
                        new Field("user_id", "string"),
                        new Field("browser_id", "string"),
                        new Field("session_id", "string"),
                        new Field("deletion_reason", "string")
                ), "record", "fct_users_deleted","com.linkedin.dataset","Sample users dataset"
        ));
    }


}
