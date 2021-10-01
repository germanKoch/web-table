package ru.bugprod.webtable.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bugprod.webtable.model.io.FileItemStreamHolder;
import ru.bugprod.webtable.model.io.StreamHolder;
import ru.bugprod.webtable.repository.DataFrameRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class IOController {

    private final DataFrameRepository repository;

    @PostMapping("/import")
    public ResponseEntity<String> handleUpload(HttpServletRequest request) throws Exception {
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterStream = upload.getItemIterator(request);
        while (iterStream.hasNext()) {
            FileItemStream item = iterStream.next();
            String name = item.getFieldName();
            StreamHolder holder = new FileItemStreamHolder(item);
            repository.importData("1", name, holder);
        }
        return ResponseEntity.ok("Ok!");
    }


    @GetMapping(value = "/export")
    public void getFile(HttpServletResponse response) throws Exception {
        String filename = "test";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        var holder = repository.exportData("1");
        org.apache.commons.io.IOUtils.copy(holder.openStream(), response.getOutputStream());
        response.flushBuffer();
    }
}
