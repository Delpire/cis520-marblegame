package com.project3.cis520.marblegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MarbleGame extends Activity implements SensorEventListener {

    GameSurfaceView gameSurfaceView;

    public static final float TIMESTEP = 1/60;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float move_x;
    private float move_y;

    long lastSensorTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurfaceView = new GameSurfaceView(this);
        setContentView(gameSurfaceView);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(accelerometer.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameSurfaceView.onResumeGameSurfaceView();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameSurfaceView.onPauseGameSurfaceView();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_marble_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            long curTime = System.currentTimeMillis();

            if((curTime - lastSensorTime) > 100){
                move_x = event.values[0];
                move_y = event.values[1];
                lastSensorTime = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class GameSurfaceView extends SurfaceView implements Runnable{

        SurfaceHolder surfaceHolder;
        volatile boolean running = false;
        private Thread thread = null;

        private Ball ball;

        private Maze maze;

        public GameSurfaceView(Context context){
            super(context);
            surfaceHolder = getHolder();
        }

        @Override
        public void run(){

            long curTimeInMilli;
            long lastTimeInMilli = System.currentTimeMillis();
            long elapsedTime = 0;

            while(running){

                curTimeInMilli = System.currentTimeMillis();

                elapsedTime += curTimeInMilli - lastTimeInMilli;

                if(surfaceHolder.getSurface().isValid()){

                    Canvas canvas = surfaceHolder.lockCanvas();

                    canvas.drawColor(Color.WHITE);

                    if(elapsedTime > 4 * TIMESTEP){
                        elapsedTime = 4 * (long)TIMESTEP;
                    }

                    if(ball == null){
                        int width = canvas.getWidth();
                        int height = canvas.getHeight();

                        maze = new Maze(width, height);
                        ball = new Ball(width, height, maze);
                    }

                    if(elapsedTime >= TIMESTEP) {
                        ball.update(move_x, move_y);
                        elapsedTime = 0;
                    }
                    maze.render(canvas);
                    ball.render(canvas);


                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

                lastTimeInMilli = System.currentTimeMillis();
            }
        }

        public void onResumeGameSurfaceView() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseGameSurfaceView() {
            boolean threadPaused = false;

            running = false;

            //Attempt to stop the thread.
            while(!threadPaused){
                try{
                    thread.join();
                    threadPaused = true;
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
