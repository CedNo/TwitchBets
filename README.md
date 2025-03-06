# TwitchBets
This REST API is a simple betting system that allows users to bet on various events related Twitch streams. 

It is built using Spring Boot.

# REST API

## Create a bet question

### Request

`POST /bets/questions`

    curl --location 'http://localhost:8181/bets/questions' \
        --header 'Content-Type: application/json' \
        --data-raw '{"question": "Will @xQc stream today? (02/16/2025)","options": ["Yes","No"]}'

### Response

    HTTP/1.1 201 Created
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Wed, 05 Mar 2025 12:53:15 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {"id":"5c41aaee-4772-441e-957a-9805f7dad34e"}

## Get a bet question

### Request

`GET /bets/questions/{questionId}`

    curl --location 'http://localhost:8181/bets/questions/5c41aaee-4772-441e-957a-9805f7dad34e'

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Wed, 05 Mar 2025 12:53:15 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {
        "betQuestion": {
            "id": "5c41aaee-4772-441e-957a-9805f7dad34e",
            "question": "Will @xQc stream today? (02/16/2025)",
            "options": [
                {
                    "id": "197a0932-416a-4259-8eec-c51ef3a5eeba",
                    "currentAmount": 0.0
                },
                {
                    "id": "aa26cd55-b1ae-4fcb-96b4-ecacd8447bf8",
                    "currentAmount": 0.0
                }
            ],
            "currentOddsOfOptions": {
                "aa26cd55-b1ae-4fcb-96b4-ecacd8447bf8": "NaN",
                "197a0932-416a-4259-8eec-c51ef3a5eeba": "NaN"
            },
            "currentBettedAmount": 0.0
        }
    }

## Create a user

### Request

`POST /users/{username}`

    curl --location --request POST 'http://localhost:8181/users/user1'

### Response

    HTTP/1.1 201 Created
    Content-Length: 0
    Date: Wed, 05 Mar 2025 13:06:54 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    []

## Get a user

### Request

`GET /users/{username}`

    curl --location 'http://localhost:8181/users/user1'

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Wed, 05 Mar 2025 13:06:54 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    "user": {
        "username": "user1"
    }