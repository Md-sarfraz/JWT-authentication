package com.jwtAuthentication.jwt.service;

import com.jwtAuthentication.jwt.DTO.requestDto.MovieRequestDto;
import com.jwtAuthentication.jwt.DTO.responseDto.MovieResponseDto;
import com.jwtAuthentication.jwt.model.Movie;
import com.jwtAuthentication.jwt.model.Theater;
import com.jwtAuthentication.jwt.repository.MovieRepository;
import com.jwtAuthentication.jwt.repository.TheaterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;
    public MovieService(MovieRepository movieRepository, TheaterRepository theaterRepository) {
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }

//    public Movie saveMovie(Movie movie, MultipartFile file, int theaterId) {
//        Theater theater=theaterRepository.findById(theaterId).orElseThrow(()->new RuntimeException("Theater not found"+theaterId));
//        movie.setTheater(theater);
//        try {
//            String imageUrl=uploadImg(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return movieRepository.save(movie);
//
//
//    }

//    public String uploadImg(MultipartFile file) throws IOException {
//        String imageUrl = cloudinaryService.uploadImage(file);
//        if (imageUrl.isEmpty()) {
//
//            throw new RuntimeException("Failed to upload image to Cloudinary");
//        }
//
//        return imageUrl;
//    }

    public String deleteMovie(int id) {
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            movieRepository.deleteById(id);
            return "Movie deleted successfully: " + id;
        } else {
            return "Movie not found with ID: " + id;
        }
    }

    public MovieResponseDto updateMovie(MovieRequestDto movieRequestDto, int movieId) {
       Optional<Movie> optionalMovie = movieRepository.findById(movieId);
       if (optionalMovie.isPresent()) {
           Movie updatedMovie = optionalMovie.get();
           updatedMovie.setTitle(movieRequestDto.getTitle());
           updatedMovie.setDescription(movieRequestDto.getDescription());
           updatedMovie.setGenre(movieRequestDto.getGenre());
           updatedMovie.setDuration(movieRequestDto.getDuration());
           updatedMovie.setLanguage(movieRequestDto.getLanguage());
           updatedMovie.setReleaseDate(LocalDate.parse(movieRequestDto.getReleaseDate()));
           updatedMovie.setPostUrl(movieRequestDto.getPostUrl());
           updatedMovie.setRating(movieRequestDto.getRating());
           movieRepository.save(updatedMovie);

           MovieResponseDto movieResponseDto = new MovieResponseDto();
           movieResponseDto.setMovieId(updatedMovie.getMovieId());
           movieResponseDto.setTitle(updatedMovie.getTitle());
           movieResponseDto.setDescription(updatedMovie.getDescription());
           movieResponseDto.setGenre(updatedMovie.getGenre());
           movieResponseDto.setDuration(updatedMovie.getDuration());
           movieResponseDto.setLanguage(updatedMovie.getLanguage());
           movieResponseDto.setReleaseDate(String.valueOf(updatedMovie.getReleaseDate()));
           movieResponseDto.setPostUrl(updatedMovie.getPostUrl());
           movieResponseDto.setRating(updatedMovie.getRating());
           return movieResponseDto;
       }
        else {
            throw new RuntimeException("Movie not found with id: " + movieId);
       }
    }

    public List<Movie> findAllMovie() {
       return movieRepository.findAll();
    }

    public Movie findMovieById(int id) {
          Optional<Movie> movie = movieRepository.findById(id);
           if(movie.isPresent()) {
            return movie.get();
           } else {
               throw new RuntimeException("Movie not found with id: " + id);
       }
    }

    public List<Movie> searchMoviesByTitle(@RequestParam String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> filterByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> filterByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }



    public List<Movie> filterMovies(String language, String genre, String format) {
        if (language != null && genre != null && format != null) {
            return movieRepository.findByLanguageAndGenreAndFormat(language, genre, format);
        } else if (language != null && genre != null) {
            return movieRepository.findByLanguageAndGenre(language, genre);
        } else if (language != null && format != null) {
            return movieRepository.findByLanguageAndFormat(language, format);
        } else if (genre != null && format != null) {
            return movieRepository.findByGenreAndFormat(genre, format);
        } else if (language != null) {
            return movieRepository.findByLanguage(language);
        } else if (genre != null) {
            return movieRepository.findByGenre(genre);
        } else if (format != null) {
            return movieRepository.findByFormat(format);
        } else {
            return movieRepository.findAll();
        }
    }


//    public List<Movie> filterByReleaseDate(LocalDate date) {
//         return movieRepository.findByReleaseDateAfter(date);
//    }
//
//
//    public List<Movie> filterByRating(Double rating) {
//        return movieRepository.findByRatingGreaterThanEqual(rating);
//    }
}
