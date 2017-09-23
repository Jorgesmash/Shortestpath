package com.shortestpath.mazeparser;

import android.content.Context;
import android.view.View;

import com.shortestpath.R;
import com.shortestpath.app.mazeparser.MazeParser;
import com.shortestpath.app.mazeparser.datamodels.Node;
import com.shortestpath.app.mazeparser.datamodels.Path;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MazeParserUnitTest {

    @Mock
    Context mMockContext;

    /**
     *  Test cases to calculate a given maze and print the result
     */

    @Test
    public void testParse1() throws Exception {

        int[][] maze = new int[][] {
                {3, 4, 1, 2, 8, 6},
                {6, 1, 8, 2, 7, 4},
                {5, 9, 3, 9, 9, 5},
                {8, 4, 1, 3, 2, 6},
                {3, 7, 2, 8, 6, 4}
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse2() throws Exception {

        int[][] maze = new int[][] {
                {3, 4, 1, 2, 8, 6},
                {6, 1, 8, 2, 7, 4},
                {5, 9, 3, 9, 9, 5},
                {8, 4, 1, 3, 2, 6},
                {3, 7, 2, 1, 2, 3}
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse3() throws Exception {

        int[][] maze = new int[][] {
                {19, 10, 19, 10, 19},
                {21, 23, 20, 19, 12},
                {20, 12, 20, 11, 10},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse4() throws Exception {

        int[][] maze = new int[][] {
                {5, 8, 5, 3, 5},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse5() throws Exception {

        int[][] maze = new int[][] {
                {5},
                {8},
                {5},
                {3},
                {5}
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse6() throws Exception {

        int[][] maze = new int[][] {
                {5, 5, 'H'},
                {8, 'M', 7},
                {5, 7, 5},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse7() throws Exception {

        int[][] maze = new int[][] {};

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse8() throws Exception {

        int[][] maze = new int[][] {
                {69, 10, 19, 10, 19},
                {51, 23, 20, 19, 12},
                {60, 12, 20, 11, 10},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse9() throws Exception {

        int[][] maze = new int[][] {
                {60, 3, 3, 6},
                { 6, 3, 7, 9},
                { 5, 6, 8, 3},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse10() throws Exception {

        int[][] maze = new int[][] {
                {6, 3, -5, 9},
                {-5, 2, 4, 10},
                {3, -2, 6, 10},
                {6, -1, -2, 10},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse11() throws Exception {

        int[][] maze = new int[][] {
                {51, 51},
                {0, 51},
                {51, 51},
                {5, 5},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse12() throws Exception {

        int[][] maze = new int[][] {
                {51, 51, 51},
                {0, 51, 51},
                {51, 51, 51},
                {5, 5, 51},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    @Test
    public void testParse13() throws Exception {

        int[][] maze = new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };

        MazeParser mazeParser = new MazeParser();
        mazeParser.setOnShortestPathFoundListener(new MazeParserOnShortestPathFoundListener());
        mazeParser.calculate(maze);
    }

    /**
     * Listens if the MazeParser class has finished to calculate the shortest path
     */
    private class MazeParserOnShortestPathFoundListener implements MazeParser.OnShortestPathFoundListener {

        @Override
        public void onShortestPathFound(boolean success, Path path) {

            if (success) {
                // Set success result as "Yes"
                System.out.println("Yes");

                // Set distance result
                String distanceString = new Integer(path.getDistance()).toString();
                System.out.println(distanceString);

                // Set sequence result
                setSequenceResult(path);

            } else {
                // Set success result as "No"
                System.out.println("No");

                if (path == null) {
                    // Set a message saying that the given matrix is invalid
                    System.out.println("Invalid matrix");
                } else if (path.getNodeList().size() == 0) {

                    // Set distance result
                    String distanceString = new Integer(path.getDistance()).toString();
                    System.out.println(distanceString);

                    // Set sequence result as an empty array
                    System.out.println("[]");

                } else if (path.getNodeList().size() > 0) {

                    // Set distance result
                    String distanceString = new Integer(path.getDistance()).toString();
                    System.out.println(distanceString);

                    // Set sequence result
                    setSequenceResult(path);
                }
            }
        }

        private void setSequenceResult(Path path) {
            // Set sequence result
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (int i = 0; i < path.getNodeList().size(); i++) {

                Node node = (Node) path.getNodeList().get(i);

                stringBuilder.append(node.getRow() + 1);

                if (i < path.getNodeList().size() - 1) {
                    stringBuilder.append(", ");
                } else {
                    stringBuilder.append("]");
                }
            }
            System.out.println(stringBuilder.toString());
        }
    }
}