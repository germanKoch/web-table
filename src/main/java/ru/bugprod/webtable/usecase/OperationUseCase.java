package ru.bugprod.webtable.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.controller.request.JoinRequest;
import ru.bugprod.webtable.model.data.DatasetMetadata;
import ru.bugprod.webtable.model.data.Field;
import ru.bugprod.webtable.model.data.FieldContainer;
import ru.bugprod.webtable.model.exception.OperationException;
import ru.bugprod.webtable.repository.MetadataRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationUseCase {

    private final MetadataRepository repo;

    public DatasetMetadata join(String sessionKey, JoinRequest request) {
        var metadata = repo.getAllMetadata(sessionKey);
        var datasetTo = metadata.stream()
                .filter(dataset -> dataset.getName().equals(request.getDataset()))
                .findFirst().orElseThrow(OperationException::new);
        var datasetFrom = metadata.stream()
                .filter(dataset -> dataset.getName().equals(request.getJoinDataset()))
                .findFirst().orElseThrow();
        var fieldTo = getField(datasetTo, request.getField());
        var fieldFrom = getField(datasetFrom, request.getJoinField());

        if (!fieldTo.getType().equals(fieldFrom.getType())) {
            throw new OperationException();
        }
        joinDatasets(datasetTo, datasetFrom);
        return datasetTo;
    }

    private Field getField(DatasetMetadata dataset, String fieldPath) {
        var path = fieldPath.split("\\.");
        FieldContainer fieldContainer = dataset;

        for (int i = 0; i < path.length; i++) {
            String field = path[i];
            Field foundField = fieldContainer
                    .getFields()
                    .stream()
                    .filter(fi -> fi.getName().equals(field))
                    .findFirst()
                    .orElseThrow(() -> {
                        throw new OperationException();
                    });
            if (i != path.length - 1) {
                if (!(foundField instanceof FieldContainer)) {
                    throw new OperationException();
                }
                fieldContainer = (FieldContainer) foundField;
            } else {
                return foundField;
            }
        }
        throw new OperationException();
    }

    private void joinDatasets(DatasetMetadata to, DatasetMetadata from) {
        to.getFields().addAll(from.getFields());
    }

}
