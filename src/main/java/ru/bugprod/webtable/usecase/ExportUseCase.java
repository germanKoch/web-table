package ru.bugprod.webtable.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.io.StreamHolder;
import ru.bugprod.webtable.repository.OperationRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExportUseCase {

    private final OperationRepository repo;

    public StreamHolder export(String sessionKey) {
        String opers = repo.exportOperations(sessionKey);
        if (opers == null) {
            opers = "{}";
        }
        InputStream targetStream = new ByteArrayInputStream(opers.getBytes());
        return new StreamHolder(targetStream, "operations-" + LocalDate.now() + ".txt");
    }

}
