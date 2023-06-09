package com.sutoga.backend.controller;

import com.sutoga.backend.entity.Recommendation;
import com.sutoga.backend.entity.response.GameResponse;
import com.sutoga.backend.entity.response.RecommendationResponse;
import com.sutoga.backend.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final SteamAPIService steamAPIService;

//    @PostMapping
//    public Game createGame(@RequestBody Game game) {
//        return gameService.createGame(game);
//    }

//    @GetMapping
//    public List<Game> getAllGamesByParameter(@RequestParam Optional<String> genreName,
//                                                     @RequestParam Optional<String> categoryName) {
//        return gameService.getAllGamesByParameter(genreName, categoryName);
//    }

//    @GetMapping("/{gameId}")
//    public Game getGameById(@PathVariable Long gameId) {
//        return gameService.getGameById(gameId);
//    }



    @DeleteMapping("/{gameId}")
    public void deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
    }

//    @PostMapping("/loadUserGames")
//    public ResponseEntity<List<Long>> loadUserGames(@RequestParam Long userId) {
//        try {
//            List<Long> ownedGames = steamAPIService.fetchUserOwnedGames(userId);
//            return ResponseEntity.ok(ownedGames);
//        } catch (RuntimeException ex) {
//            // handle the exception, you can return a suitable error message or status code
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @GetMapping("/getUserGames/{userId}")
//    public ResponseEntity<Page<GameResponse>> getUserGames(@PathVariable Long userId,
//                                                           @RequestParam(defaultValue = "0") int pageNumber,
//                                                           @RequestParam(defaultValue = "500") int pageSize) {
//        Page<GameResponse> gameResponses = gameService.getUserGames(userId, PageRequest.of(pageNumber, pageSize));
//        return new ResponseEntity<>(gameResponses, HttpStatus.OK);
//    }

    @GetMapping("/getUserGames/{userId}")
    public ResponseEntity<List<GameResponse>> getUserGames(@PathVariable Long userId) {
        List<GameResponse> gameResponses = gameService.getUserGames(userId);
        return new ResponseEntity<>(gameResponses, HttpStatus.OK);
    }


    @GetMapping("/getUserGameCount/{userId}")
    public ResponseEntity<Integer> getUserGameCountByUserId(@PathVariable Long userId) {
        Integer gameCount = gameService.getUserGameCount(userId);
        return new ResponseEntity<>(gameCount, HttpStatus.OK);
    }

    @GetMapping("/getUserGameCountByUsername/{username}")
    public ResponseEntity<Integer> getUserGameCountByUsername(@PathVariable String username) {
        Integer gameCount = gameService.getUserGameCountByUsername(username);
        return new ResponseEntity<>(gameCount, HttpStatus.OK);
    }

    @PostMapping("/startFetchUserGames/{userId}")
    public ResponseEntity<?> startFetchUserGames(@PathVariable Long userId) {
        gameService.fetchUserGames(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fetchRecommendations/{userId}")
    public ResponseEntity<List<RecommendationResponse>> fetchRecommendations(@PathVariable Long userId) {
        return ResponseEntity.ok(gameService.getRecommendationByUserId(userId));
    }

    @GetMapping("/getRecommendations/{userId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendations(@PathVariable Long userId) {
        return ResponseEntity.ok(gameService.getRecommendationsFromDatabase(userId));
    }

}
