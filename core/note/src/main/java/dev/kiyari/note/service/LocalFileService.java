package dev.kiyari.note.service;

import dev.kiyari.note.util.exception.UnexpectedException;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class LocalFileService {

    public LocalFileService() throws IOException {
    }

    public void write(String title, String text) {
        try (FileWriter writer = new FileWriter(title)){
            writer.write(text);

        } catch (IOException exception) {
            throw new UnexpectedException("Unexpected exception while writing text to local temp file");
        }
    }
}
