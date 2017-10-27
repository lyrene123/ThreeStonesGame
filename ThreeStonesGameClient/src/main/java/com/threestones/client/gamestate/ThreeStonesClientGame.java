package com.threestones.client.gamestate;

import com.threestones.client.gamestate.ThreeStonesClientGameBoard.CellState;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lyrene Labor
 */
public class ThreeStonesClientGame {

    private ThreeStonesClientGameBoard board;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    //GENERAL BASIC LOGIC FOR OUR MOVE SELECTION
    //DEPENDING ON WHERE WE WANT TO TAKE THIS WE CAN ADD MORE MOVE COMPLEXITY SO THAT IT CHECKS THE AVAILALBE
    //SQUARES THAT WILL BECOME AVAILABLE BASE ON A CERTAIN MOVE AND THE SCORES WHITE OR BLACK CAN SCORE IN THE FUTURE (reading ahead of time)
    public ThreeStonesClientGame() {
        this.board = new ThreeStonesClientGameBoard();
    }

//    public void updateBoard(int x, int y, CellState color) {
//        board.getBoard()[x][y] = color;
//        board.setBoard(board.getBoard());
//    }
    public byte[] determineNextMove(int x, int y) {
        List<ThreeStonesMove> bestMoves = new ArrayList<ThreeStonesMove>();
        CellState[][] gameBoard = board.getBoard();
        int highestMoveValue = 0;
        byte[] moves = new byte[4];

        //LOOPS THROUGH GAMEBOARD
        for (int i = 0; i < gameBoard[0].length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                //IF CURRENT TILE IS AVAILABLE DETERMINE ITS VALUE
                if (gameBoard[i][j] == CellState.AVAILABLE) {
                    log.debug("determineNextMove CellState.Available");
                    int whitePoints = board.checkForThreeStones(x, y, CellState.WHITE);
                    int blackPoints = board.checkForThreeStones(i, j, CellState.BLACK);
                    ThreeStonesMove move = new ThreeStonesMove(whitePoints, blackPoints, i, j);
                    //ThreeStonesMove move = new ThreeStonesMove(board.checkForThreeStones(i, j, CellState.WHITE), board.checkForThreeStones(i, j, CellState.BLACK), i, j);
                    //resets list and adds the current move
                    bestMoves.add(move);
                    //ADD LOGIC HERE FOR ADVANCED AI
                    //check hypothical board state
                    //get a list of created hypothical moves
                    //POSSIBLY CHANGE THE moveValue system so that 
                    //different moves are worth more ie white +2 black +4 , moves
                    //2 turns ahead +1 +2
//                    if (move.getMoveValue() > highestMoveValue) {
//                        bestMoves.clear();
//                        highestMoveValue = move.getMoveValue();
//                        bestMoves.add(move);
//                    } //if move is equal to the top add it to the list
//                    else if (move.getMoveValue() == highestMoveValue) {
//                        bestMoves.add(move);
//                    }
                }
            }
        }
        if (bestMoves.size() > 1) {
            //RANDOMIZES MOVE
            //CAN CHANGE IT SO IT PREFERS HIS OWN SCORES OVER THE ENEMY PlAYER SCORING
            //WASNT SURE WHAT TO DO FROM HERE , HOW WE ARE GOING TO HANDLE making moves

            int position = (int) (Math.random() * bestMoves.size());
            moves[0] = (byte) 1;
            moves[1] = (byte) bestMoves.get(position).getX();
            moves[2] = (byte) bestMoves.get(position).getY();
        } else {
            //IF ONLY ONE GOOD MOVE MAKE IT USING LIST.get(0)
        }

        log.debug("determineNextMove" + bestMoves.size());
        for (ThreeStonesMove bestMove : bestMoves) {
            log.debug("determineNextMove" + bestMove);
        }
        if (moves[1] != 0 || moves[2] != 0 ) {
            int blackStones = this.board.getBlackStoneCount();
            this.board.setBlackStoneCount(--blackStones);
        }
        return moves;
    }

    public void startGame() {
        board.startNewGame();
    }

    private ThreeStonesMove createMove(int x, int y) {

        return new ThreeStonesMove();
    }

    public void setBoard(ThreeStonesClientGameBoard board) {
        this.board = board;
    }

}
