package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
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
    private IDiscoRepository discoRepo;
    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Discos> HandlePostDiscoRequest(@RequestBody Discos disc){
        if (disc.idArtista == null || !artistaRepo.existsById(disc.idArtista)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
        Discos temp = discoRepo.insert(disc);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Discos>> HandleGetDiscosRequest(){
        return new ResponseEntity<>(discoRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Discos> HandleGetDiscoRequest(@PathVariable ("id") String id){
        Optional<Discos> temp = discoRepo.findById(id);

        if (!temp.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(), HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Discos>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id){
        if (!artistaRepo.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Discos> discosArtistas = discoRepo.findDiscosbyIdArtista(id);

        return new ResponseEntity<>(discosArtistas, HttpStatus.OK);
    }

}
