package com.mygdx.gameoflife.mock;

import com.mygdx.gameoflife.core.Board;

public class TestBoard extends Board {

    public static TestBoard getMockInstance(){
        return new TestBoard();
    }
    @Override
    protected String loadJsonFile() {
        // Replace this with your mocked JSON string
        return "[\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": 35\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      1\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": 55\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      2\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 15,\n" +
                "      \"y\": 75\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      3\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 35,\n" +
                "      \"y\": 90\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      4,\n" +
                "      12\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 62,\n" +
                "      \"y\": 93\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      5\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 85,\n" +
                "      \"y\": 80\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      6\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 98,\n" +
                "      \"y\": 60\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      7\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 110,\n" +
                "      \"y\": 40\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      8\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 115,\n" +
                "      \"y\": 22\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      9\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 95,\n" +
                "      \"y\": 5\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      10\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 60,\n" +
                "      \"y\": 2\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      11\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 50,\n" +
                "      \"y\": 22\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      0\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 35,\n" +
                "      \"y\": 115\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      13\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 40,\n" +
                "      \"y\": 140\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      14\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 55,\n" +
                "      \"y\": 155\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      15\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 85,\n" +
                "      \"y\": 165\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      16\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 115,\n" +
                "      \"y\": 160\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      17\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 140,\n" +
                "      \"y\": 135\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      18\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 155,\n" +
                "      \"y\": 110\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      19, 25\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 95\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      20\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 197,\n" +
                "      \"y\": 72\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      21\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 197,\n" +
                "      \"y\": 55\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      22\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 185,\n" +
                "      \"y\": 40\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      23\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 165,\n" +
                "      \"y\": 32\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      24\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 143,\n" +
                "      \"y\": 25\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      8\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 165,\n" +
                "      \"y\": 127\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      26\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 143\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      27\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 213,\n" +
                "      \"y\": 137\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      28\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 235,\n" +
                "      \"y\": 118\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      29\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 240,\n" +
                "      \"y\": 100\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      30\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 247,\n" +
                "      \"y\": 83\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      31\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 250,\n" +
                "      \"y\": 65\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      32\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 235,\n" +
                "      \"y\": 50\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      33\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"position\": {\n" +
                "      \"x\": 215,\n" +
                "      \"y\": 48\n" +
                "    },\n" +
                "    \"nextPositions\": [\n" +
                "      21\n" +
                "    ]\n" +
                "  }\n" +
                "]";
    }
}