package org.iplacex.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista savedArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(savedArtista, HttpStatus.CREATED);
    }

    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return new ResponseEntity<>(artistaRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable String id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        return artista.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (artistaRepository.existsById(id)) {
            artista._id = id;
            Artista updatedArtista = artistaRepository.save(artista);
            return new ResponseEntity<>(updatedArtista, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
