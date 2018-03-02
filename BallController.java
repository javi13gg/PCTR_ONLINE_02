public class BallController implements Runnable {
	private Ball ball;
	private static final int INTERVALO=20;
	
	public BallController(Ball b){
		this.ball = b;
	}
	
	@Override
	public void run(){
		try{
			
			while(true){
				ball.move();
				Thread.sleep(INTERVALO);
			}
			
		} catch(InterruptedException e){
			return;
		}

	}
	
}

