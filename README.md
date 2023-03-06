# moviesbattle

This project is a cool card game of movies! :) 

Where the player needs to guess what movie has the best rating o/

### First of all you need to know this technologies
This project use the following stacks:

- Java 17
- Spring Boot 2.7.8
- Spring Doc OpenApi 1.6.14
- Jsoup 1.15.4
- Basic Authorization

### How to Play
All requests you will need to put the `Authorization` on `Header`.

The all users is authenticated in memory. (y)

#### Let's start!!

1. you need start a game and it will return two movies
2. what movie do you think that has the best rating? And the worst ranting? So, get `movieId` of each and put it in the next request. 

`POST /game/start`

- Response
```
{
    "id": "cf94d996-9848-4821-855e-84f2aeda92bb",
    "round": [
        {
            "movieId": "tt12844910",
            "title": "Pathaan"
        },
        {
            "movieId": "tt0059742",
            "title": "A Noviça Rebelde"
        }
    ]
}
```


3. So now, you will need to put in path the `gameId` and in the body put the winner and loser movie throught `movieId`.
4. The response will be the next quiz and repeat again the step 3 until you lose.

`POST /game/{gameId}/quiz`
- Resquest
```
{
    "movieWinnerId": "tt12844910",
    "movieLoserId": "tt0059742"
}
```
- Response
```
[
    {
        "movieId": "tt0105793",
        "title": "Quanto Mais Idiota Melhor"
    },
    {
        "movieId": "tt24328636",
        "title": "Paul T. Goldman"
    }
]
```
