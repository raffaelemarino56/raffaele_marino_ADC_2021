package it.raffaelemarino.sudoku;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestSudokuGameImpl {

	protected static SudokuGameImpl peer0, peer1, peer2, peer3;

	@Test
	public static void setup() throws Exception{
		peer0 = new SudokuGameImpl(0, "127.0.0.1", new MessageListenerImpl(0));	
		peer1 = new SudokuGameImpl(1, "127.0.0.1", new MessageListenerImpl(1));
		peer2 = new SudokuGameImpl(2, "127.0.0.1", new MessageListenerImpl(2));
		peer3 = new SudokuGameImpl(3, "127.0.0.1", new MessageListenerImpl(3));

	}
	
	@Test
	public void testCaseGenerateNewSudoku(TestInfo testInfo){
		assertNotNull(peer0.generateNewSudoku("Sudoku0"));
	}
	
	

}
