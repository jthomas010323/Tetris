package application;

/*
 * 
 */
public interface Controller {
	
	public boolean Rotate_Clockwise(Tetromino Tetromino, Board gameBoard);
	public boolean Move_Down(Tetromino Tetromino, Board gameBoard);
	public boolean Move_Left(Tetromino Tetromino, Board gameBoard);
	public boolean Move_Right(Tetromino Tetromino, Board gameBoard);
}
