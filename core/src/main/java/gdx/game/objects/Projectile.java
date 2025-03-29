package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import gdx.game.helpers.AssetManager;

public class Projectile extends Enemy {
    private Rectangle collisionRect;
    private EnemyState enemyState;
    private float explosionTransitionDelay = 0.42000002f;
    private final int height;
    private float timeSinceTransition = 0.0f;
    private final int width;

    enum EnemyState {
        MOVING,
        EXPLODING
    }

    public Projectile(float x, float y, float scale, float speed) {
        super(x, y, scale, speed);
        int round = Math.round(12.0f * scale);
        this.width = round;
        int round2 = Math.round(15.0f * scale);
        this.height = round2;
        this.collisionRect = new Rectangle();
        setBounds(x, y, (float) round, (float) round2);
        setTouchable(Touchable.enabled);
        this.enemyState = EnemyState.MOVING;
    }

    public void act(float delta) {
        super.act(delta);
        Vector2 position = super.getPosition();
        Rectangle rectangle = this.collisionRect;
        if (rectangle != null) {
            rectangle.set(position.x, position.y, (float) this.width, (float) this.height);
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
        switch (AnonymousClass1.$SwitchMap$gdx$game$objects$Projectile$EnemyState[this.enemyState.ordinal()]) {
            case 1:
                batch.draw((TextureRegion) AssetManager.fireballMoving.getKeyFrame(stateTime, true), (position.x - ((scale * 46.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 46.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 46.0f, scale * 46.0f);
                return;
            case 2:
                float deltaTime = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime;
                if (deltaTime >= this.explosionTransitionDelay) {
                    super.setOutOfScreen(true);
                }
                batch.draw((TextureRegion) AssetManager.fireballExplosion.getKeyFrame(stateTime, true), (position.x - ((scale * 46.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 46.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 46.0f, scale * 46.0f);
                return;
            default:
                return;
        }
    }

    /* renamed from: gdx.game.objects.Projectile$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$gdx$game$objects$Projectile$EnemyState;

        static {
            int[] iArr = new int[EnemyState.values().length];
            $SwitchMap$gdx$game$objects$Projectile$EnemyState = iArr;
            try {
                iArr[EnemyState.MOVING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$gdx$game$objects$Projectile$EnemyState[EnemyState.EXPLODING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void hurt() {
    }

    public boolean collides(Player player) {
        if (this.collisionRect == null || player.getCollisionRect() == null) {
            return false;
        }
        return Intersector.overlaps(this.collisionRect, player.getCollisionRect());
    }

    public boolean attack() {
        this.enemyState = EnemyState.EXPLODING;
        super.setStateTime(0.0f);
        super.setSpeed(0.0f);
        this.timeSinceTransition = 0.0f;
        this.collisionRect = null;
        AssetManager.fireballHitSound.play(AssetManager.soundVolume);
        return true;
    }

    public void chargeAttack() {
    }

    public boolean canAttack(Player player) {
        return false;
    }

    public Rectangle getAttackCollisionRect() {
        return null;
    }

    public Rectangle getCollisionRect() {
        return this.collisionRect;
    }
}
