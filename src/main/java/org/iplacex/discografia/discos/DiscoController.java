package org.iplacex.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (artistaRepository.existsById(disco.idArtista)) {
            Disco savedDisco = discoRepository.save(disco);
            return new ResponseEntity<>(savedDisco, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return new ResponseEntity<>(discoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable String id) {
        Optional<Disco> disco = discoRepository.findById(id);
        return disco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}
