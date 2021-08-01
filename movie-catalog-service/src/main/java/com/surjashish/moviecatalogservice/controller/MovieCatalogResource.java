package com.surjashish.moviecatalogservice.controller;

import com.surjashish.moviecatalogservice.Model.CatalogItem;
import com.surjashish.moviecatalogservice.Model.Movie;
import com.surjashish.moviecatalogservice.Model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

//        RestTemplate restTemplate = new RestTemplate();
        //Make a bean of RestTemplate to avoid initializing it multiple times over and over whenever this API is getting called.



        //get all rated movie IDs
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 5)
        );
        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Desc", rating.getRating());
        }).collect(Collectors.toList());


        //FOr each movie ID, call movie info service and get details
        //put them all together

    }
}
