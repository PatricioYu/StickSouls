package com.sticksouls.items.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
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
	private World world;
	private Fixture fixture;
	protected Body weaponBody;
	private int width;
	private int height;
	protected boolean firstDraw = false;
	
	protected final RevoluteJoint rightJoint, leftJoint, topJoint, bottomJoint;
	protected RevoluteJointDef rightJointDef, leftJointDef, topJointDef, bottomJointDef;
	private boolean isRightJoint, isLeftJoint, isTopJoint, isBottomJoint;
	
	protected Weapon(String name, String description, int width, int height, final Body CHARACTERBODY, World world) {
		super(name, description);
		this.CHARACTERBODY = CHARACTERBODY;
		this.width = width;
		this.height = height;
		this.world = world;
		
		createWeaponBody(world);
		
		rightJointDef = new RevoluteJointDef();
		leftJointDef = new RevoluteJointDef();
		topJointDef = new RevoluteJointDef();
		bottomJointDef = new RevoluteJointDef();
		
		rightJoint = createJoint(new Vector2(5, 0), new Vector2(0, -10), rightJointDef);
		leftJoint = createJoint(new Vector2(-5, 0), new Vector2(0, -10), leftJointDef);
		topJoint = createJoint(new Vector2(0, 8), new Vector2(0, -10), topJointDef);
		bottomJoint = createJoint(new Vector2(0, -8), new Vector2(0, -10), bottomJointDef);
		
		destroyAllJoints();
		createJoint(rightJointDef);
		
	}

	private RevoluteJoint createJoint(Vector2 anchorA, Vector2 anchorB, RevoluteJointDef jointDef) {
		jointDef.bodyA = CHARACTERBODY;
		jointDef.bodyB = weaponBody;
		jointDef.collideConnected = false;
		jointDef.localAnchorA.set(anchorA.x, anchorA.y); // Punto de anclaje en el cuerpo del personaje (ajustar según la forma del personaje)
		jointDef.localAnchorB.set(anchorB.x, anchorB.y); // Punto de anclaje en la espada (ajustar según la forma de la espada)
		jointDef.enableLimit = true;
		
		return (RevoluteJoint) world.createJoint(jointDef);
	}
	
	protected void createJoint(RevoluteJointDef joint) {
		world.createJoint(joint);
	}
	
	protected void destroyAllJoints() {
		Array<Joint> joints = new Array<Joint>();
		world.getJoints(joints);
		
		for(Joint joint : joints) {
			world.destroyJoint(joint);	
		}
	}
	
	public void draw() {
		if(!firstDraw) {
			firstDraw = true;
			weaponBody.setTransform(CHARACTERBODY.getPosition().x + 5, CHARACTERBODY.getPosition().y, 0);
		}
		//this.rightJoint.setLimits(0, 0);
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
		fixtureDef.density = 0f;
		fixtureDef.friction = 0f;
		fixtureDef.isSensor = true;
		
		fixture = weaponBody.createFixture(fixtureDef);
				
		shape.dispose();
	}
	
	protected void deleteFixture() {
		weaponBody.destroyFixture(fixture);
	}

	public abstract void attack(Vector2 characterCoordinates);
	public abstract boolean continueAttack();
	protected abstract boolean finishAttack();
	
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

