package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static final int WIDTH = 800;
    public float angle = 0;
    public int w = 80;
    public int cols = (WIDTH/w)-1;
    public int rows = cols;
    Curve[][] curves = new Curve[rows][cols];
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene  = new Scene(root,WIDTH,WIDTH);
        Canvas canvas = new Canvas(WIDTH,WIDTH);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //"Lissajous Table by Michael Coppola\n based off Daniel Shiffman's visual"




        for(int i=0;i<rows;i++){
            for(int j=0;j<rows;j++){
                curves[i][j] = new Curve();
            }
        }

        new AnimationTimer(){
            @Override
            public void handle(long now) {
                draw(gc,now);
                if(angle > Math.PI*2.2){
                    for(int i=0;i<rows;i++){
                        for(int j=0;j<rows;j++){
                            curves[i][j] = new Curve();
                        }
                    }
                    angle = 0;
                }
            }
        }.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Lissajous Grapher: Michael Coppola");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void draw(GraphicsContext gc, long dt) {
        clear(gc);


        float d = w-10;
        float r=d/2;
        ArrayList<Float> xa = new ArrayList<Float>();
        ArrayList<Float> ya = new ArrayList<Float>();

        gc.setStroke(Color.WHITE);

        for(int i=0;i<cols;i++){
            float cx = w + i*w + w/2;
            float cy = w/2;


            gc.setLineWidth(1);
            gc.strokeOval(cx-r,cy-r,d,d);
            float x = r * (float)Math.cos(angle*(i+1) - Math.PI/2);
            float y = r * (float)Math.sin(angle*(i+1) - Math.PI/2);
            gc.setFill(Color.WHITE);
            gc.fillOval(cx + x -4, cy + y - 4,8,8);

            gc.setGlobalAlpha(.5);
            gc.fillRect(cx+x,0,1,WIDTH);
            gc.setGlobalAlpha(1);

            xa.add(cx+x);


        }

        for(int i=0;i<rows;i++){
            float cy = w + i*w + w/2;
            float cx = w/2;


            gc.setLineWidth(1);
            gc.strokeOval(cx-r,cy-r,d,d);
            float x = r * (float)Math.cos(angle*(i+1) - Math.PI/2);
            float y = r * (float)Math.sin(angle*(i+1) - Math.PI/2);
            gc.setFill(Color.WHITE);
            gc.fillOval(cx + x -4, cy + y - 4,8,8);

            gc.setGlobalAlpha(.5);
            gc.fillRect(0,cy + y,WIDTH,1);
            gc.setGlobalAlpha(1);

            ya.add(cy+y);

        }
        for(int i=0;i<rows;i++){
            for(int j=0;j<rows;j++){
                curves[i][j].setY(ya.get(i));
                curves[i][j].setX(xa.get(j));
                curves[i][j].addPoint();
                curves[i][j].show(gc);
            }
        }
        angle += 0.01;
    }



    private void clear(GraphicsContext gc){
        Paint tempFill = gc.getFill();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,WIDTH,WIDTH);
        gc.setFill(tempFill);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
