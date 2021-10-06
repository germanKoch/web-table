package ru.bugprod.webtable.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.io.FileItemTableStreamHolder;
import ru.bugprod.webtable.model.io.TableStreamHolder;
import ru.bugprod.webtable.repository.DataFrameRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IOController {

    private final DataFrameRepository repository;

    @ApiOperation(value = "Импортировать файл в формате csv.", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/import")
    public ResponseEntity<String> handleUpload(@RequestHeader String sessionKey, HttpServletRequest request) throws Exception {
        var upload = new ServletFileUpload();
        var iterStream = upload.getItemIterator(request);
        while (iterStream.hasNext()) {
            FileItemStream item = iterStream.next();
            String name = item.getFieldName();
            TableStreamHolder holder = new FileItemTableStreamHolder(item, name);
            repository.importData(sessionKey, holder);
        }
        return ResponseEntity.ok("Ok!");
    }

    @ApiOperation(value = "Экспортировать таблицу в формат csv.",
            produces = MediaType.TEXT_PLAIN_VALUE,
            responseHeaders = {@ResponseHeader(name = "Content-Disposition", description = "attachment; filename=Название_датасета")})
    @GetMapping(value = "/export")
    public void getFile(@RequestHeader String sessionKey, HttpServletResponse response) throws Exception {
        var holder = repository.exportData(sessionKey);
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + holder.getTableName());
        try (var stream = holder.openStream()) {
            org.apache.commons.io.IOUtils.copy(stream, response.getOutputStream());
        }
        response.flushBuffer();
    }
}
