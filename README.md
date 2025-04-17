# TwitchBets
This REST API is a simple betting system that allows users to bet on various events related Twitch streams. 

It is built using Spring Boot.

# REST API

## Create a bet question

### Request

`POST /bets/questions`

    curl --location 'http://localhost:8181/bets/questions' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "question": "Will @xQc stream today? (02/16/2025)",
            "options": [
                "Yes",
                "No"
            ],
            "endTime": "2025-06-11 18:04:56"
        }'

### Response

    HTTP/1.1 201 Created
    Location: http://localhost:8181/bets/questions/5c41aaee-4772-441e-957a-9805f7dad34e
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Wed, 05 Mar 2025 12:53:15 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    []

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
            "id": "e4585195-6185-4522-a118-1a21a99dd9a7",
            "question": "Will @xQc stream today? (02/16/2025)",
            "options": [
                {
                    "id": "757c3f15-c78e-4179-a3d0-8f928ac8f618",
                    "option": "Yes",
                    "bets": [],
                    "currentAmount": 0.0
                },
                {
                    "id": "57f415d7-97de-42e5-bae5-c5fa4cbde505",
                    "option": "No",
                    "bets": [],
                    "currentAmount": 0.0
                }
            ],
            "currentOddsOfOptions": {
                "757c3f15-c78e-4179-a3d0-8f928ac8f618": "NaN",
                "57f415d7-97de-42e5-bae5-c5fa4cbde505": "NaN"
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

## Place a bet

### Request

    `POST /bets/

    curl --location 'http://localhost:8181/bets' \
    --header 'Content-Type: application/json' \
    --data '{
        "username": "user1",
        "amount": 10.0,
        "betQuestionId": "1c2ab561-37b2-44e9-9979-ae685f5d6958",
        "betOptionId": "153dfe05-6f92-4751-8cad-5f210701b9ae"
    }'

### Response

    HTTP/1.1 201 Created
    Content-Length: 0
    Date: Wed, 05 Mar 2025 13:06:54 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    []
