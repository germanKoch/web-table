package ru.bugprod.webtable.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.data.TableMetadata;
import ru.bugprod.webtable.repository.MetadataRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetadataUseCase {
    private final MetadataRepository repo;

    public List<TableMetadata> getAllMetadata() {
        return repo.getAllMetadata();
    }

}
