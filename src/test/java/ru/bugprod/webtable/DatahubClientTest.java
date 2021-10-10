package ru.bugprod.webtable;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.bugprod.webtable.usecase.DataHubUseCase;

@SpringBootTest
public class DatahubClientTest {

    @Autowired
    DataHubUseCase useCase;

    @Test
    public void dataHub() {
        System.out.println(useCase.getData());
    }

}
