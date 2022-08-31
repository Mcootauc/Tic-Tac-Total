package main.t3;

import java.util.*;
import java.util.Map.Entry;

/**
 * Artificial Intelligence responsible for playing the game of T3!
 * Implements the alpha-beta-pruning mini-max search algorithm
 */
public class T3Player {
	
	
    /**
     * Workhorse of an AI T3Player's choice mechanics that, given a game state,
     * makes the optimal choice from that state as defined by the mechanics of
     * the game of Tic-Tac-Total.
     * Note: In the event that multiple moves have equivalently maximal minimax
     * scores, ties are broken by move col, then row, then move number in ascending
     * order (see spec and unit tests for more info). The agent will also always
     * take an immediately winning move over a delayed one (e.g., 2 moves in the future).
     * @param state The state from which the T3Player is making a move decision.
     * @return The T3Player's optimal action.
     */
    public T3Action choose (T3State state) {
    	int UScore = 0;
    	T3Action bestAction = new T3Action(0, 0, 2);
    	Iterator<Entry<T3Action, T3State>> mapIt = state.getTransitions().entrySet().iterator();
    	while (mapIt.hasNext()) {
    		Map.Entry mapElement = (Map.Entry)mapIt.next();
    		T3State nextState = (T3State)mapElement.getValue();
    		//checks if the transition is a killer move
    		if (nextState.isWin()) {
    			bestAction = (T3Action)mapElement.getKey();
    			break;
    		}
    		//takes the best possible action
    		if (minimax(nextState, Integer.MIN_VALUE, Integer.MAX_VALUE, true) > UScore) {
    			UScore = minimax(nextState, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    			bestAction = (T3Action)mapElement.getKey();
    		}
    	}
    	return bestAction;
    }
    
    
    /**
     * Gives the utility score of a given state to show if the state is eventually a win,
     * a tie, or a loss. 
     * @param state The state from which the T3Player is making a move decision.
     * @param alpha The smallest score from a previously explored path
     * @param beta The biggest score from a previously explored path
     * @return The utility score of the given state
     */
    public int minimax(T3State state, int alpha, int beta, boolean oddTurn) {
    	int UScore;
    	Iterator<Entry<T3Action, T3State>> mapIt = state.getTransitions().entrySet().iterator();
    	//checks if there are no more moves
    	if (state.getTransitions() == null){
    		if (state.isWin()) { 
    			UScore = 2;
    		} else if (state.isTie()) {
    			UScore = 1;
    		} else {
    			UScore = 0;
    		}
    		return UScore;
    	}
    	if (oddTurn) {
    		UScore = Integer.MIN_VALUE;
    		while (mapIt.hasNext()) {
    			Map.Entry mapElement = (Map.Entry)mapIt.next();
    			UScore = Math.max(UScore, minimax((T3State)mapElement.getValue(), alpha, beta, false)); 
    			alpha = Math.max(alpha, UScore);
    			if (beta <= alpha) {
    				break;
    			}
    		}
    		return UScore;
    	} else {
    		UScore = Integer.MAX_VALUE;
    		while (mapIt.hasNext()) {
    			Map.Entry mapElement = (Map.Entry)mapIt.next();
    			UScore = Math.min(UScore, minimax((T3State)mapElement.getValue(), alpha, beta, true)); 
    			beta = Math.min(beta, UScore);
    			if (beta <= alpha) {
    				break;
    			}
    		}
    		return UScore;
    	}
    }
}

