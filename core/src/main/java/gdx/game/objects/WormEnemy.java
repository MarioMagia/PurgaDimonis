package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import gdx.game.helpers.AssetManager;

public class WormEnemy extends Enemy {
    private Rectangle attackCollisionRect;
    private float attackTransitionDelay = 0.28f;
    private boolean canShoot;
    private float chargingAttackTransitionDelay = 0.63f;
    private Rectangle collisionRect;
    float dyingTransitionDelay = 0.49f;
    private EnemyState enemyState;
    private int height;
    private boolean isSpawningBullet;
    float timeSinceShoot = 0.0f;
    float timeSinceTransition = 0.0f;
    private int width;

    enum EnemyState {
        IDLE,
        DYING,
        CHARGING_ATTACK,
        ATTACKING,
        DEAD
    }

    public WormEnemy(float x, float y, float scale, float speed) {
        super(x, y, scale, speed);
        this.width = Math.round(51.0f * scale);
        this.height = Math.round(25.0f * scale);
        this.collisionRect = new Rectangle();
        this.attackCollisionRect = new Rectangle();
        setBounds(x, y, (float) this.width, (float) this.height);
        setTouchable(Touchable.enabled);
        this.enemyState = EnemyState.IDLE;
        this.canShoot = false;
        this.isSpawningBullet = false;
    }

    public void act(float delta) {
        super.act(delta);
        Vector2 position = super.getPosition();
        Rectangle rectangle = this.collisionRect;
        if (rectangle != null) {
            rectangle.set(position.x, position.y, (float) this.width, (float) this.height);
        }
        Rectangle rectangle2 = this.attackCollisionRect;
        if (rectangle2 != null) {
            rectangle2.set(position.x - 100.0f, position.y, 100.0f, (float) this.height);
        }
        position.x += super.getSpeed() * delta;
        setBounds(position.x, position.y, (float) this.width, (float) this.height);
        if (position.x + ((float) this.width) < 0.0f) {
            super.setOutOfScreen(true);
        }
        float f = this.timeSinceShoot + delta;
        this.timeSinceShoot = f;
        if (f >= 4.0f) {
            this.timeSinceShoot = 0.0f;
            this.timeSinceTransition = 0.0f;
            this.canShoot = true;
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        super.setStateTime(super.getStateTime() + Gdx.graphics.getDeltaTime());
        float stateTime = super.getStateTime();
        Vector2 position = super.getPosition();
        float scale = super.getScale();
        switch (AnonymousClass1.$SwitchMap$gdx$game$objects$WormEnemy$EnemyState[this.enemyState.ordinal()]) {
            case 1:
                batch.draw((TextureRegion) AssetManager.wormIdle.getKeyFrame(stateTime, true), (position.x - ((scale * 90.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 90.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 90.0f, scale * 90.0f);
                return;
            case 2:
                float deltaTime = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime;
                if (deltaTime >= this.dyingTransitionDelay) {
                    this.enemyState = EnemyState.DEAD;
                }
                batch.draw((TextureRegion) AssetManager.wormDying.getKeyFrame(stateTime, true), (position.x - ((scale * 90.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 90.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 90.0f, scale * 90.0f);
                return;
            case 3:
                float deltaTime2 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime2;
                if (deltaTime2 >= this.attackTransitionDelay) {
                    this.enemyState = EnemyState.IDLE;
                }
                batch.draw((TextureRegion) AssetManager.wormAttack.getKeyFrame(stateTime, true), (position.x - ((scale * 90.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 90.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 90.0f, scale * 90.0f);
                return;
            case 4:
                float deltaTime3 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime3;
                if (deltaTime3 >= this.chargingAttackTransitionDelay) {
                    this.enemyState = EnemyState.ATTACKING;
                    AssetManager.fireballShotSound.play(AssetManager.soundVolume);
                    this.isSpawningBullet = true;
                    this.timeSinceTransition = 0.0f;
                }
                batch.draw((TextureRegion) AssetManager.wormChargingAttack.getKeyFrame(stateTime, true), (position.x - ((scale * 90.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 90.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 90.0f, scale * 90.0f);
                return;
            case 5:
                batch.draw((TextureRegion) AssetManager.wormDeadSprite, (position.x - ((scale * 90.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 90.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 90.0f, scale * 90.0f);
                return;
            default:
                return;
        }
    }

    /* renamed from: gdx.game.objects.WormEnemy$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$gdx$game$objects$WormEnemy$EnemyState;

        static {
            int[] iArr = new int[EnemyState.values().length];
            $SwitchMap$gdx$game$objects$WormEnemy$EnemyState = iArr;
            try {
                iArr[EnemyState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$gdx$game$objects$WormEnemy$EnemyState[EnemyState.DYING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$gdx$game$objects$WormEnemy$EnemyState[EnemyState.ATTACKING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$gdx$game$objects$WormEnemy$EnemyState[EnemyState.CHARGING_ATTACK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$gdx$game$objects$WormEnemy$EnemyState[EnemyState.DEAD.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void hurt() {
        this.enemyState = EnemyState.DYING;
        super.setStateTime(0.0f);
        this.timeSinceTransition = 0.0f;
        this.collisionRect = null;
        this.attackCollisionRect = null;
        setSpeed(-200.0f);
        AssetManager.wormSquishSound.play(AssetManager.soundVolume);
    }

    public boolean collides(Player player) {
        return false;
    }

    public boolean attack() {
        if (this.enemyState != EnemyState.IDLE) {
            return false;
        }
        this.enemyState = EnemyState.ATTACKING;
        super.setStateTime(0.0f);
        this.timeSinceTransition = 0.0f;
        return true;
    }

    public void chargeAttack() {
        if (this.enemyState == EnemyState.IDLE) {
            this.enemyState = EnemyState.CHARGING_ATTACK;
            this.canShoot = false;
            super.setStateTime(0.0f);
        }
    }

    public boolean canAttack(Player player) {
        return this.canShoot;
    }

    public Rectangle getAttackCollisionRect() {
        return null;
    }

    public Rectangle getCollisionRect() {
        return this.collisionRect;
    }

    public boolean isSpawningBullet() {
        return this.isSpawningBullet;
    }

    public void setSpawningBullet(boolean spawningBullet) {
        this.isSpawningBullet = spawningBullet;
    }
}
