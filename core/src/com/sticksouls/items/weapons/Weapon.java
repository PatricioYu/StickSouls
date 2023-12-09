package com.sticksouls.items.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Timer;
import com.sticksouls.items.Item;

public abstract class Weapon extends Item {
	protected int baseDmg, empoweredDmg;
	protected float frameDuration = 0.1f;
	protected Texture spriteSheet;
	protected TextureRegion[][] basicAttackFrames;
	protected TextureRegion[][] empoweredAttackFrames;
	protected Animation<TextureRegion> basicAttackAnimation;
	protected Animation<TextureRegion> empoweredAttackAnimation;
	
	protected final Body CHARACTERBODY;
	private Fixture fixture;
	protected Body weaponBody;
	private int width;
	private int height;
	
	final RevoluteJoint joint;
	
	protected Weapon(String name, String description, int width, int height, final Body CHARACTERBODY, World world) {
		super(name, description);
		this.CHARACTERBODY = CHARACTERBODY;
		this.width = width;
		this.height = height;
		
		createWeaponBody(world);
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		
		jointDef.bodyA = CHARACTERBODY;
		jointDef.bodyB = weaponBody;
		jointDef.collideConnected = false;
		jointDef.localAnchorA.set(5, 0); // Punto de anclaje en el cuerpo del personaje (ajustar según la forma del personaje)
		jointDef.localAnchorB.set(0, -10); // Punto de anclaje en la espada (ajustar según la forma de la espada)
		jointDef.enableLimit = true;
		
		joint = (RevoluteJoint) world.createJoint(jointDef);
		
	}
	
	public abstract void attack();
	
	public void draw() {
		weaponBody.setTransform(CHARACTERBODY.getPosition().x + 5, CHARACTERBODY.getPosition().y, 0);
	}

	private void createWeaponBody(World world){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 0);
		
		weaponBody = world.createBody(bodyDef);
		createFixture();
	}
	
	public void createFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.4f;
		fixtureDef.isSensor = true;
		
		fixture = weaponBody.createFixture(fixtureDef);
				
		shape.dispose();
	}
	
	protected void deleteFixture() {
		weaponBody.destroyFixture(fixture);
	}
	
	// getters
	public Body getBody() {
		return this.weaponBody;
	}
	
	public int getBaseDmg() {
		return baseDmg;
	}
	
	public int getEmpoweredDmg() {
		return empoweredDmg;
	}	
}
