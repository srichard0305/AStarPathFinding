package io.steve.comp452;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Ant {

    private Sprite ant;
    private float x, y;
    private float maxSpeed = 2.0f;
    private ArrayList<Node> path;
    private Vector2 result;
    private float angle;


    Ant(ArrayList<Node> path){
        ant = new Sprite(new Texture("ant.png"));
        this.path = new ArrayList<>(path);
        x = convertCoordinates(path.get(0).getX());
        y = convertCoordinates(path.get(0).getY());
        //angle = align(x, y);
        ant.setPosition(x, y);
        ant.setRotation(angle);
        Gdx.app.log("", String.valueOf(x) + " " + String.valueOf(y));

    }

    public void draw(Batch batch){
        if(path.size() > 1) {
            Node targetNode = followPath();
            Vector2 seekResult = seek(convertCoordinates(targetNode.getX()), convertCoordinates(targetNode.getY()));
            x += seekResult.x;
            y += seekResult.y;
            ant.setPosition(x, y);
            ant.setRotation(align(convertCoordinates(targetNode.getX()), convertCoordinates(targetNode.getY())) - 90);
        }
        ant.draw(batch);
    }

    private Node followPath(){
        if(x/50 == path.get(0).getX() && y/50 == path.get(0).getY()){
            Node temp = path.get(1);
            path.remove(0);
            path.trimToSize();
            return temp;
        }

        return path.get(0);
    }

    private float convertCoordinates(float coordinate){
        return coordinate * 50;
    }

    private Vector2 seek(float goalX, float goalY){

        result = new Vector2();

        // get direction to target
        result.set(goalX - x, goalY - y);

        // the velocity in this direction at max speed
        result.nor();
        result.set(result.x * maxSpeed, result.y * maxSpeed);

        return result;

    }


    private float align(float goalX, float goalY){
        angle = (float) Math.atan2(goalY - y, goalX - x);
        angle -= Math.PI/2;
        angle = angle * MathUtils.radDeg;
        return angle;
    }


}
