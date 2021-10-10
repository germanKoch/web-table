package ru.bugprod.webtable.usecase;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.client.DataHubClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataHubUseCase {

    private final DataHubClient dataHubClient;

    private final String username = "datahub";
    private final String password = "datahub";

    private final String testData = "{\n" +
            "  search(input:{type: DATASET, query: \"a\", start:0, count: 10 }){\n" +
            "    start\n" +
            "    count\n" +
            "    total\n" +
            "    searchResults{\n" +
            "    entity{\n" +
            "      urn\n" +
            "      type\n" +
            "      ...on Dataset {\n" +
            "        name\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "  }\n" +
            "}";
    private static Map<String, String> dataHubClientCookies;

    @PostConstruct
    @SneakyThrows
    public void setUp() {
        dataHubClientCookies = dataHubClient.auth(username, password);
    }

    public String getData() {
        try {
            return dataHubClient.graphRequest(testData, dataHubClientCookies);
        } catch (IOException e) {
            return "Ошибка получения метаданных";
        }
    }
}
