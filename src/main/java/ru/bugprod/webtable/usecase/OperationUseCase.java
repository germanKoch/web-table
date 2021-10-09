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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
//TODO: сохранение
public class OperationUseCase {

    private final MetadataRepository repo;

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
        return datasetTo;
    }

    public DatasetMetadata addField(String sessionKey, AddFieldRequest request) {
        //TODO: валидация экспрэшина
        var metadata = repo.getAllMetadata(sessionKey);
        var dataset = getDataset(metadata, request.getDataset());
        addNewField(dataset, request.getNewFieldName(), request.getNewFieldType());
        return dataset;
    }

    public void filter(String sessionKey, FilterRequest request) {
        //TODO: валидация экспрешина фильтрации
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

        for (int i = 0; i < path.length; i++) {
            try {
                Field field = getFieldPlain(fieldContainer, path[i]);
                if (field instanceof FieldContainer) {
                    fieldContainer = (FieldContainer) field;
                } else {

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
        return container
                .getFields()
                .stream()
                .filter(fi -> fi.getName().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> {
                    throw new FieldNotFoundException();
                });
    }

}
