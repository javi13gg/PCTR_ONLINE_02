public class BoardController implements Runnable {
	private Board board;
	private static final int INTERVALO=20;
	
	public BoardController(Board board){
		this.board = board;
	}
	
	@Override
	public void run(){
		try{
			
			while(true){
				board.repaint();
				Thread.sleep(INTERVALO);
			}
			
		} catch(InterruptedException e){
			return;
		}

	}
}
