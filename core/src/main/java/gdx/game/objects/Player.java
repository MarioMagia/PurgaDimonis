package gdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import gdx.game.helpers.AssetManager;

public class Player extends Actor {
    float attack2TransitionDelay = 0.35f;
    private boolean attackAgain;
    private Rectangle attackCollisionRect;
    float attackCooldown = 0.5f;
    float attackTransitionDelay = 0.21000001f;
    private Body body;
    private BodyDef bodyDef;
    private Rectangle collisionRect;
    float dyingTransitionDelay = 0.63f;
    private FixtureDef fixtureDef;
    private int height;
    float hitTransitionDelay = 0.28f;
    private boolean isJumping;
    float jumpFallTransitionDelay = 0.07f;
    PlayerState lastPlayerState = PlayerState.RUNNING;
    PlayerState playerState = PlayerState.RUNNING;
    private Vector2 position;
    private Rectangle rangeCollisionRect;
    private float scale;
    private float stateTime;
    float timeSinceAttack = 0.0f;
    float timeSinceTransition = 0.0f;
    private int width;

    enum PlayerState {
        RUNNING,
        JUMPING,
        FALLING,
        JUMP_FALL_TRANSITION,
        ATTACK,
        ATTACK2,
        HIT,
        DYING,
        DEAD
    }

    public Player(float x, float y, float scale2) {
        this.width = Math.round(20.0f * scale2);
        this.height = Math.round(38.0f * scale2);
        this.scale = scale2;
        this.position = new Vector2(x, y);
        this.collisionRect = new Rectangle();
        setBounds(this.position.x, this.position.y, (float) this.width, (float) this.height);
        setTouchable(Touchable.enabled);
        this.rangeCollisionRect = new Rectangle();
        this.stateTime = 0.0f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) (this.width / 2), (float) (this.height / 2));
        BodyDef bodyDef2 = new BodyDef();
        this.bodyDef = bodyDef2;
        bodyDef2.type = BodyDef.BodyType.DynamicBody;
        this.bodyDef.fixedRotation = true;
        this.bodyDef.position.set(this.position.x + ((float) (this.width / 2)), this.position.y + ((float) (this.height / 2)));
        FixtureDef fixtureDef2 = new FixtureDef();
        this.fixtureDef = fixtureDef2;
        fixtureDef2.shape = shape;
        this.fixtureDef.density = 1.0f;
        this.fixtureDef.friction = 0.0f;
        this.fixtureDef.restitution = 0.0f;
        this.isJumping = false;
        this.attackAgain = false;
    }

    public void act(float delta) {
        super.act(delta);
        Rectangle rectangle = this.collisionRect;
        if (rectangle != null) {
            rectangle.set(this.position.x, this.position.y, (float) this.width, (float) this.height);
        }
        Rectangle rectangle2 = this.rangeCollisionRect;
        if (rectangle2 != null) {
            rectangle2.set(this.position.x, this.position.y, ((float) this.width) * 1.8f, (float) this.height);
        }
        setBounds(this.position.x, this.position.y, (float) this.width, (float) this.height);
        float velocityY = this.body.getLinearVelocity().y;
        if (this.playerState == PlayerState.HIT) {
            float deltaTime = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
            this.timeSinceTransition = deltaTime;
            if (deltaTime >= this.hitTransitionDelay) {
                this.timeSinceTransition = 0.0f;
                this.playerState = velocityY < -1.0f ? PlayerState.FALLING : this.lastPlayerState;
            }
        } else if (velocityY > 1.0f && this.playerState != PlayerState.DYING && this.playerState != PlayerState.DEAD) {
            this.playerState = PlayerState.JUMPING;
        } else if (velocityY >= -1.0f || this.playerState == PlayerState.DYING) {
            if (this.playerState == PlayerState.ATTACK) {
                float deltaTime2 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime2;
                if (deltaTime2 >= this.attackTransitionDelay) {
                    this.attackCollisionRect = null;
                    this.playerState = this.attackAgain ? PlayerState.ATTACK2 : PlayerState.RUNNING;
                    if (this.attackAgain) {
                        this.timeSinceAttack = 0.0f;
                        Rectangle rectangle3 = new Rectangle();
                        this.attackCollisionRect = rectangle3;
                        rectangle3.setPosition(this.position.x + ((float) this.width), this.position.y);
                        this.attackCollisionRect.setSize(((float) this.width) * 1.5f, (float) this.height);
                        AssetManager.slash2Sound.play(AssetManager.soundVolume);
                    }
                    this.stateTime = 0.0f;
                    this.timeSinceTransition = 0.0f;
                }
            } else if (this.playerState == PlayerState.ATTACK2) {
                float deltaTime3 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime3;
                if (deltaTime3 >= this.attack2TransitionDelay) {
                    this.attackCollisionRect = null;
                    this.playerState = PlayerState.RUNNING;
                    this.timeSinceTransition = 0.0f;
                    this.attackAgain = false;
                }
            } else if (this.playerState == PlayerState.DYING) {
                float deltaTime4 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
                this.timeSinceTransition = deltaTime4;
                if (deltaTime4 >= this.dyingTransitionDelay) {
                    this.playerState = PlayerState.DEAD;
                    Gdx.app.log("dead", "game over");
                    AssetManager.evilLaughSound.play(AssetManager.soundVolume);
                }
            } else if (!(this.playerState == PlayerState.DEAD || this.playerState == PlayerState.JUMPING)) {
                if (this.playerState == PlayerState.FALLING) {
                    AssetManager.fallGroundSound.play(AssetManager.soundVolume);
                    this.isJumping = false;
                }
                this.playerState = PlayerState.RUNNING;
            }
        } else if (this.playerState == PlayerState.JUMP_FALL_TRANSITION) {
            float deltaTime5 = this.timeSinceTransition + Gdx.graphics.getDeltaTime();
            this.timeSinceTransition = deltaTime5;
            if (deltaTime5 >= this.jumpFallTransitionDelay) {
                this.playerState = PlayerState.FALLING;
                this.timeSinceTransition = 0.0f;
            }
        } else if (this.playerState == PlayerState.JUMPING) {
            this.playerState = PlayerState.JUMP_FALL_TRANSITION;
            this.stateTime = 0.0f;
            this.timeSinceTransition = 0.0f;
        }
        this.timeSinceAttack += delta;
    }

    public void attack() {
        if (this.body.getLinearVelocity().y < 1.0f && this.body.getLinearVelocity().y > -1.0f && this.playerState != PlayerState.JUMP_FALL_TRANSITION) {
            if (this.playerState == PlayerState.ATTACK || this.playerState == PlayerState.ATTACK2 || this.playerState == PlayerState.HIT) {
                if (this.playerState == PlayerState.ATTACK) {
                    this.attackAgain = true;
                }
            } else if (this.timeSinceAttack >= this.attackCooldown) {
                this.timeSinceAttack = 0.0f;
                this.playerState = PlayerState.ATTACK;
                this.stateTime = 0.0f;
                Rectangle rectangle = new Rectangle();
                this.attackCollisionRect = rectangle;
                rectangle.setPosition(this.position.x + ((float) this.width), this.position.y);
                this.attackCollisionRect.setSize(((float) this.width) * 2.0f, (float) this.height);
                AssetManager.slashSound.play(AssetManager.soundVolume);
            }
        }
    }

    public void hurt() {
        this.lastPlayerState = this.playerState;
        this.playerState = PlayerState.HIT;
        AssetManager.hitSound.play(AssetManager.soundVolume);
    }

    public Rectangle getRangeCollisionRect() {
        return this.rangeCollisionRect;
    }

    public void die() {
        this.playerState = PlayerState.DYING;
        this.collisionRect = null;
        this.rangeCollisionRect = null;
        this.stateTime = 0.0f;
        AssetManager.music.pause();
        AssetManager.hitSound.play(AssetManager.soundVolume);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.stateTime += Gdx.graphics.getDeltaTime();
        switch (AnonymousClass1.$SwitchMap$gdx$game$objects$Player$PlayerState[this.playerState.ordinal()]) {
            case 1:
                float f = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f2 = this.position.y;
                float f3 = this.scale;
                batch.draw((TextureRegion) AssetManager.running.getKeyFrame(this.stateTime, true), f, f2, f3 * 120.0f, f3 * 80.0f);
                return;
            case 2:
                float f4 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f5 = this.position.y;
                float f6 = this.scale;
                batch.draw((TextureRegion) AssetManager.jump.getKeyFrame(this.stateTime, true), f4, f5, f6 * 120.0f, f6 * 80.0f);
                return;
            case 3:
                float f7 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f8 = this.position.y;
                float f9 = this.scale;
                batch.draw((TextureRegion) AssetManager.fall.getKeyFrame(this.stateTime, true), f7, f8, f9 * 120.0f, f9 * 80.0f);
                return;
            case 4:
                float f10 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f11 = this.position.y;
                float f12 = this.scale;
                batch.draw((TextureRegion) AssetManager.jumpFallInBetween.getKeyFrame(this.stateTime, true), f10, f11, f12 * 120.0f, f12 * 80.0f);
                return;
            case 5:
                float f13 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f14 = this.position.y;
                float f15 = this.scale;
                batch.draw((TextureRegion) AssetManager.attack.getKeyFrame(this.stateTime, true), f13, f14, f15 * 120.0f, f15 * 80.0f);
                return;
            case 6:
                float f16 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f17 = this.position.y;
                float f18 = this.scale;
                batch.draw((TextureRegion) AssetManager.attack2.getKeyFrame(this.stateTime, true), f16, f17, f18 * 120.0f, f18 * 80.0f);
                return;
            case 7:
                Texture texture = AssetManager.hit;
                float f19 = ((float) (this.width / 2)) + (this.position.x - ((this.scale * 120.0f) / 2.0f));
                float f20 = this.position.y;
                float f21 = this.scale;
                batch.draw(texture, f19, f20, 120.0f * f21, f21 * 80.0f);
                return;
            case 8:
                float f22 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f23 = this.position.y;
                float f24 = this.scale;
                batch.draw((TextureRegion) AssetManager.dying.getKeyFrame(this.stateTime, true), f22, f23, f24 * 120.0f, f24 * 80.0f);
                return;
            case 9:
                Texture texture2 = AssetManager.dead;
                float f25 = (this.position.x - ((this.scale * 120.0f) / 2.0f)) + ((float) (this.width / 2));
                float f26 = this.position.y;
                float f27 = this.scale;
                batch.draw(texture2, f25, f26, f27 * 120.0f, f27 * 80.0f);
                return;
            default:
                return;
        }
    }

    /* renamed from: gdx.game.objects.Player$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$gdx$game$objects$Player$PlayerState;

        static {
            int[] iArr = new int[PlayerState.values().length];
            $SwitchMap$gdx$game$objects$Player$PlayerState = iArr;
            try {
                iArr[PlayerState.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.JUMPING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.FALLING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.JUMP_FALL_TRANSITION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.ATTACK.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.ATTACK2.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.HIT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.DYING.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$gdx$game$objects$Player$PlayerState[PlayerState.DEAD.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    public Rectangle getCollisionRect() {
        return this.collisionRect;
    }

    public Rectangle getAttackCollisionRect() {
        return this.attackCollisionRect;
    }

    public void setAttackCollisionRect(Rectangle attackCollisionRect2) {
        this.attackCollisionRect = attackCollisionRect2;
    }

    public BodyDef getBodyDef() {
        return this.bodyDef;
    }

    public void setBodyDef(BodyDef bodyDef2) {
        this.bodyDef = bodyDef2;
    }

    public FixtureDef getFixtureDef() {
        return this.fixtureDef;
    }

    public void setFixtureDef(FixtureDef fixtureDef2) {
        this.fixtureDef = fixtureDef2;
    }

    public void setPosition(Vector2 position2, boolean center) {
        this.position = position2;
        if (center) {
            position2.x -= (float) (this.width / 2);
            this.position.y = position2.y - ((float) (this.height / 2));
        }
    }

    public boolean attackCollides(Enemy enemy) {
        if (this.attackCollisionRect == null || enemy.getCollisionRect() == null) {
            return false;
        }
        boolean hasCollided = Intersector.overlaps(this.attackCollisionRect, enemy.getCollisionRect());
        if ((enemy instanceof DemonEnemy) && hasCollided) {
            this.attackCollisionRect = null;
        }
        return hasCollided;
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body2) {
        this.body = body2;
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setJumping(boolean jumping) {
        this.isJumping = jumping;
    }
}
