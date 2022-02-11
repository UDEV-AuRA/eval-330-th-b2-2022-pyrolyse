package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.repository.AlbumRepository;
import com.ipiecoles.java.eval.th330.repository.ArtistRepository;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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
    @RequestMapping(method = RequestMethod.GET, value = "/artist/new")
    public String newArtist(final ModelMap model) {
        model.put("artist", new Artist());
        return  "detailArtist";
    }
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value = "/artists/new")
    public RedirectView newArtists(Artist artist) {
        if (artist.getId() != null) {
            artist = artistService.updateArtiste(artist.getId(), artist);
        } else {
            artist = artistService.creerArtiste(artist);
        }
        return new RedirectView("/artists/" + artist.getId());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/artists/{id}/delete")
    public RedirectView deleteArtist(@PathVariable(value = "id") Long id) {
        artistService.deleteArtist(id);
        return new RedirectView("/");
    }
    @RequestMapping(method = RequestMethod.GET, value="/artists/albums/delete/{id}")
    public RedirectView deleteAlbum(@PathVariable(value = "id") Long albumId) {
        albumService.deleteAlbum(albumId);
        return new RedirectView("/");
    }
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value = "/artists/albums/new")
    public RedirectView newAlbum(Album album) {
        album = albumService.creerAlbum(album);
        return new RedirectView("/artists/" + album.getArtist().getId());
    }





}
