package io.steve.comp452;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {

    Game game;
    TiledMap map;
    TiledMapTileLayer tiledMapTileLayerTerrain, tiledMapTileLayerAnt;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Stage stage;
    Viewport viewport;

    final int TILE_WIDTH = 50;
    final int TILE_HEIGHT = 50;
    final int ROW = 16;
    final int COL = 16;

    Table menu;
    Button openTerrain, grassTerrain, swampTerrain, obstacle, start, restart;
    Table clickableActors;


    GameScreen(Game game){
        this.game = game;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new FillViewport(camera.viewportWidth, camera.viewportHeight);
        stage = new Stage(viewport);

        initMap();
        initMenu();
        initClickableTiles();

    }

    //initialize map on start up
    public void initMap(){
        map = new TiledMap();
        tiledMapTileLayerTerrain = new TiledMapTileLayer(ROW, COL,TILE_WIDTH, TILE_HEIGHT);
        tiledMapTileLayerAnt = new TiledMapTileLayer(ROW, COL,TILE_WIDTH, TILE_HEIGHT);
        map.getLayers().add(tiledMapTileLayerTerrain);
        map.getLayers().add(tiledMapTileLayerAnt);
        for(int i  = 0; i < ROW; i++){
            for(int j  = 0; j < COL; j++){
                Texture landTexture = new Texture(Gdx.files.internal("square.png"));
                TextureRegion landTextureReg = new TextureRegion(landTexture);
                StaticTiledMapTile myTile = new StaticTiledMapTile(landTextureReg);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(myTile);
                tiledMapTileLayerTerrain.setCell(i, j, cell);
            }
        }

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    //initialize menu
    public void initMenu(){
        menu = new Table();
        menu.setFillParent(true);
        menu.right();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.BLACK;

        openTerrain = new TextButton("Open Terrain", textButtonStyle);
        openTerrain.setName("Open");
        openTerrain.addListener(new ClickListener(Input.Buttons.LEFT){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeTerrainType(openTerrain);
            }
        });
        grassTerrain = new TextButton("Grass Terrain", textButtonStyle);
        grassTerrain.setName("Grass");
        grassTerrain.addListener(new ClickListener(Input.Buttons.LEFT){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeTerrainType(grassTerrain);
            }
        });
        swampTerrain = new TextButton("Swamp Terrain", textButtonStyle);
        swampTerrain.setName("Swamp");
        swampTerrain.addListener(new ClickListener(Input.Buttons.LEFT){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeTerrainType(swampTerrain);
            }
        });
        obstacle = new TextButton("Obstacle Terrain", textButtonStyle);
        obstacle.setName("Obstacle");
        obstacle.addListener(new ClickListener(Input.Buttons.LEFT){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeTerrainType(obstacle);
            }
        });
        start = new TextButton("START", textButtonStyle);
        restart =  new TextButton("restart", textButtonStyle);

        menu.add(openTerrain).padRight(50f).padBottom(25f);
        menu.row();
        menu.add(grassTerrain).padRight(50f).padBottom(25f);
        menu.row();
        menu.add(swampTerrain).padRight(50f).padBottom(25f);
        menu.row();
        menu.add(obstacle).padRight(50f).padBottom(25f);
        menu.row();
        menu.add(start).padRight(50f).padBottom(25f);
        menu.row();
        menu.add(restart).padRight(50f).padBottom(25f);

        stage.addActor(menu);

    }

    public void initClickableTiles(){

        clickableActors = new Table();
        clickableActors.setFillParent(true);
        clickableActors.left();

        for(int i  = 0; i < ROW; i++){
            for(int j  = 0; j < COL; j++){
                Actor act = new Actor();
                act.setBounds(i*TILE_WIDTH, j*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                act.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        changeTile(act.getX()/50, act.getY()/50);
                    }
                });
                clickableActors.add(act);
            }
            clickableActors.row();
        }

        stage.addActor(clickableActors);
        Gdx.input.setInputProcessor(stage);
    }

   public void  changeTile(float x, float y){
            Gdx.app.log("", String.valueOf(x) + " " + String.valueOf(y));
    }

    public void changeTerrainType(Button button){
            String s = button.getName();
            Gdx.app.log("", s);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(222/225f,184/225f,135/225f, 1);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stage.draw();
        
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
