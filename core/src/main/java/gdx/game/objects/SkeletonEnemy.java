package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import gdx.game.helpers.AssetManager;

public class SkeletonEnemy extends Enemy {
    private Rectangle attackCollisionRect;
    float attackingTransitionDelay = 0.18900001f;
    private Rectangle collisionRect;
    float dyingTransitionDelay = 0.3f;
    private EnemyState enemyState;
    private int height;
    float timeSinceTransition = 0.0f;
    private int width;

    enum EnemyState {
        IDLE,
        DYING,
        DEAD,
        CHARGING_ATTACK,
        ATTACKING
    }

    public SkeletonEnemy(float x, float y, float scale, float speed) {
        super(x, y, scale, speed);
        this.width = Math.round(40.0f * scale);
        this.height = Math.round(51.0f * scale);
        this.collisionRect = new Rectangle();
        this.attackCollisionRect = new Rectangle();
        setBounds(x, y, (float) this.width, (float) this.height);
        setTouchable(Touchable.enabled);
        this.enemyState = EnemyState.IDLE;
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
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        super.setStateTime(super.getStateTime() + Gdx.graphics.getDeltaTime());
        float stateTime = super.getStateTime();
        Vector2 position = super.getPosition();
        float scale = super.getScale();
        switch (AnonymousClass1.$SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState[this.enemyState.ordinal()]) {
            case 1:
                batch.draw((TextureRegion) AssetManager.skeletonIdle.getKeyFrame(stateTime, true), (position.x - ((scale * 150.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 150.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 150.0f, scale * 150.0f);
                return;
            case 2:
                float deltaTime = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime;
                if (deltaTime >= this.dyingTransitionDelay) {
                    this.enemyState = EnemyState.DEAD;
                }
                batch.draw((TextureRegion) AssetManager.skeletonDying.getKeyFrame(stateTime, true), (position.x - ((scale * 150.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 150.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 150.0f, scale * 150.0f);
                return;
            case 3:
                batch.draw(AssetManager.skeletonDead, (position.x - ((scale * 150.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 150.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 150.0f, scale * 150.0f);
                return;
            case 4:
                float deltaTime2 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime2;
                if (deltaTime2 >= this.attackingTransitionDelay) {
                    this.enemyState = EnemyState.IDLE;
                }
                batch.draw((TextureRegion) AssetManager.skeletonAttack.getKeyFrame(stateTime, true), (position.x - ((scale * 150.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 150.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 150.0f, scale * 150.0f);
                return;
            case 5:
                batch.draw((TextureRegion) AssetManager.skeletonChargingAttack.getKeyFrame(stateTime, true), (position.x - ((scale * 150.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 150.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 150.0f, scale * 150.0f);
                return;
            default:
                return;
        }
    }

    /* renamed from: gdx.game.objects.SkeletonEnemy$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState;

        static {
            int[] iArr = new int[EnemyState.values().length];
            $SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState = iArr;
            try {
                iArr[EnemyState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState[EnemyState.DYING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState[EnemyState.DEAD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState[EnemyState.ATTACKING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$gdx$game$objects$SkeletonEnemy$EnemyState[EnemyState.CHARGING_ATTACK.ordinal()] = 5;
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
        AssetManager.boneCrackSound.play(AssetManager.soundVolume);
    }

    public boolean attack() {
        this.enemyState = EnemyState.ATTACKING;
        super.setStateTime(0.0f);
        this.timeSinceTransition = 0.0f;
        this.collisionRect = null;
        this.attackCollisionRect = null;
        return true;
    }

    public boolean collides(Player player) {
        if (this.attackCollisionRect == null || player.getCollisionRect() == null) {
            return false;
        }
        return Intersector.overlaps(this.attackCollisionRect, player.getCollisionRect());
    }

    public boolean canAttack(Player player) {
        if (this.attackCollisionRect == null || player.getRangeCollisionRect() == null || this.enemyState != EnemyState.IDLE) {
            return false;
        }
        return Intersector.overlaps(this.attackCollisionRect, player.getRangeCollisionRect());
    }

    public void chargeAttack() {
        this.enemyState = EnemyState.CHARGING_ATTACK;
        super.setStateTime(0.0f);
    }

    public Rectangle getCollisionRect() {
        return this.collisionRect;
    }

    public Rectangle getAttackCollisionRect() {
        return this.attackCollisionRect;
    }
}
