/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class ParticleSystem {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected ParticleSystem(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ParticleSystem obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        throw new UnsupportedOperationException("C++ destructor does not have public access");
      }
      swigCPtr = 0;
    }
  }

  public int createParticle(ParticleDef def) {
    return liquidfunJNI.ParticleSystem_createParticle(swigCPtr, this, ParticleDef.getCPtr(def), def);
  }

  public void joinParticleGroups(ParticleGroup groupA, ParticleGroup groupB) {
    liquidfunJNI.ParticleSystem_joinParticleGroups(swigCPtr, this, ParticleGroup.getCPtr(groupA), groupA, ParticleGroup.getCPtr(groupB), groupB);
  }

  public ParticleGroup getParticleGroupList() {
    long cPtr = liquidfunJNI.ParticleSystem_getParticleGroupList(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleGroup(cPtr, false);
  }

  public void destroyParticlesInShape(Shape shape, Transform xf) {
    liquidfunJNI.ParticleSystem_destroyParticlesInShape(swigCPtr, this, Shape.getCPtr(shape), shape, Transform.getCPtr(xf), xf);
  }

  public ParticleGroup createParticleGroup(ParticleGroupDef def) {
    long cPtr = liquidfunJNI.ParticleSystem_createParticleGroup(swigCPtr, this, ParticleGroupDef.getCPtr(def), def);
    return (cPtr == 0) ? null : new ParticleGroup(cPtr, false);
  }

  public int getParticleGroupCount() {
    return liquidfunJNI.ParticleSystem_getParticleGroupCount(swigCPtr, this);
  }

  public int getParticleCount() {
    return liquidfunJNI.ParticleSystem_getParticleCount(swigCPtr, this);
  }

  public void setMaxParticleCount(int count) {
    liquidfunJNI.ParticleSystem_setMaxParticleCount(swigCPtr, this, count);
  }

  public void setDamping(float damping) {
    liquidfunJNI.ParticleSystem_setDamping(swigCPtr, this, damping);
  }

  public void setRadius(float radius) {
    liquidfunJNI.ParticleSystem_setRadius(swigCPtr, this, radius);
  }

  public void setDensity(float density) {
    liquidfunJNI.ParticleSystem_setDensity(swigCPtr, this, density);
  }

  public float getDensity() {
    return liquidfunJNI.ParticleSystem_getDensity(swigCPtr, this);
  }

  public void setGravityScale(float gravityScale) {
    liquidfunJNI.ParticleSystem_setGravityScale(swigCPtr, this, gravityScale);
  }

  public float getGravityScale() {
    return liquidfunJNI.ParticleSystem_getGravityScale(swigCPtr, this);
  }

  public void queryShapeAABB(QueryCallback callback, Shape shape, Transform xf) {
    liquidfunJNI.ParticleSystem_queryShapeAABB(swigCPtr, this, QueryCallback.getCPtr(callback), callback, Shape.getCPtr(shape), shape, Transform.getCPtr(xf), xf);
  }

  public void setParticleVelocity(int index, float vx, float vy) {
    liquidfunJNI.ParticleSystem_setParticleVelocity(swigCPtr, this, index, vx, vy);
  }

  public float getParticlePositionX(int index) {
    return liquidfunJNI.ParticleSystem_getParticlePositionX(swigCPtr, this, index);
  }

  public float getParticlePositionY(int index) {
    return liquidfunJNI.ParticleSystem_getParticlePositionY(swigCPtr, this, index);
  }

  public void setParticleLifetime(int index, float lifetime) {
    liquidfunJNI.ParticleSystem_setParticleLifetime(swigCPtr, this, index, lifetime);
  }

  public void setDestructionByAge(boolean enable) {
    liquidfunJNI.ParticleSystem_setDestructionByAge(swigCPtr, this, enable);
  }

  public int copyPositionBuffer(int startIndex, int numParticles, java.nio.ByteBuffer outBuf) {
    return liquidfunJNI.ParticleSystem_copyPositionBuffer(swigCPtr, this, startIndex, numParticles, outBuf);
  }

  public int copyColorBuffer(int startIndex, int numParticles, java.nio.ByteBuffer outBuf) {
    return liquidfunJNI.ParticleSystem_copyColorBuffer(swigCPtr, this, startIndex, numParticles, outBuf);
  }

  public int copyWeightBuffer(int startIndex, int numParticles, java.nio.ByteBuffer outBuf) {
    return liquidfunJNI.ParticleSystem_copyWeightBuffer(swigCPtr, this, startIndex, numParticles, outBuf);
  }

  public int copyVelocityBuffer(int startIndex, int numParticles, java.nio.ByteBuffer outBuf) {
    return liquidfunJNI.ParticleSystem_copyVelocityBuffer(swigCPtr, this, startIndex, numParticles, outBuf);
  }

}
