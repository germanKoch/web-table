package ru.bugprod.webtable.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.bugprod.webtable.model.data.TableMetadata;
import ru.bugprod.webtable.repository.mock.DatahubMock;

import java.util.List;

@Repository
@AllArgsConstructor
public class MetadataRepository {

    private DatahubMock data;

    public List<TableMetadata> getAllMetadata() {
        return data.getData();
    }

}
