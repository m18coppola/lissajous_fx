package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Curve {
    private ArrayList<Point2D> path;
    private Point2D current;
    private float x;
    private float y;
    private boolean flip;

    public Curve(){
        path = new ArrayList<Point2D>();
        current = new Point2D(0,0);
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public void addPoint(){
        if(path.size()>400){
            path.remove(0);
        }
        if(flip) {
            path.add(new Point2D(x, y));
            x = 0;
            y = 0;
            flip = !flip;
        }
        else{
            flip = !flip;
        }
    }
    public void show(GraphicsContext gc){
       gc.setFill(Color.WHITE);

       double x[] = new double[path.size()];
       double y[] = new double[path.size()];
       for(int i=0;i<path.size();i++){
           x[i] = path.get(i).getX();
           y[i] = path.get(i).getY();
       }
       gc.strokePolyline(x,y,path.size());

    }

}
