package com.google.fpl.liquidfunpaint.physics;

import android.opengl.Matrix;

import com.google.fpl.liquidfun.World;
import com.google.fpl.liquidfunpaint.renderer.DebugRenderer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 15-09-20.
 */
public class WorldLock {

    final private Queue<Runnable> pendingRunnables = new ConcurrentLinkedQueue<>();


    private static final float TIME_STEP = 1 / 120f; // 60 fps

    public static final float WORLD_SCALE = 3f;
    public float sPhysicsWorldWidth = WORLD_SCALE;
    public float sPhysicsWorldHeight = WORLD_SCALE;

    public float screenRatio = 1.0f;

    // Parameters for world simulation
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private static final int PARTICLE_ITERATIONS = 5;

    private World mWorld = null;
    private Lock mWorldLock = new ReentrantLock();

    public final float[] mDrawToScreenTransform = new float[16];

    private static WorldLock sInstance = new WorldLock();

    public static WorldLock getInstance() {
        return sInstance;
    }

    public World getWorld(){
        return mWorld;
    }

    public void lock(){
        mWorldLock.lock();
    }

    public void createWorld(){
        mWorld = new World(0, 0);
    }

    public void resetWorld(){
        lock();

        try {
            deleteWorld();
            createWorld();

            ParticleSystems.getInstance().reset(mWorld);

        } finally {
            unlock();
        }
    }

    public void setDebugDraw(DebugRenderer renderer){
        mWorld.setDebugDraw(renderer);
    }

    private void deleteWorld() {
        lock();

        try {
            if (mWorld != null) {
                mWorld.delete();
                mWorld = null;
            }

        } finally {
            unlock();
        }
    }

    public void setWorldDimensions(float width, float height){
        screenRatio = height/width;

        if(height < width) { //landscape
            sPhysicsWorldHeight = WORLD_SCALE;
            sPhysicsWorldWidth = width * WORLD_SCALE / height;
        } else { //portrait
            sPhysicsWorldHeight = height * WORLD_SCALE / width;
            sPhysicsWorldWidth = WORLD_SCALE;
        }

        createScreenRender();

    }

    private void createScreenRender(){

        Matrix.setIdentityM(mDrawToScreenTransform, 0);

        if(screenRatio > 1) { //portrait
            Matrix.scaleM(mDrawToScreenTransform, 0, screenRatio, 1, 1);
        } else { //landscape
            Matrix.scaleM(mDrawToScreenTransform, 0, 1, 1 / screenRatio, 1);
        }
    }

    public void stepWorld(){
        lock();

        runPendingRunnables();

        try {
            mWorld.step(
                    TIME_STEP, VELOCITY_ITERATIONS,
                    POSITION_ITERATIONS, PARTICLE_ITERATIONS);
        } finally {
            unlock();
        }
    }

    public void unlock() {
        mWorldLock.unlock();
    }

    public void setGravity(float gravityX, float gravityY){

       lock();
        try {
            mWorld.setGravity(
                    gravityX,
                    gravityY);

        } finally {
            unlock();
        }
    }


    public void addPhysicsCommand(Runnable runnable){
        pendingRunnables.add(runnable);
    }

    public void clearPhysicsCommands(){
        pendingRunnables.clear();
    }

    public void runPendingRunnables(){
        while (!pendingRunnables.isEmpty()) {
            pendingRunnables.poll().run();
        }
    }

    @Override
    protected void finalize() {
        deleteWorld();
    }


    public void perspectiveParticleTransform(float[] mPerspectiveTransform, float distance) {
        Matrix.setIdentityM(mPerspectiveTransform, 0);

        float[] transformFromPhysicsWorld = new float[16];

        Matrix.setIdentityM(transformFromPhysicsWorld, 0);

        // understretch
        if(screenRatio < 1) //landscape
            Matrix.scaleM(transformFromPhysicsWorld, 0, 1, screenRatio, 1);
        else //portrait
            Matrix.scaleM(transformFromPhysicsWorld, 0, 1/screenRatio, 1, 1);


        Matrix.translateM(transformFromPhysicsWorld, 0, -0.5f, -0.5f, distance); // center

        Matrix.scaleM(transformFromPhysicsWorld, 0, 1 / WORLD_SCALE, 1 / WORLD_SCALE, 1); //-1, 1

        if(screenRatio < 1) //landscape
            Matrix.scaleM(transformFromPhysicsWorld, 0, screenRatio, 1, 1);
        else //portrait
            Matrix.scaleM(transformFromPhysicsWorld, 0, 1, 1/screenRatio, 1);

        float[] mvpMatrix = new float[16];
        createMVP(mvpMatrix, 0.25f);

        Matrix.multiplyMM(mPerspectiveTransform, 0, mvpMatrix, 0, transformFromPhysicsWorld, 0);
    }

    public void perspectiveTransform(float[] mPerspectiveTransform, float distance) {
        Matrix.setIdentityM(mPerspectiveTransform, 0);

        float[] transformFromPhysicsWorld = new float[16];
        createWorldTransform(transformFromPhysicsWorld, distance);

        float[] mvpMatrix = new float[16];
        createMVP(mvpMatrix, 0.25f);

        Matrix.multiplyMM(mPerspectiveTransform, 0, mvpMatrix, 0, transformFromPhysicsWorld, 0);
    }

    private void createMVP(float[] destArray, float multiplier){

        float[] mViewMatrix = new float[16];
        float[] mProjectionMatrix = new float[16];

        createProjection(mProjectionMatrix, multiplier);
        createViewMatrix(mViewMatrix);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(destArray, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    private void createProjection(float[] destArray, float multiplier){
        Matrix.setIdentityM(destArray, 0);

        Matrix.frustumM(destArray, 0, multiplier, -multiplier, -multiplier, multiplier, 0.5f, 1000.0f);
    }

    private void createViewMatrix(float[] destArray){
        Matrix.setIdentityM(destArray, 0);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(destArray, 0,
                0, 0, -1,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);
    }

    private void createWorldTransform(float[] destArray, float distance){

        Matrix.setIdentityM(destArray, 0);


        Matrix.translateM(destArray, 0, -0.5f, -0.5f, distance); // center

        Matrix.scaleM(destArray, 0, 1 / WORLD_SCALE, 1 / WORLD_SCALE, 1); //-1, 1

        if(screenRatio < 1) //landscape
            Matrix.scaleM(destArray, 0, screenRatio, 1, 1);
        else //portrait
            Matrix.scaleM(destArray, 0, 1, 1/screenRatio, 1);

        Matrix.translateM(destArray, 0, 0, 0, distance);
    }
}
