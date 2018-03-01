import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	// TODO update with number of group label. See practice statement.
	private final int N_BALL = 2+3;
	private Ball[] balls;
	private Thread[] hilos;
	private Thread hiloTablero;

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("PrÃ¡ctica programaciÃ³n concurrente objetos mÃ³viles independientes");
		setResizable(false);
		setVisible(true);
	}

	private void initBalls() {
		balls = new Ball[N_BALL];
		for (int i=0; i<N_BALL; i++){
			balls[i] = new Ball();
		}
		board.setBalls(balls);
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Por no complicar mucho más la estructura del código
			// de la práctica hemos incluido aquí mismo algunas comprobaciones 
			// para mantener consistente la ejecución en relación
			// a las posibles interacciones del usuario.
			if (hilos==null)
				//Sólo creamos el array de hilos la primera vez
				hilos = new Thread[N_BALL];
			
			for (int i=0; i<N_BALL; i++){
				
				if (hilos[i]==null || !hilos[i].isAlive()) {
					//Si es la primera vez que se 'empieza'
					//o el hilo ya ha sido interrumpido
					hilos[i] = new Thread(new BallController(balls[i]));
					hilos[i].start();
				}
			}
			
			if (hiloTablero==null || !hiloTablero.isAlive()) {
				//Si es la primera vez que se 'empieza'
				//o el hilo ya ha sido interrumpido
				hiloTablero = new Thread(new BoardController(board));
				hiloTablero.start();
			}

		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (hilos!=null)
				for (int i=0; i<N_BALL; i++){
					if (hilos[i]!=null)
						hilos[i].interrupt();
			}
			if (hiloTablero!=null)
				hiloTablero.interrupt();	

		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}
