package com.stesuzart.moviesbattle.controller;

import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;
import com.stesuzart.moviesbattle.service.GameService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@OpenAPIDefinition(info = @Info(title = "Movies Battle API"))
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Operation(summary = "Create a game")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Create a new game successfully",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GameResponse.class)
                    )
                )
            )
        }
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, example = " Basic {token}")
    @PostMapping("/game/start")
    public ResponseEntity<?> createGame(){
        GameResponse game = gameService.createGame();
        return ResponseEntity.ok(game);
    }

    @Operation(summary = "Create a quiz", description = "This API will create new quiz everytime when input the winner and loser movieId.")
    @ApiResponses(
        value = {

            @ApiResponse(
                responseCode = "200",
                description = "Create a new quiz/round successfully",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(implementation = RoundResponse.class),
                                maxItems = 2
                        )
                )),
            @ApiResponse(
                responseCode = "404",
                description = "The round was not found"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Game Over - The user has reached the maximum number of errors."
            )

        }
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, example = " Basic {token}")
    @PostMapping("/game/{id}/quiz")
    public ResponseEntity<?> nextQuiz(
            @Parameter(description = "gameId", required = true)  @PathVariable String id,
            @RequestBody NextQuizRequest request){
        List<RoundResponse> round = gameService.nextQuiz(id, request);
        return ResponseEntity.ok(round);
    }

}
