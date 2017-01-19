package works.cae.pongv1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import works.cae.simplegameenginev1.SGView;

/**
 * Created by salfl on 15/01/17.
 */

public class GameView extends SGView {

    private final static int BALL_SIZE = 16; // Tamanho da bola
    private final static int DISTANCE_FROM_EDGE = 16;
    private final static int PADDLE_HEIGHT = 98;
    private final static int PADDLE_WIDTH = 23;

    private final static int BALL_SPEED = 120; // Velocidade da bola 120px por segundo
    private final static int OPPONENT_SPEED = 120; // Velocidade do paddle oponente 120px por segundo

    private RectF mBallDestination = new RectF();
    private RectF mOpponentDestination = new RectF();
    private RectF mPlayerDestination = new RectF();

    private Paint mTempPaint = new Paint();

    private boolean mBallMoveRight = true;
    private boolean mOpponentMoveDown = true;

    @Override
    public void setup() {
        Point viewDimensions = getDimensions();
        Point viewCenter = new Point(viewDimensions.x / 2, viewDimensions.y / 2);

        int halfBall = BALL_SIZE / 2;
        int halfPaddleHeight = PADDLE_HEIGHT / 2;

        mBallDestination.set(viewCenter.x - halfBall, // Esquerda
                viewCenter.y - halfBall, // Topo
                viewCenter.x + halfBall, // Direita
                viewCenter.y + halfBall); // Base

        mPlayerDestination.set(DISTANCE_FROM_EDGE, // Esquerda
                viewCenter.y - halfPaddleHeight, // Topo
                DISTANCE_FROM_EDGE + PADDLE_WIDTH, // Direita
                viewCenter.y + halfPaddleHeight); // Base

        mOpponentDestination.set(viewDimensions.x - (DISTANCE_FROM_EDGE + PADDLE_WIDTH), // Esquerda
                viewCenter.y - halfPaddleHeight, // Topo
                viewDimensions.x - DISTANCE_FROM_EDGE, // Direita
                viewCenter.y + halfPaddleHeight); // Base
    }

    @Override
    public void step(Canvas canvas, float eleapsedTimeInSeconds) { // Sobrepondo elementos do jogo à tela
        moveBall(eleapsedTimeInSeconds);
        moveOpponent(eleapsedTimeInSeconds);

        mTempPaint.setColor(Color.RED);

        canvas.drawRect(mPlayerDestination, mTempPaint);
        canvas.drawRect(mBallDestination, mTempPaint);
        canvas.drawRect(mOpponentDestination, mTempPaint);
    }

    private void moveOpponent(float eleapsedTimeInSeconds) {
        Point viewDimensions = getDimensions();

        if(mOpponentMoveDown) { // Testa se a direção do oponente é para BAIXO
            mOpponentDestination.top += OPPONENT_SPEED * eleapsedTimeInSeconds; // Desenha o topo do paddle oponente 2px para BAIXO
            mOpponentDestination.bottom += OPPONENT_SPEED * eleapsedTimeInSeconds; // Desenha a base do paddle oponente 2px para BAIXO

            if(mOpponentDestination.bottom >= viewDimensions.y) { // Testa se a base ultrapassou o canto INFERIOR da tela (efeito de encostar/rebater)
                mOpponentDestination.top = viewDimensions.y - PADDLE_HEIGHT; // Recua o topo em 2px para BAIXO usando a medida do paddle
                mOpponentDestination.bottom = viewDimensions.y; // Recoloca a base 2px para BAIXO (já que ela teria passado a extremidade em 2px que é a velocidade)

                mOpponentMoveDown = false; // Muda para false porque o movimento da bola agora será para CIMA
            }
        } else {
            mOpponentDestination.top -= OPPONENT_SPEED * eleapsedTimeInSeconds; // Desenha o topo do paddle oponente 2px para CIMA
            mOpponentDestination.bottom -= OPPONENT_SPEED * eleapsedTimeInSeconds;// Desenha a base do paddle oponente 2px para CIMA

            if(mOpponentDestination.top < 0) {
                mOpponentDestination.top = 0;
                mOpponentDestination.bottom = PADDLE_HEIGHT;

                mOpponentMoveDown = true; // Muda para false porque o movimento da bola agora será para BAIXO
            }
        }
    }

    private void moveBall(float eleapsedTimeInSeconds) {
        Point viewDimensions = getDimensions();

        if(mBallMoveRight) { // Testa se a direção é DIREITA
            mBallDestination.left += BALL_SPEED * eleapsedTimeInSeconds; // Desenha a aresta ESQUERDA da bola 2 px para DIREITA
            mBallDestination.right += BALL_SPEED * eleapsedTimeInSeconds; // Desenha a aresta DIREITA da bola 2 px para DIREITA

            if(mBallDestination.right >= viewDimensions.x) { // Testa se a aresta DIREITA ultrapassou o canto DIREITO da tela (efeito de encostar/rebater)
                mBallDestination.left = viewDimensions.x - BALL_SIZE; // Recua a aresta ESQUERDA em 2px para a ESDQUERDA
                mBallDestination.right = viewDimensions.x; // Recoloca a aresta DIREITA 2px para a ESQUERDA (já que ela teria passado a extremidade em 2px que é a velocidade)

                mBallMoveRight = false; // Muda para false porque o movimento da bola agora é para a ESQUERDA
            }
        } else { // Lógica inversa para movimentar a bola para a ESQUERDA
            mBallDestination.left -= BALL_SPEED * eleapsedTimeInSeconds;
            mBallDestination.right -= BALL_SPEED * eleapsedTimeInSeconds;

            if(mBallDestination.left <= 0) {
                mBallDestination.left = 0;
                mBallDestination.right =  BALL_SIZE; // Aqui recebe o tamanho da bola pra manter o tamanho do desenho

                mBallMoveRight = true;
            }
        } // Fim do moveBall()
    }

    public GameView(Context context) {
        super(context);
    }

}
