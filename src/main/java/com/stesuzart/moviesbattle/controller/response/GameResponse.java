package com.stesuzart.moviesbattle.controller.response;


import java.util.List;

public record GameResponse(String id, List<RoundResponse> round) {
}
