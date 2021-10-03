package ru.bugprod.webtable.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.io.FileItemTableStreamHolder;
import ru.bugprod.webtable.model.io.TableStreamHolder;
import ru.bugprod.webtable.repository.DataFrameRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class IOController {

    private final DataFrameRepository repository;

    @PostMapping("/import")
    public ResponseEntity<String> handleUpload(HttpServletRequest request) throws Exception {
        var upload = new ServletFileUpload();
        var iterStream = upload.getItemIterator(request);
        while (iterStream.hasNext()) {
            FileItemStream item = iterStream.next();
            String name = item.getFieldName();
            TableStreamHolder holder = new FileItemTableStreamHolder(item, name);
            repository.importData("1", holder);
        }
        return ResponseEntity.ok("Ok!");
    }


    @GetMapping(value = "/export")
    public void getFile(HttpServletResponse response) throws Exception {
        var holder = repository.exportData("1");
        response.setHeader("Content-Disposition", "attachment; filename=" + holder.getTableName());
        try (var stream = holder.openStream()) {
            org.apache.commons.io.IOUtils.copy(stream, response.getOutputStream());
        }
        response.flushBuffer();
    }
}
