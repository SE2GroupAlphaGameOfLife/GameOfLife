package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game board with a list of gameFields.
 */
public class Board {

    /**
     *
     */
    private static Board INSTANCE;

    /**
     *
     */
    private final List<GameField> gameFields; // The list of gameFields on the board

    /**
     *
     */
    public Board() {
        String jsonString = loadJsonFile();

        // Parsing the json so we can use it
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(jsonString);

        List<GameField> gameFields = new ArrayList<>();

        // Read the values and create a list of gameFields
        for (JsonValue jsonField : jsonValue) {
            List<Integer> nextPositions = new ArrayList<>();
            for (int i : jsonField.get("nextPositions").asIntArray()) {
                nextPositions.add(i);
            }

            GameField gameField = new GameField(
                    new Vector2(jsonField.get("position").getInt("x"), jsonField.get("position").getInt("y")),
                    nextPositions
            );
            gameFields.add(gameField);
        }
        this.gameFields = gameFields;
    }

    /**
     * @return
     */
    public static Board getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Board();
        }
        return INSTANCE;
    }

    /**
     * @return
     */
    protected String loadJsonFile() {
        return "[\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 55,\n" +
                "      \"y\": -8\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      1,43\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 43,\n" +
                "      \"y\": -30\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      2\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": -40\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      3\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -15,\n" +
                "      \"y\": -30\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      4,75\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -45,\n" +
                "      \"y\": -20\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      5\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -55,\n" +
                "      \"y\": 0\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      6\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -45,\n" +
                "      \"y\": 25\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      7,107\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -28,\n" +
                "      \"y\": 42\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      8\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -8,\n" +
                "      \"y\": 45\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      9\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": 45\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      10,12\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 50,\n" +
                "      \"y\": 32\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      11\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 60,\n" +
                "      \"y\": 12\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      0\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": 65\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      13\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": 85\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      14\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 35,\n" +
                "      \"y\": 100\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      15,\n" +
                "      21\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 62,\n" +
                "      \"y\": 103\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      16\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 85,\n" +
                "      \"y\": 90\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      17\n" +
                "    ],\n" +
                "    \"type\" : \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 98,\n" +
                "      \"y\": 70\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      18\n" +
                "    ],\n" +
                "    \"type\" : \"get15tLP\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 110,\n" +
                "      \"y\": 50\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      19\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 115,\n" +
                "      \"y\": 32\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      20\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 95,\n" +
                "      \"y\": 15\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      11\n" +
                "    ],\n" +
                "    \"type\" : \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 35,\n" +
                "      \"y\": 125\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      22\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 40,\n" +
                "      \"y\": 150\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      23\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 55,\n" +
                "      \"y\": 165\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      24\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 85,\n" +
                "      \"y\": 175\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      25\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 115,\n" +
                "      \"y\": 170\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      26\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 140,\n" +
                "      \"y\": 145\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      27\n" +
                "    ],\n" +
                "    \"type\" : \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 155,\n" +
                "      \"y\": 120\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      28, 34\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 105\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      29\n" +
                "    ],\n" +
                "    \"type\" : \"casino\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 197,\n" +
                "      \"y\": 82\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      30\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 197,\n" +
                "      \"y\": 65\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      31\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 185,\n" +
                "      \"y\": 50\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      32\n" +
                "    ],\n" +
                "    \"type\" : \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 165,\n" +
                "      \"y\": 42\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      33\n" +
                "    ],\n" +
                "    \"type\" : \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 143,\n" +
                "      \"y\": 35\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      19\n" +
                "    ],\n" +
                "    \"type\" : \"get3tLP\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 165,\n" +
                "      \"y\": 137\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      35\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 153\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      36\n" +
                "    ],\n" +
                "    \"type\" : \"casino\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 213,\n" +
                "      \"y\": 147\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      37\n" +
                "    ],\n" +
                "    \"type\" : \"get3.5tLP\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 235,\n" +
                "      \"y\": 128\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      38\n" +
                "    ],\n" +
                "    \"type\" : \"casino\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 240,\n" +
                "      \"y\": 110\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      39\n" +
                "    ],\n" +
                "    \"type\" : \"pay10tEUR\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 247,\n" +
                "      \"y\": 93\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      40\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 250,\n" +
                "      \"y\": 75\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      41\n" +
                "    ],\n" +
                "    \"type\" : \"get200tEUR\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 235,\n" +
                "      \"y\": 60\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      42\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 215,\n" +
                "      \"y\": 58\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      30\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 77,\n" +
                "      \"y\": -15\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      44\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 100,\n" +
                "      \"y\": -30\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      45\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 130,\n" +
                "      \"y\": -35\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      46\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 147,\n" +
                "      \"y\": -45\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      47,53\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 143,\n" +
                "      \"y\": -65\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      48\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 118,\n" +
                "      \"y\": -85\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      49\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 83,\n" +
                "      \"y\": -77\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      50\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 53,\n" +
                "      \"y\": -67\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      51\n" +
                "    ],\n" +
                "    \"type\" : \"changecareer\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 30,\n" +
                "      \"y\": -65\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      52\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": -50\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      2\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 180,\n" +
                "      \"y\": -40\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      54\n" +
                "    ],\n" +
                "    \"type\" : \"pay20t\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 225,\n" +
                "      \"y\": -50\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      55\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 245,\n" +
                "      \"y\": -77\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      56\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 250,\n" +
                "      \"y\": -100\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      57\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 245,\n" +
                "      \"y\": -128\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      58\n" +
                "    ],\n" +
                "    \"type\" : \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 232,\n" +
                "      \"y\": -148\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      59\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 210,\n" +
                "      \"y\": -160\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      60\n" +
                "    ],\n" +
                "    \"type\" : \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 175,\n" +
                "      \"y\": -160\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      61\n" +
                "    ],\n" +
                "    \"type\" : \"diploma\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 145,\n" +
                "      \"y\": -140\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      62, 66\n" +
                "    ],\n" +
                "    \"type\" : \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 110,\n" +
                "      \"y\": -125\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      63\n" +
                "    ],\n" +
                "    \"type\" : \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 80,\n" +
                "      \"y\": -120\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      64\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 50,\n" +
                "      \"y\": -108\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      65\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 45,\n" +
                "      \"y\": -85\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      50\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 140,\n" +
                "      \"y\": -165\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      67\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 120,\n" +
                "      \"y\": -175\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      68\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 92,\n" +
                "      \"y\": -170\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      69\n" +
                "    ],\n" +
                "    \"type\" : \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 62,\n" +
                "      \"y\": -175\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      70\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 35,\n" +
                "      \"y\": -177\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      71\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 17,\n" +
                "      \"y\": -168\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      72\n" +
                "    ],\n" +
                "    \"type\" : \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 10,\n" +
                "      \"y\": -155\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      73\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 10,\n" +
                "      \"y\": -140\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      74\n" +
                "    ],\n" +
                "    \"type\" : \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 25,\n" +
                "      \"y\": -120\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      64\n" +
                "    ],\n" +
                "    \"type\" : \"doctor\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -18,\n" +
                "      \"y\": -52\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      76\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -33,\n" +
                "      \"y\": -62\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      77, 86\n" +
                "    ],\n" +
                "    \"type\": \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -55,\n" +
                "      \"y\": -70\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      78\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -75,\n" +
                "      \"y\": -73\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      79\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -98,\n" +
                "      \"y\": -75\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      80\n" +
                "    ],\n" +
                "    \"type\": \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -125,\n" +
                "      \"y\": -67\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      81\n" +
                "    ],\n" +
                "    \"type\": \"promotion\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -135,\n" +
                "      \"y\": -45\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      82\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -130,\n" +
                "      \"y\": -30\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      83\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -110,\n" +
                "      \"y\": -20\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      84\n" +
                "    ],\n" +
                "    \"type\": \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -92,\n" +
                "      \"y\": -13\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      85\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -75,\n" +
                "      \"y\": -5\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      5\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -20,\n" +
                "      \"y\": -82\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      87\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -12,\n" +
                "      \"y\": -115\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      88\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -27,\n" +
                "      \"y\": -140\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      89\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -50,\n" +
                "      \"y\": -155\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      90\n" +
                "    ],\n" +
                "    \"type\": \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -83,\n" +
                "      \"y\": -160\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      91\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -113,\n" +
                "      \"y\": -155\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      92\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -145,\n" +
                "      \"y\": -150\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      93\n" +
                "    ],\n" +
                "    \"type\": \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -175,\n" +
                "      \"y\": -140\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      94, 99\n" +
                "    ],\n" +
                "    \"type\": \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -180,\n" +
                "      \"y\": -120\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      95\n" +
                "    ],\n" +
                "    \"type\": \"newcompany\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -183,\n" +
                "      \"y\": -100\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      96\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -190,\n" +
                "      \"y\": -80\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      97\n" +
                "    ],\n" +
                "    \"type\": \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -188,\n" +
                "      \"y\": -57\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      98\n" +
                "    ],\n" +
                "    \"type\": \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -165,\n" +
                "      \"y\": -47\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      81\n" +
                "    ],\n" +
                "    \"type\": \"promotion2x\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -185,\n" +
                "      \"y\": -155\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      100\n" +
                "    ],\n" +
                "    \"type\": \"newcompany\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -203,\n" +
                "      \"y\": -175\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      101\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -230,\n" +
                "      \"y\": -175\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      102\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -235,\n" +
                "      \"y\": -150\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      103\n" +
                "    ],\n" +
                "    \"type\": \"promotion1x\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -240,\n" +
                "      \"y\": -130\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      104\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -245,\n" +
                "      \"y\": -110\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      105\n" +
                "    ],\n" +
                "    \"type\": \"shares100tEUR\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -235,\n" +
                "      \"y\": -92\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      106\n" +
                "    ],\n" +
                "    \"type\": \"newcompany\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -215,\n" +
                "      \"y\": -80\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      96\n" +
                "    ],\n" +
                "    \"type\": \"promotion1x\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -75,\n" +
                "      \"y\": 25\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      108\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -110,\n" +
                "      \"y\": 17\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      109\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -137,\n" +
                "      \"y\": 27\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      110, 117\n" +
                "    ],\n" +
                "    \"type\": \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -140,\n" +
                "      \"y\": 45\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      111\n" +
                "    ],\n" +
                "    \"type\": \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -127,\n" +
                "      \"y\": 62\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      112\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -108,\n" +
                "      \"y\": 70\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      113\n" +
                "    ],\n" +
                "    \"type\": \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -87,\n" +
                "      \"y\": 68\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      114\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -63,\n" +
                "      \"y\": 62\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      115\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -35,\n" +
                "      \"y\": 75\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      116\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -10,\n" +
                "      \"y\": 63\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      8\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -170,\n" +
                "      \"y\": 28\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      118\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -205,\n" +
                "      \"y\": 43\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      119\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -225,\n" +
                "      \"y\": 72\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      120\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -215,\n" +
                "      \"y\": 103\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      121\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -225,\n" +
                "      \"y\": 130\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      122\n" +
                "    ],\n" +
                "    \"type\": \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -230,\n" +
                "      \"y\": 160\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      123\n" +
                "    ],\n" +
                "    \"type\": \"lottery\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -200,\n" +
                "      \"y\": 173\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      124\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -160,\n" +
                "      \"y\": 168\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      125\n" +
                "    ],\n" +
                "    \"type\": \"stopwedding\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -127,\n" +
                "      \"y\": 153\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      126, 130\n" +
                "    ],\n" +
                "    \"type\": \"intersection\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -107,\n" +
                "      \"y\": 138\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      127\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -97,\n" +
                "      \"y\": 122\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      128\n" +
                "    ],\n" +
                "    \"type\": \"twins\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -90,\n" +
                "      \"y\": 103\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      129\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -85,\n" +
                "      \"y\": 85\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      113\n" +
                "    ],\n" +
                "    \"type\": \"car\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -123,\n" +
                "      \"y\": 172\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      131\n" +
                "    ],\n" +
                "    \"type\": \"son\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -105,\n" +
                "      \"y\": 187\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      132\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -77,\n" +
                "      \"y\": 180\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      133\n" +
                "    ],\n" +
                "    \"type\": \"daughter\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -70,\n" +
                "      \"y\": 160\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      134\n" +
                "    ],\n" +
                "    \"type\": \"twins\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -73,\n" +
                "      \"y\": 140\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      135\n" +
                "    ],\n" +
                "    \"type\": \"house\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -50,\n" +
                "      \"y\": 143\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      136\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -27,\n" +
                "      \"y\": 143\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      137\n" +
                "    ],\n" +
                "    \"type\": \"son\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -15,\n" +
                "      \"y\": 125\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      138\n" +
                "    ],\n" +
                "    \"type\": \"empty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -28,\n" +
                "      \"y\": 108\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      139\n" +
                "    ],\n" +
                "    \"type\": \"daughter\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": -53,\n" +
                "      \"y\": 102\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      128\n" +
                "    ],\n" +
                "    \"type\": \"stopoffspring\"\n" +
                "  }\n" +
                "]";
    }

    /**
     * Returns the list of gameFields on the board.
     *
     * @return The list of gameFields on the board.
     */
    public List<GameField> getGameFields() {
        return gameFields;
    }
}
