package ru.bugprod.webtable.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.controller.request.AddFieldRequest;
import ru.bugprod.webtable.controller.request.FilterRequest;
import ru.bugprod.webtable.controller.request.JoinRequest;
import ru.bugprod.webtable.model.data.DatasetMetadata;
import ru.bugprod.webtable.model.data.Field;
import ru.bugprod.webtable.model.data.FieldContainer;
import ru.bugprod.webtable.model.data.Struct;
import ru.bugprod.webtable.model.exception.FieldNotFoundException;
import ru.bugprod.webtable.model.exception.OperationException;
import ru.bugprod.webtable.repository.MetadataRepository;
import ru.bugprod.webtable.repository.OperationRepository;
import ru.bugprod.webtable.repository.entity.OperationType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationUseCase {

    private final MetadataRepository repo;
    private final OperationRepository opRepo;

    public DatasetMetadata join(String sessionKey, JoinRequest request) {
        var metadata = repo.getAllMetadata(sessionKey);
        var datasetTo = getDataset(metadata, request.getDataset());
        var datasetFrom = getDataset(metadata, request.getJoinDataset());
        var fieldTo = getField(datasetTo, request.getField());
        var fieldFrom = getField(datasetFrom, request.getJoinField());
        if (!fieldTo.getType().equals(fieldFrom.getType())) {
            throw new OperationException();
        }
        joinDatasets(datasetTo, datasetFrom);

        opRepo.saveOperation(sessionKey, request, OperationType.JOIN);
        return datasetTo;
    }

    public DatasetMetadata addField(String sessionKey, AddFieldRequest request) {
        //TODO: валидация экспрэшина
        var metadata = repo.getAllMetadata(sessionKey);
        var dataset = getDataset(metadata, request.getDataset());
        addNewField(dataset, request.getNewFieldName(), request.getNewFieldType());

        opRepo.saveOperation(sessionKey, request, OperationType.COMPUTE_FIELD);
        return dataset;
    }

    public void filter(String sessionKey, FilterRequest request) {
        opRepo.saveOperation(sessionKey, request, OperationType.FILTER);
    }

    private DatasetMetadata getDataset(List<DatasetMetadata> datasets, String name) {
        return datasets.stream()
                .filter(dataset -> dataset.getName().equals(name))
                .findFirst().orElseThrow(OperationException::new);
    }

    private Field getField(DatasetMetadata dataset, String fieldPath) {
        var path = fieldPath.split("\\.");
        FieldContainer fieldContainer = dataset;

        for (int i = 0; i < path.length; i++) {
            Field foundField = getFieldPlain(fieldContainer, path[i]);
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

    private void addNewField(DatasetMetadata dataset, String fieldPath, String fieldType) {
        var path = fieldPath.split("\\.");
        FieldContainer fieldContainer = dataset;

        for (var i = 0; i < path.length; i++) {
            try {
                var field = getFieldPlain(fieldContainer, path[i]);
                if (field instanceof FieldContainer) {
                    fieldContainer = (FieldContainer) field;
                } else {
                    throw new OperationException();
                }
            } catch (FieldNotFoundException e) {
                Field field;
                if (i == path.length - 1) {
                    field = new Field(path[i], fieldType);
                    fieldContainer.getFields().add(field);
                } else {
                    field = new Struct(path[i], "struct", new ArrayList<>());
                    fieldContainer.getFields().add(field);
                    fieldContainer = (FieldContainer) field;
                }
            }
        }
    }

    private void joinDatasets(DatasetMetadata to, DatasetMetadata from) {
        to.getFields().addAll(from.getFields());
    }

    private Field getFieldPlain(FieldContainer container, String fieldName) {
        List<Field> fields =  container
                .getFields()
                .stream()
                .filter(fi -> fi.getName().equals(fieldName))
                .collect(Collectors.toList());
        if (fields.size() >= 2) {
            throw new OperationException();
        }
        return fields.get(0);
    }

}
