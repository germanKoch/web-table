package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.usecase.ExportUseCase;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IOController {

    private final ExportUseCase useCase;

    @ApiOperation(value = "Экспортировать операции в текстовый файл.",
            responseHeaders = {@ResponseHeader(name = "Content-Disposition", description = "attachment; filename=Название_датасета")})
    @GetMapping(value = "/export", produces = MediaType.TEXT_PLAIN_VALUE)
    public void getFile(@RequestHeader String sessionKey, HttpServletResponse response) throws Exception {
        var holder = useCase.export(sessionKey);
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + holder.getFileName());
        try (var stream = holder.openStream()) {
            org.apache.commons.io.IOUtils.copy(stream, response.getOutputStream());
        }
        response.flushBuffer();
    }
}
