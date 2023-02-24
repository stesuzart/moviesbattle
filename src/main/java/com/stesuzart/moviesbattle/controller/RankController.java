package com.stesuzart.moviesbattle.controller;

import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.entity.Ranking;
import com.stesuzart.moviesbattle.service.GameService;
import com.stesuzart.moviesbattle.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RankController {

    @Autowired
    private RankingService rankingService;

    @Operation(summary = "Get Ranking", description = "Get score and user's rank")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Get ranking successfully",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ranking.class)
                    )
                )
            )
        }
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, example = " Basic {token}")
    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking(){
        return ResponseEntity.ok(rankingService.getRanking());
    }
}
