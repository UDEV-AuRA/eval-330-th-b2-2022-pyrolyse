package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.repository.AlbumRepository;
import com.ipiecoles.java.eval.th330.repository.ArtistRepository;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class artists {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/{id}"
    )public String artist(@PathVariable(value = "id") Long id, final ModelMap model) {
        Artist artist = artistService.findById(id);
        model.put("artist", artist);
        return "detailArtist";
    }
    @RequestMapping(
            method = RequestMethod.GET,
            value = "artists"
    )
    public String FindArtist(@RequestParam(value ="names", required = false) String name,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         @RequestParam(value = "name", defaultValue = "name") String sortProperty,
                                         @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                                         final ModelMap model) {

        if (name == null || name.isEmpty()) {
                Page<Artist> artist = artistService.findAllArtists(page, size, sortProperty, sortDirection);
                model.put("artist", artist);
                model.put("start", page * size + 1);
                model.put("end", page * size + artist.getNumberOfElements());
                model.put("page", page + 1);
        } else {
            Page<Artist> artists = artistService.findByNameLikeIgnoreCase(name, page, size, sortProperty, sortDirection);
            model.put("artist", artists);
            model.put("start", page * size + 1);
            model.put("end", page * size + artists.getNumberOfElements());
            model.put("page", page + 1);
        }
        return "listeArtists";
    }


}
