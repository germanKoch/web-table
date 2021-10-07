package ru.bugprod.webtable.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.data.TableFragment;
import ru.bugprod.webtable.repository.DataFrameRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationUseCase {
    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 10;

    private final DataFrameRepository repo;

    public TableFragment getBetween(String key, int offset, int limit) {
        return repo.getBetween(key, offset, limit);
    }

    public TableFragment computeAndSave(String key, String columnName, String expr) {
        repo.computeAndSave(key, columnName, expr);
        return repo.getBetween(key, DEFAULT_OFFSET, DEFAULT_LIMIT);
    }

    public TableFragment filterByCondition(String key, String expr) {
        repo.filterByCondition(key, expr);
        return repo.getBetween(key, DEFAULT_OFFSET, DEFAULT_LIMIT);
    }
}
