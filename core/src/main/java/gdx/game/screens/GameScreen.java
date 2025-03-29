package gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import gdx.game.ScreenManager;
import gdx.game.helpers.AssetManager;
import gdx.game.helpers.InputHandler;
import gdx.game.objects.BackgroundScrollHandler;
import gdx.game.objects.DemonEnemy;
import gdx.game.objects.Enemy;
import gdx.game.objects.ForegroundScrollHandler;
import gdx.game.objects.Player;
import gdx.game.objects.Projectile;
import gdx.game.objects.SkeletonEnemy;
import gdx.game.objects.WormEnemy;
import gdx.game.utils.Settings;

public class GameScreen implements Screen {
    private float accumulator = 0.0f;
    private BackgroundScrollHandler backgroundScrollHandler;
    private Batch batch;
    private Table buttonTable;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private boolean demonSpawned = false;
    private Array<Enemy> enemies;
    private ForegroundScrollHandler foregroundScrollHandler;
    private float gameOverTransitionDelay = 1.0f;
    /* access modifiers changed from: private */
    public boolean gameover = false;
    private boolean gameoverWindowCreated = false;
    private Table healthTable;
    private ProgressBar healthbar;
    private float highscore;
    private int hp;
    private InputHandler inputHandler;
    private long lastEnemyTime;
    private long nextEnemyTime;
    /* access modifiers changed from: private */
    public Player player;
    private Body playerbody;
    private float score;
    private Label scoreLabel;
    private Table scoreTable;
    private ShapeRenderer shapeRenderer;
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    private Stage stage;
    private float timeSinceTransition = 0.0f;
    private World world;

    public GameScreen() {
        Box2D.init();
        OrthographicCamera orthographicCamera = new OrthographicCamera(1280.0f, 720.0f);
        this.camera = orthographicCamera;
        orthographicCamera.setToOrtho(false, 1280.0f, 720.0f);
        FitViewport fitViewport = new FitViewport(1280.0f, 720.0f);
        this.shapeRenderer = new ShapeRenderer();
        Stage stage2 = new Stage(fitViewport);
        this.stage = stage2;
        this.batch = stage2.getBatch();
        ForegroundScrollHandler foregroundScrollHandler2 = new ForegroundScrollHandler();
        this.foregroundScrollHandler = foregroundScrollHandler2;
        this.stage.addActor(foregroundScrollHandler2);
        BackgroundScrollHandler backgroundScrollHandler2 = new BackgroundScrollHandler();
        this.backgroundScrollHandler = backgroundScrollHandler2;
        this.stage.addActor(backgroundScrollHandler2);
        Player player2 = new Player(310.0f, 102.0f, 4.0f);
        this.player = player2;
        this.stage.addActor(player2);
        createWorld();
        this.inputHandler = new InputHandler(this);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this.stage);
        multiplexer.addProcessor(this.inputHandler);
        Gdx.input.setInputProcessor(multiplexer);
        Table table = new Table();
        this.buttonTable = table;
        table.setPosition(0.0f, 0.0f);
        this.stage.addActor(this.buttonTable);
        this.buttonTable.bottom().left();
        Button button = new Button(this.skin, "arcade");
        this.buttonTable.add(button).pad(60.0f);
        button.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!GameScreen.this.gameover) {
                    GameScreen.this.player.attack();
                }
            }
        });
        Table table2 = new Table();
        this.healthTable = table2;
        table2.setPosition(0.0f, 720.0f);
        this.stage.addActor(this.healthTable);
        this.healthTable.top().left();
        ProgressBar progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, this.skin, "health");
        this.healthbar = progressBar;
        this.hp = 100;
        progressBar.setValue((float) 100);
        this.healthTable.add(this.healthbar).pad(60.0f).size(500.0f, 92.0f);
        Table table3 = new Table();
        this.scoreTable = table3;
        table3.setPosition(1280.0f, 720.0f);
        this.stage.addActor(this.scoreTable);
        this.scoreTable.top().right();
        Label label = new Label("0 m", this.skin, "title");
        this.scoreLabel = label;
        this.scoreTable.add(label).pad(60.0f);
        this.enemies = new Array<>();
        this.nextEnemyTime = MathUtils.random((long) Settings.ENEMY_MIN_COUNTER, (long) Settings.ENEMY_MAX_COUNTER);
        this.player.toBack();
        button.toFront();
        this.score = 0.0f;
        this.highscore = Gdx.app.getPreferences("data").getFloat("score");
        AssetManager.music.play();
    }

    private void spawnDemon() {
        Enemy enemy = new DemonEnemy(1380.0f, 150.0f, 3.0f, -300.0f, 20);
        this.enemies.add(enemy);
        this.stage.addActor(enemy);
        this.player.toBack();
        enemy.toBack();
        this.backgroundScrollHandler.toBack();
        this.demonSpawned = true;
    }

    private void createWorld() {
        this.world = new World(new Vector2(0.0f, -900.0f), false);
        this.debugRenderer = new Box2DDebugRenderer();
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0.0f, 10.0f));
        Body groundBody = this.world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(this.camera.viewportWidth, 90.0f);
        groundBody.createFixture(groundBox, 0.0f);
        Body createBody = this.world.createBody(this.player.getBodyDef());
        this.playerbody = createBody;
        Fixture createFixture = createBody.createFixture(this.player.getFixtureDef());
        this.player.setBody(this.playerbody);
    }

    public void show() {
    }

    public void render(float delta) {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f);
        this.stage.draw();
        this.stage.act(delta);
        doPhysicsStep(delta);
        boolean z = this.gameover;
        if (!z && !this.demonSpawned) {
            this.score += 3.0f * delta;
        }
        if (z) {
            float f = this.timeSinceTransition + delta;
            this.timeSinceTransition = f;
            if (f >= this.gameOverTransitionDelay && !this.gameoverWindowCreated) {
                createGameOverWindow(false);
            }
        }
        this.scoreLabel.setText(((int) this.score) + " m");
        this.healthbar.setValue((float) this.hp);
        this.player.setPosition(this.playerbody.getPosition(), true);
        enemiesRender();
        if (this.score < 500.0f) {
            if (TimeUtils.nanoTime() - this.lastEnemyTime > this.nextEnemyTime && !this.gameover) {
                spawnEnemy();
            }
        } else if (!this.demonSpawned) {
            spawnDemon();
        }
        if (this.score > this.highscore) {
            this.scoreLabel.setColor(Color.YELLOW);
        }
        playerAttack();
    }

    private void playerAttack() {
        for (int i = 0; i < this.enemies.size; i++) {
            if (this.player.attackCollides(this.enemies.get(i))) {
                this.enemies.get(i).hurt();
            } else if (this.enemies.get(i).isOutOfScreen()) {
                if (this.enemies.get(i) instanceof DemonEnemy) {
                    createGameOverWindow(true);
                }
                this.enemies.get(i).remove();
                this.enemies.removeIndex(i);
            }
        }
    }

    private void enemiesRender() {
        for (int i = 0; i < this.enemies.size; i++) {
            if (this.enemies.get(i).canAttack(this.player)) {
                this.enemies.get(i).chargeAttack();
            }
            if (this.enemies.get(i).collides(this.player) && this.enemies.get(i).attack()) {
                int i2 = this.hp - 20;
                this.hp = i2;
                if (i2 <= 0) {
                    this.player.die();
                    finishGame();
                } else {
                    this.player.hurt();
                }
            }
            if (this.enemies.get(i) instanceof WormEnemy) {
                WormEnemy worm = (WormEnemy) this.enemies.get(i);
                if (worm.isSpawningBullet()) {
                    Projectile fireball = new Projectile(worm.getPosition().x, worm.getPosition().y + 30.0f, 3.0f, -500.0f);
                    this.enemies.add(fireball);
                    this.stage.addActor(fireball);
                    fireball.toBack();
                    this.player.toBack();
                    this.backgroundScrollHandler.toBack();
                    worm.setSpawningBullet(false);
                }
            }
            if ((this.enemies.get(i) instanceof DemonEnemy) && !this.gameover) {
                DemonEnemy demon = (DemonEnemy) this.enemies.get(i);
                if (demon.isAttacking() && demon.attack(this.player)) {
                    int i3 = this.hp - 20;
                    this.hp = i3;
                    if (i3 <= 0) {
                        this.player.die();
                        finishGame();
                    } else {
                        this.player.hurt();
                    }
                }
            }
        }
    }

    private void createGameOverWindow(boolean win) {
        this.gameoverWindowCreated = true;
        Table root = new Table();
        root.setFillParent(true);
        Table gameOverTable = new Table(this.skin);
        root.add(gameOverTable);
        gameOverTable.setBackground("window-round");
        gameOverTable.defaults().pad(10.0f);
        TextButton exitButton = new TextButton("SORTIR", this.skin);
        exitButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(new FirstScreen());
            }
        });
        if (win) {
            Label resultLabel = new Label("Has completat la demo!", this.skin, "title");
            resultLabel.setColor(Color.YELLOW);
            gameOverTable.add(resultLabel).row();
        } else if (this.highscore < this.score) {
            Gdx.app.getPreferences("data").putFloat("score", this.score).flush();
            Label resultLabel2 = new Label("RECORD: " + ((int) this.score) + " m", this.skin, "title");
            resultLabel2.setColor(Color.YELLOW);
            gameOverTable.add(resultLabel2).row();
        } else {
            gameOverTable.add(new Label("Puntuacio: " + ((int) this.score) + " m", this.skin, "subtitle")).row();
            Label highScoreLabel = new Label("Puntuacio maxima: " + ((int) this.highscore) + " m", this.skin);
            highScoreLabel.setColor(Color.YELLOW);
            gameOverTable.add(highScoreLabel).row();
        }
        gameOverTable.add(exitButton);
        gameOverTable.pack();
        this.stage.addActor(root);
    }

    private void finishGame() {
        this.gameover = true;
        for (int i = 0; i < this.enemies.size; i++) {
            this.enemies.get(i).setSpeed(0.0f);
        }
        this.foregroundScrollHandler.setBgSpeed(0.0f);
        this.backgroundScrollHandler.setBgSpeed(0.0f);
    }

    private void doPhysicsStep(float deltaTime) {
        this.accumulator += Math.min(deltaTime, 0.25f);
        while (this.accumulator >= 0.004166667f) {
            this.world.step(0.004166667f, 60, 2);
            this.accumulator -= 0.004166667f;
        }
    }

    private void spawnEnemy() {
        Enemy enemy;
        if (Math.random() < 0.800000011920929d) {
            enemy = new SkeletonEnemy(1380.0f, 100.0f, 3.0f, -200.0f);
        } else {
            enemy = new WormEnemy(1380.0f, 100.0f, 3.0f, -100.0f);
        }
        this.enemies.add(enemy);
        this.stage.addActor(enemy);
        enemy.toBack();
        this.player.toBack();
        this.backgroundScrollHandler.toBack();
        this.lastEnemyTime = TimeUtils.nanoTime();
        this.nextEnemyTime = MathUtils.random((long) Settings.ENEMY_MIN_COUNTER, (long) Settings.ENEMY_MAX_COUNTER);
    }

    private void drawElements() {
        this.buttonTable.setDebug(true);
        this.healthTable.setDebug(true);
        this.debugRenderer.render(this.world, this.camera.combined);
        this.shapeRenderer.setProjectionMatrix(this.batch.getProjectionMatrix());
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.GREEN);
        Rectangle rangeCollisionRect = this.player.getRangeCollisionRect();
        if (rangeCollisionRect != null) {
            this.shapeRenderer.rect(rangeCollisionRect.x, rangeCollisionRect.y, rangeCollisionRect.width, rangeCollisionRect.height);
        }
        for (int i = 0; i < this.enemies.size; i++) {
            Rectangle enemyAttack = this.enemies.get(i).getAttackCollisionRect();
            if (enemyAttack != null) {
                this.shapeRenderer.rect(enemyAttack.x, enemyAttack.y, enemyAttack.width, enemyAttack.height);
            }
        }
        this.shapeRenderer.setColor(Color.RED);
        this.shapeRenderer.rect(this.player.getX(), this.player.getY(), this.player.getWidth(), this.player.getHeight());
        Rectangle attackCollisionRect = this.player.getAttackCollisionRect();
        for (int i2 = 0; i2 < this.enemies.size; i2++) {
            Enemy enemy = this.enemies.get(i2);
            this.shapeRenderer.rect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }
        if (attackCollisionRect != null) {
            this.shapeRenderer.setColor(Color.YELLOW);
            this.shapeRenderer.rect(attackCollisionRect.x, attackCollisionRect.y, attackCollisionRect.width, attackCollisionRect.height);
        }
        this.shapeRenderer.end();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }

    public void dispose() {
        AssetManager.dispose();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Stage getStage() {
        return this.stage;
    }

    public boolean isGameover() {
        return this.gameover;
    }
}
