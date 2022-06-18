package it.raffaelemarino.sudoku;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestSudokuGameImpl {

	protected static SudokuGameImpl peer0, peer1, peer2, peer3;

	@BeforeAll
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
	
	@Test
	public void testCaseGenerateNewSudokuStessoNome(TestInfo testInfo){
		assertNotNull(peer0.generateNewSudoku("Sudoku1"));
		assertNull(peer1.generateNewSudoku("Sudoku1"));
	}
	
	
	@Test
	public void testCaseJoinConStessoNome(TestInfo testInfo){
		assertNotNull(peer1.generateNewSudoku("Sudoku3"));
		assertTrue(peer1.join("Sudoku3", "Raffaele"));
		assertFalse(peer2.join("Sudoku3", "Raffaele"));
	}
	

}
