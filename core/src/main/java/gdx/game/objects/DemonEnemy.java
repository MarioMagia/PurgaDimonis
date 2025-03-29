package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import gdx.game.helpers.AssetManager;

public class DemonEnemy extends Enemy {
    private float attackingTransitionDelay = 0.315f;
    private int bossHp;
    private boolean canShoot;
    private float chargingAttackTransitionDelay = 0.55f;
    private Rectangle collisionRect;
    private float damageTransitionDelay = 0.07f;
    private float dyingTransitionDelay = 0.91f;
    private EnemyState enemyState;
    private boolean hasDied;
    private final int height;
    private boolean isAttacking;
    private boolean isDamaged;
    private float nextShootTime = 5.0f;
    float timeSinceDmgTransition = 0.0f;
    float timeSinceShoot = 0.0f;
    float timeSinceTransition = 0.0f;
    private final int width;

    enum EnemyState {
        IDLE,
        ATTACKING,
        CHARGING_ATTACK,
        DYING
    }

    public DemonEnemy(float x, float y, float scale, float speed, int bossHp2) {
        super(x, y, scale, speed);
        int round = Math.round(49.0f * scale);
        this.width = round;
        int round2 = Math.round(92.0f * scale);
        this.height = round2;
        this.bossHp = bossHp2;
        this.collisionRect = new Rectangle();
        setBounds(x, y, (float) round, (float) round2);
        setTouchable(Touchable.enabled);
        this.enemyState = EnemyState.IDLE;
        this.hasDied = false;
        this.isDamaged = false;
    }

    public void act(float delta) {
        super.act(delta);
        Vector2 position = super.getPosition();
        this.collisionRect.set(position.x - 50.0f, position.y, (float) this.width, (float) this.height);
        position.x += super.getSpeed() * delta;
        setBounds(position.x - 50.0f, position.y, (float) this.width, (float) this.height);
        if (position.x + ((float) this.width) < 0.0f) {
            super.setOutOfScreen(true);
        }
        if (position.x <= 512.0f && !this.hasDied) {
            super.setSpeed(0.0f);
        }
        float f = this.timeSinceShoot + delta;
        this.timeSinceShoot = f;
        if (f >= this.nextShootTime) {
            this.timeSinceShoot = 0.0f;
            this.timeSinceTransition = 0.0f;
            this.canShoot = true;
            this.nextShootTime = MathUtils.random(1.0f, 5.0f);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        super.setStateTime(super.getStateTime() + Gdx.graphics.getDeltaTime());
        float stateTime = super.getStateTime();
        Vector2 position = super.getPosition();
        float scale = super.getScale();
        switch (AnonymousClass1.$SwitchMap$gdx$game$objects$DemonEnemy$EnemyState[this.enemyState.ordinal()]) {
            case 1:
                if (this.isDamaged) {
                    float deltaTime = this.timeSinceDmgTransition + Gdx.graphics.getDeltaTime();
                    this.timeSinceDmgTransition = deltaTime;
                    if (deltaTime >= this.damageTransitionDelay) {
                        this.isDamaged = false;
                    }
                    batch.draw((TextureRegion) AssetManager.demonIdleWhite.getKeyFrame(stateTime, true), (position.x - ((scale * 160.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 144.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 160.0f, scale * 144.0f);
                    return;
                }
                batch.draw((TextureRegion) AssetManager.demonIdle.getKeyFrame(stateTime, true), (position.x - ((scale * 160.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 144.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 160.0f, scale * 144.0f);
                return;
            case 2:
                float deltaTime2 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime2;
                if (deltaTime2 >= this.attackingTransitionDelay) {
                    this.enemyState = EnemyState.IDLE;
                }
                if (this.isDamaged) {
                    float deltaTime3 = this.timeSinceDmgTransition + Gdx.graphics.getDeltaTime();
                    this.timeSinceDmgTransition = deltaTime3;
                    if (deltaTime3 >= this.damageTransitionDelay) {
                        this.isDamaged = false;
                    }
                    batch.draw((TextureRegion) AssetManager.demonAttackWhite.getKeyFrame(stateTime, true), (position.x - ((scale * 240.0f) / 1.4f)) + ((float) this.width), (position.y - ((scale * 192.0f) / 1.5f)) + ((float) this.height), scale * 240.0f, scale * 192.0f);
                    return;
                }
                batch.draw((TextureRegion) AssetManager.demonAttack.getKeyFrame(stateTime, true), (position.x - ((scale * 240.0f) / 1.4f)) + ((float) this.width), (position.y - ((scale * 192.0f) / 1.5f)) + ((float) this.height), scale * 240.0f, scale * 192.0f);
                return;
            case 3:
                float deltaTime4 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime4;
                if (deltaTime4 >= this.chargingAttackTransitionDelay) {
                    this.enemyState = EnemyState.ATTACKING;
                    AssetManager.fireballShotSound.play(AssetManager.soundVolume);
                    super.setStateTime(0.0f);
                    this.isAttacking = true;
                    this.timeSinceTransition = 0.0f;
                }
                if (this.isDamaged) {
                    float deltaTime5 = this.timeSinceDmgTransition + Gdx.graphics.getDeltaTime();
                    this.timeSinceDmgTransition = deltaTime5;
                    if (deltaTime5 >= this.damageTransitionDelay) {
                        this.isDamaged = false;
                    }
                    batch.draw((TextureRegion) AssetManager.demonChargingAttackWhite.getKeyFrame(stateTime, true), (position.x - ((scale * 240.0f) / 1.4f)) + ((float) this.width), (position.y - ((scale * 192.0f) / 1.5f)) + ((float) this.height), scale * 240.0f, scale * 192.0f);
                    return;
                }
                batch.draw((TextureRegion) AssetManager.demonChargingAttack.getKeyFrame(stateTime, true), (position.x - ((scale * 240.0f) / 1.4f)) + ((float) this.width), (position.y - ((scale * 192.0f) / 1.5f)) + ((float) this.height), scale * 240.0f, scale * 192.0f);
                return;
            case 4:
                this.timeSinceTransition += Gdx.graphics.getDeltaTime();
                if (stateTime >= this.dyingTransitionDelay) {
                    super.setOutOfScreen(true);
                }
                if (stateTime < this.dyingTransitionDelay - 0.14f) {
                    batch.draw((TextureRegion) AssetManager.demonIdleWhite.getKeyFrame(0.0f, true), (position.x - ((scale * 160.0f) / 2.0f)) + ((float) (this.width / 2)), (position.y - ((scale * 144.0f) / 2.0f)) + ((float) (this.height / 2)), scale * 160.0f, scale * 144.0f);
                }
                batch.draw((TextureRegion) AssetManager.explosion.getKeyFrame(stateTime, true), position.x - (scale * 64.0f), position.y - ((scale * 64.0f) / 2.0f), scale * 64.0f * 2.5f, 64.0f * scale * 2.5f);
                return;
            default:
                return;
        }
    }

    /* renamed from: gdx.game.objects.DemonEnemy$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$gdx$game$objects$DemonEnemy$EnemyState;

        static {
            int[] iArr = new int[EnemyState.values().length];
            $SwitchMap$gdx$game$objects$DemonEnemy$EnemyState = iArr;
            try {
                iArr[EnemyState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$gdx$game$objects$DemonEnemy$EnemyState[EnemyState.ATTACKING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$gdx$game$objects$DemonEnemy$EnemyState[EnemyState.CHARGING_ATTACK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$gdx$game$objects$DemonEnemy$EnemyState[EnemyState.DYING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void hurt() {
        if (!this.hasDied) {
            this.bossHp--;
            this.isDamaged = true;
            this.timeSinceDmgTransition = 0.0f;
            Gdx.app.log("hurt", new StringBuilder().append(this.bossHp).toString());
            if (this.bossHp <= 0) {
                this.hasDied = true;
                this.timeSinceTransition = 0.0f;
                super.setStateTime(0.0f);
                this.enemyState = EnemyState.DYING;
            }
        }
    }

    public boolean collides(Player player) {
        return false;
    }

    public boolean attack(Player player) {
        if (this.enemyState != EnemyState.ATTACKING || player.isJumping() || this.hasDied) {
            return false;
        }
        super.setStateTime(0.0f);
        this.timeSinceTransition = 0.0f;
        this.canShoot = false;
        this.isAttacking = false;
        return true;
    }

    public boolean attack() {
        return false;
    }

    public void chargeAttack() {
        if (this.enemyState == EnemyState.IDLE && !this.hasDied) {
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

    public boolean isAttacking() {
        return this.isAttacking;
    }
}
