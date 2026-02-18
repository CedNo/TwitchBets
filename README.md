# TwitchBets
This REST API is a simple betting system that allows players to bet on various events related Twitch streams. 

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
        "id": "2bf9bc80-3cd5-4869-9123-e92f2b136f8e",
        "question": "Will @xQc stream today? (08/19/2025)",
        "options": [
            {
                "id": "fda37c03-a3cb-492c-bc07-2c344fb38f1e",
                "option": "Yes",
                "bets": [
                    {
                        "username": "Edgouille",
                        "amount": 100.0,
                        "createdAt": "2025-08-13T22:34:02.2322474",
                        "betWin": 0.0
                    },
                    {
                        "username": "Edgouille",
                        "amount": 100.0,
                        "createdAt": "2025-08-13T22:34:23.7552183",
                        "betWin": 0.0
                    }
                ],
                "odds": 0.6666667,
                "currentAmount": 200.0
            },
            {
                "id": "5334ced4-d28e-44e0-8c0f-236ace12da46",
                "option": "No",
                "bets": [
                    {
                        "username": "Edgouille",
                        "amount": 100.0,
                        "createdAt": "2025-08-13T22:34:40.0367351",
                        "betWin": 0.0
                    }
                ],
                "odds": 0.33333334,
                "currentAmount": 100.0
            }
        ],
        "endTime": "2025-08-22T00:00:00",
        "currentBettedAmount": 300.0
    }

## Get ending bet questions

### Request

`GET /bets/questions/ending?amount={amount}`

    curl --location 'http://localhost:8181/bets/questions/ending?amount=2'

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Mon, 11 Aug 2025 21:54:06 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    [
        {
            "id": "c9ed83c9-8667-4261-987a-3ffdd9f1ede2",
            "question": "Will @xQc stream today? (08/19/2025)",
            "options": [
                {
                    "id": "add0506f-67f5-4c6b-b326-49d22246b5d0",
                    "option": "Yes",
                    "bets": [],
                    "odds": 0.0,
                    "currentAmount": 0.0
                },
                {
                    "id": "b06f7457-c855-45b4-bc59-2d9a89ae1bfe",
                    "option": "No",
                    "bets": [],
                    "odds": 0.0,
                    "currentAmount": 0.0
                }
            ],
            "endTime": "2025-08-22T00:00:00",
            "currentBettedAmount": 0.0
        },
        {
            "id": "2bf9bc80-3cd5-4869-9123-e92f2b136f8e",
            "question": "Will @xQc stream today? (08/19/2025)",
            "options": [
                {
                    "id": "fda37c03-a3cb-492c-bc07-2c344fb38f1e",
                    "option": "Yes",
                    "bets": [
                        {
                            "username": "Edgouille",
                            "amount": 100.0,
                            "createdAt": "2025-08-13T22:34:02.2322474",
                            "betWin": 0.0
                        },
                        {
                            "username": "Edgouille",
                            "amount": 100.0,
                            "createdAt": "2025-08-13T22:34:23.7552183",
                            "betWin": 0.0
                        }
                    ],
                    "odds": 0.6666667,
                    "currentAmount": 200.0
                },
                {
                    "id": "5334ced4-d28e-44e0-8c0f-236ace12da46",
                    "option": "No",
                    "bets": [
                        {
                            "username": "Edgouille",
                            "amount": 100.0,
                            "createdAt": "2025-08-13T22:34:40.0367351",
                            "betWin": 0.0
                        }
                    ],
                    "odds": 0.33333334,
                    "currentAmount": 100.0
                }
            ],
            "endTime": "2025-08-22T00:00:00",
            "currentBettedAmount": 300.0
        }
    ]

## Create a player

### Request

`POST /players/{username}`

    curl --location --request POST 'http://localhost:8181/players/player1'

### Response

    HTTP/1.1 201 Created
    Content-Length: 0
    Date: Wed, 05 Mar 2025 13:06:54 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    []

## Get a player

### Request

`GET /players/{username}`

    curl --location 'http://localhost:8181/players/player1'

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Wed, 05 Mar 2025 13:06:54 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {
        "username": "player1",
        "balance": 1000.0
    }

## Place a bet

### Request

    `POST /bets/

    curl --location 'http://localhost:8181/bets' \
    --header 'Content-Type: application/json' \
    --data '{
        "username": "player1",
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

## Get bet history of a player

### Request

    `GET /bets/{username}/history`

    curl --location 'http://localhost:8181/bets/Edgouille/history'

### Response

    HTTP/1.1 200 Ok
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sun, 17 Aug 2025 23:16:20 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    [
        {
            "username": "Edgouille",
            "amount": 100.0,
            "createdAt": "2025-08-17T23:14:41.7404879",
            "betWin": 0.0
        },
        {
            "username": "Edgouille",
            "amount": 125.0,
            "createdAt": "2025-08-17T23:14:51.4133776",
            "betWin": 0.0
        }
    ]

## Get latest bets of a player

### Request

    `GET /bets/{username}/latest?amount={amount}`

    curl --location 'http://localhost:8181/bets/Edgouille/latest?limit=1'

### Response

    HTTP/1.1 200 Ok
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Thu, 11 Sep 2025 21:32:38 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    [
        {
            "username": "Edgouille",
            "amount": 34.0,
            "createdAt": "2025-09-11T21:32:14.6918846",
            "betQuestionId": "6e8c6f4c-717d-458a-accf-cc0ba2863b81",
            "betQuestion": "Will @xQc stream today? (08/19/2025)",
            "betOptionId": "36772708-e49d-4165-85c2-afb8c6acfc05",
            "betOption": "No",
            "betWin": 0.0
        }
    ]