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
	public void testCaseJoinGame(TestInfo testInfo){
		assertNotNull(peer1.generateNewSudoku("Sudoku2"));
		assertTrue(peer1.join("Sudoku2", "Raffaele"));
		assertTrue(peer2.join("Sudoku2", "Gerry"));
		assertTrue(peer3.join("Sudoku2", "Tom"));
	}
	
	@Test
	public void testCaseJoinConStessoNome(TestInfo testInfo){
		assertNotNull(peer1.generateNewSudoku("Sudoku3"));
		assertTrue(peer1.join("Sudoku3", "Raffaele"));
		assertFalse(peer2.join("Sudoku3", "Raffaele"));
	}

	@Test
	public void testCasePlaceNumber(TestInfo testInfo){
		assertNotNull(peer1.generateNewSudoku("Sudoku4"));
		assertTrue(peer1.join("Sudoku4", "Raffaele"));
		assertNotNull(peer1.placeNumber("Sudoku4", 3, 4, 7));
		
	}
	
	@Test
	public void testCasePlaceNumberInSamePosition(TestInfo testInfo){
		assertNotNull(peer1.generateNewSudoku("Sudoku5"));
		assertTrue(peer1.join("Sudoku5", "Raffaele"));
		assertTrue(peer2.join("Sudoku5", "Gerry"));
		assertNotNull(peer1.placeNumber("Sudoku5", 3, 4, 7));
		assertNotNull(peer2.placeNumber("Sudoku5", 3, 4, 7));
	}
	
	
	@Test
	public void testGetSudoku(TestInfo testInfo){
		assertNotNull(peer1.generateNewSudoku("Sudoku6"));
		assertTrue(peer1.join("Sudoku6", "Raffaele"));
		assertNotNull(peer1.getSudoku("Sudoku6"));
		assertNull(peer2.getSudoku("Sudoku6"));
	}
	
	@Test
	public void testGetLead(TestInfo testInfo) {
		assertNotNull(peer1.getLeadboard("Sudoku5"));
	}
	
	@Test
	public void testIsTerminated(TestInfo testInfo) {
		assertNotNull(peer1.isTeerminated("Sudoku5"));
	}
	
	@Test
	public void testLeaveGame(TestInfo testInfo) {
		assertTrue(peer1.leaveGame("Sudoku6"));
		assertFalse(peer0.leaveGame("Sudoku6"));
	}
	
	@Test
	public void testLeaveAllGame(TestInfo testInfo) {
		assertTrue(peer1.leveAllGames());
	}
	

	@Test
	public void testLeaveNetwork(TestInfo testInfo) {
		assertTrue(peer0.leaveNetwoks());
	}
	

}
