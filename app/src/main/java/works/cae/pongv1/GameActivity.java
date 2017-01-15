package works.cae.pongv1;

import android.os.Bundle;
import android.util.Log;

import works.cae.simplegameenginev1.SGActivity;
import works.cae.simplegameenginev1.SGPreferences;

/**
 * Created by salfl on 17/12/16.
 */

public class GameActivity extends SGActivity {
    public GameView mView;
    public static final String TAG = "PongV1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableFullScreen();
        enableKeepScreenOn();

        mView = new GameView(this);
        setContentView(mView);

        SGPreferences preferences = new SGPreferences(this);
        if(preferences.getInt("first_time", -1) == -1) {
            preferences.begin()
                    .putInt("first_time", 1)
                    .putInt("difficulty", 0)
                    .putInt("high_score", 15)
                    .end();

            Log.d(TAG, "Primeira inicialização");
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Nível de dificuldade: ");
        stringBuilder.append(preferences.getInt("difficulty", 0));
        Log.d(TAG, stringBuilder.toString());

        stringBuilder.setLength(0);
        stringBuilder.append("High score: ");
        stringBuilder.append(preferences.getInt("high_score", 0));
        Log.d(TAG, stringBuilder.toString());
    }

}
