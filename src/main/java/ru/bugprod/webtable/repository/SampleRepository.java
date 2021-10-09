package ru.bugprod.webtable.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.bugprod.webtable.model.data.Sample;
import ru.bugprod.webtable.repository.mock.DatahubMock;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleRepository {

    private final DatahubMock mock;

    public List<Sample> getAllSamples() {
        return new ArrayList<>(mock.getData().values());
    }

    public Sample getSample(String table) {
        return mock.getData().get(table);
    }

}
