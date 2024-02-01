package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import Day8.Tuple;

/*
 * Day10
 * Advent of Code 2023
 * 
 * This class represents the Day10
 */
public class Day10 {


    /*
     * Represents a direction
     */
    public enum Direction {
        NORTH(0,-1),
        SOUTH(0,1),
        EAST(1,0),
        WEST(-1,0);
        
        public final int y;
        public final int x;

        /*
         * Constructor
         * @param y the y coords
         * @param x the x coords
         */
        Direction (int y, int x) {        
            this.y = y;
            this.x = x;
        }

        /*
         * Returns the position after moving
         * @param pos the position
         * @return the position after moving
         */
        public Tuple<Integer> move(Tuple<Integer> pos) {
            return new Tuple<Integer>(pos.left + y, pos.right + x);
        }

        /*
         * Returns the opposite direction
         * @return the opposite direction
         */
        public Direction getOpposite() {
            switch (this) {
                case NORTH:
                    return SOUTH;
                case SOUTH:
                    return NORTH;
                case EAST:
                    return WEST;
                case WEST:
                    return EAST;
                default:
                    return null;
            }
        }

    }

    /*
     * Represents the state for the inside pipes counting
     */
    public enum State {
        IN,
        OUT,
        CORNER_UP,
        CORNER_DOWN;
    
        /*
         * Returns the opposite state
         * @return the opposite state
         */
        public State getOpposite() {
            switch (this) {
                case IN:
                    return OUT;
                case OUT:
                    return IN;
                case CORNER_UP:
                    return CORNER_DOWN;
                case CORNER_DOWN:
                    return CORNER_UP;
                default:
                    return null;
            }
        }
    }

    

    /*
     * Represents a pipe
     */
    public class Pipe {
        private Tuple<Direction> directions;

        /*
         * Constructor
         * @param in the direction of the input
         * @param out the direction of the output
         */
        public Pipe(Direction in, Direction out) {
            directions = new Tuple<Direction>(in, out);
        }

        /*
         * Returns the direction of the input
         * @return the direction of the input
         */
        public Direction getLeft() {
            return directions.left;
        }

        /*
         * Returns the direction of the output
         * @return the direction of the output
         */
        public Direction getRight() {
            return directions.right;
        }

        /*
         * Returns true if the pipe has the direction
         * @param d the direction
         * @return true if the pipe has the direction, false otherwise
         */
        public boolean hasDirection(Direction d) {
            return directions.left == d || directions.right == d;
        }

        /*
         * Returns the other direction
         * @param d the direction
         * @return the other direction of the pipe
         */
        private Direction getOtherDir(Direction d) {
            if (directions.left == d) return directions.right;
            if (directions.right == d) return directions.left;
            System.out.println("Error in getOtherDir");
            return null;
        }

        /*
         * Returns the position of the output
         * @param inDir the direction of the input
         * @param pos the position of the input
         * @return the position of the output
         */
        public Tuple<Integer> moveFrom(Direction inDir, Tuple<Integer> pos) {
            // get the out direction
            Direction outDir = getOtherDir(inDir);
            return outDir.move(pos);
        }

        /*
         * Returns true if the pipe is equal to the other pipe
         * @param p the other pipe
         * @return true if the pipe is equal to the other pipe, false otherwise
         */
        public boolean equals(Pipe p) {
            return p.getLeft() == getLeft() && p.getRight() == getRight();
        }

        /*
         * Returns the string representation of the pipe
         * @return the string representation of the pipe
         */
        public String toString() {
            return "l : " + directions.left.toString() + " r : " +  directions.right.toString();
        }

        
    }

    public static Tuple<Integer> startPos = new Tuple<Integer>(0,0); 
    public static Tuple<Integer> bounds = new Tuple<Integer>(0,0);

    /*
     * Returns the pipe from the character
     * @param s the character
     * @param x the actual x coords 
     * @param y the actual y coords
     * @return the pipe
     */
    public Pipe getPipe(Character s, int x, int y) {
        switch (s) {
            case '|':
                return new Pipe(Direction.NORTH, Direction.SOUTH);
            case '-':
                return new Pipe(Direction.WEST, Direction.EAST);
            case 'L':
                return new Pipe(Direction.NORTH, Direction.EAST);
            case 'J':
                return new Pipe(Direction.NORTH, Direction.WEST);
            case '7':
                return new Pipe(Direction.SOUTH, Direction.WEST);
            case 'F':
                return new Pipe(Direction.SOUTH, Direction.EAST);
            case 'S':
                startPos.set(x, y);
                return null;
            default:
                return new Pipe(null, null);
        }
    }

    /*
     * Returns the character that represents the pipe
     * @param p the pipe
     * @return the character
     */
    public Character charFromPipe(Pipe p) {
        if (p == null) return ' ';
        if (p.hasDirection(Direction.NORTH) && p.hasDirection(Direction.SOUTH)) return '|';
        if (p.hasDirection(Direction.WEST) && p.hasDirection(Direction.EAST)) return '-';
        if (p.hasDirection(Direction.NORTH) && p.hasDirection(Direction.EAST)) return 'L';
        if (p.hasDirection(Direction.NORTH) && p.hasDirection(Direction.WEST)) return 'J';
        if (p.hasDirection(Direction.SOUTH) && p.hasDirection(Direction.WEST)) return '7';
        if (p.hasDirection(Direction.SOUTH) && p.hasDirection(Direction.EAST)) return 'F';
        return ' ';
    }

    /*
     * Returns the pipe from the tuple
     * @param pipes the pipes
     * @param pos the position
     * @return the pipe
     */
    private static Pipe getFromTuple(Pipe[][] pipes, Tuple<Integer> pos) {
        return pipes[pos.getLeft()][pos.getRight()];
    }

   

    /*
     * Flood fill algorithm
     * @param pipes the pipes of the input
     * @param x the x coords of the start
     * @param y the y coords of the start
     * @param lines the lines of the input
     * @return the matrix of the cycle
     */
    public Character[][] floodFill_2sides(Pipe[][] pipes, int x, int y, String[] lines) {
        Character[][] cyclePipe = new Character[bounds.left][bounds.right];
        Tuple<Tuple<Integer>> positions = new Tuple<Tuple<Integer>>();
        Tuple<Direction> ins = new Tuple<Direction>();
        
        // Find both ways
        int waysFind = 0; 
        if (y+1 < bounds.right) {
            if(pipes[x][y+1].hasDirection(Direction.NORTH)) {
                if(waysFind == 0) {
                    positions.setLeft(new Tuple<Integer>(x, y+1));
                    ins.setLeft(Direction.NORTH);
                } else {
                    positions.setRight(new Tuple<Integer>(x, y+1));
                    ins.setRight(Direction.NORTH);
                    
                }
                waysFind++;
            }
        } 
        if (y-1 >= 0) {
            if(pipes[x][y-1].hasDirection(Direction.SOUTH)) {
                if(waysFind == 0) {
                    positions.setLeft(new Tuple<Integer>(x, y-1));
                    ins.setLeft(Direction.SOUTH);
                } else {
                    positions.setRight(new Tuple<Integer>(x, y-1));
                    ins.setRight(Direction.SOUTH);
                }
                waysFind++;
            }
        }
        if (0 <= x-1 && waysFind < 2) {
            if(pipes[x-1][y].hasDirection(Direction.EAST)) {
                if(waysFind == 0) {
                    positions.setLeft(new Tuple<Integer>(x-1, y));
                    ins.setLeft(Direction.EAST);
                } else {
                    positions.setRight(new Tuple<Integer>(x-1, y));
                    ins.setRight(Direction.EAST);
                }
                waysFind++;
            }
        }
        if (x+1 < bounds.left && waysFind < 2) {
            if(pipes[x+1][y].hasDirection(Direction.WEST)) {
                if(waysFind == 0) {
                    positions.setLeft(new Tuple<Integer>(x+1, y));
                    ins.setLeft(Direction.WEST);
                    waysFind++;
                } else {
                    positions.setRight(new Tuple<Integer>(x+1, y));
                    ins.setRight(Direction.WEST);
                }
                waysFind++;
            }
        }

        // If we found both ways
        if (waysFind != 2) {
            System.out.println("error finding ways around S" + waysFind);
            return null;
        }
        
        // Change S to the right char
        cyclePipe[x][y] = charFromPipe(new Pipe(ins.left.getOpposite(), ins.right.getOpposite()));
   
        // go around the pipes until they meet
        int actualDistance = 0;
        while (true) {

            actualDistance++;
            // update the distances values
            cyclePipe[positions.getLeft().getLeft()][positions.getLeft().getRight()] = lines[positions.getLeft().getRight()].charAt(positions.getLeft().getLeft());
            cyclePipe[positions.getRight().getLeft()][positions.getRight().getRight()] = lines[positions.getRight().getRight()].charAt(positions.getRight().getLeft());
            
            // Next directions
            Direction nextDir1 = getFromTuple(pipes, positions.getLeft()).getOtherDir(ins.getLeft()).getOpposite();
            Direction nextDir2 = getFromTuple(pipes, positions.getRight()).getOtherDir(ins.getRight()).getOpposite();
 
            // Next positions
            positions.setLeft(getFromTuple(pipes, positions.getLeft()).moveFrom(ins.getLeft(), positions.getLeft()));
            positions.setRight(getFromTuple(pipes, positions.getRight()).moveFrom(ins.getRight(), positions.getRight()));
            
            // update next ins
            ins.set(nextDir1, nextDir2);
            
            // Debug
            //System.out.println(positions);
            //System.out.println(ins);
            //printMatrix(cyclePipe);


            // Check if they are the same
            if (positions.getLeft().getLeft() == positions.getRight().getLeft() && positions.getLeft().getRight() == positions.getRight().getRight()) {
                printMatrix(cyclePipe);
                // System.out.println("Found the max distance at " + positions.getLeft().getLeft() + ", " + positions.getLeft().getRight() + " with distance " + (actualDistance + 1));
                System.out.println("Part 1 : " + actualDistance);
                cyclePipe[positions.getLeft().getLeft()][positions.getLeft().getRight()] = lines[positions.getLeft().getRight()].charAt(positions.getLeft().getLeft());
                cyclePipe[positions.getRight().getLeft()][positions.getRight().getRight()] = lines[positions.getRight().getRight()].charAt(positions.getRight().getLeft());
                return cyclePipe;
            }


            // Check if we already visited the position
            if (checkVisited(cyclePipe, positions.getLeft())) {
                printMatrix(cyclePipe);
                System.out.println("Part 1 : " + actualDistance);
                return cyclePipe;
            }

            if (checkVisited(cyclePipe, positions.getRight())) {
                printMatrix(cyclePipe);
                System.out.println("Part 1 : " + actualDistance);
                return cyclePipe;
            }
            
        }

    }

    /*
     * Checks if the position is already visited
     * @param cyclePipe the matrix of the cycle
     * @param pos the position
     * @return true if visited, false otherwise
     */
    private boolean checkVisited(Character[][] cyclePipe, Tuple<Integer> pos) {
        if (cyclePipe[pos.getLeft()][pos.getRight()] != null) {
            // System.out.println("Found max distance at " + pos.getLeft() + ", " + pos.getRight());
            return true;
        }
        return false;
    }

    /*
     * Prints the matrix
     * @param matrix the matrix
     */
    private void printMatrix(Character[][] matrix) {
        for (int y = 0; y<bounds.right; y++) {
            for (int x = 0; x<bounds.left; x++) {
                if (matrix[x][y] != null) {
                    System.out.print(matrix[x][y] + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    /*
     * Counts the number of inside pipes
     * @param matrix the matrix
     * @return the number of inside pipes
     */
    public int countInside(Character[][] matrix) {
        int count = 0;
        // to count the inside pipes, we memorize the walls we went through
        for (int y = 0; y<bounds.right-1; y++) {
            State state = State.OUT;
            State oldState = State.OUT;
            for (int x = 0; x<bounds.left-1; x++) {
                switch (matrix[x][y]) {
                    case '|':
                        state = state.getOpposite();
                        break;
                    case '-':
                        break;
                    case 'L':
                        oldState = state;
                        state = State.CORNER_UP;
                        break;
                    case 'J':
                        if (state == State.CORNER_DOWN) {
                            state = oldState.getOpposite();
                        } else {
                            state = oldState;
                        }
                        break;
                    case '7':
                        if (state == State.CORNER_UP) {
                            state = oldState.getOpposite();
                        } else {
                            state = oldState;
                        }
                        break;
                    case 'F':
                        oldState = state;
                        state = State.CORNER_DOWN;
                        break;
                    case null:
                        if (state == State.IN) {
                            count++;
                            matrix[x][y] = 'I';
                        }
                        break;
                    default:
                        System.out.println("Error in countInside");
                        break;
                }
            }
        }
        // Uncomment to see the matrix with the inside pipes noted with I
        // printMatrix(matrix);
        return count;
    }

    /*
     * Main function of Day10
     */
    public static void main(String[] args) throws IOException {
        
        // Create instance
        Day10 day10 = new Day10();
        //day10.test();

        // Read input
        String path = "Day10/input";
        String input = Files.readString(Paths.get(path));
        String[] lines = input.split("\n");

        // Bounds (maxX, MaxY)
        bounds.set(lines[0].length(), lines.length); 

        // Replace the pipes
        Pipe[][] pipes = new Pipe[bounds.left][bounds.right];
        for (int y = 0; y<bounds.right; y++) {
            for (int x = 0; x<bounds.left; x++) {
                pipes[x][y] = day10.getPipe(lines[y].charAt(x), x, y);
            }
        }

        // flood fill
        Character[][] cycle = day10.floodFill_2sides(pipes, startPos.left, startPos.right, lines);
        System.out.println("Part 2 : " + day10.countInside(cycle));


    }


    
    
    

}
