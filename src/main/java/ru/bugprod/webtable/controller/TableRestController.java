package ru.bugprod.webtable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.data.TableFragment;
import ru.bugprod.webtable.repository.DataFrameRepository;

@RequiredArgsConstructor
@RestController
public class TableRestController {

    private final DataFrameRepository repo;

    @GetMapping("/get")
    public TableFragment getFragment() {
        repo.getBetween("1", 10, 20);
        return null;
    }

}
