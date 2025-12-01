package jogo.framework.math;

import com.jme3.math.Vector3f;

public class Vec3 {
    public float x, y, z;

    public Vec3(Vector3f vector3f) { this.x = vector3f.x; this.y =  vector3f.y;  this.z = vector3f.z; }
    public Vec3(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }

    public Vec3 set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; }
    public Vec3 set(Vec3 other) { return set(other.x, other.y, other.z); }


    public Vec3 getVectorTo(Vec3 pos){
        return new Vec3(pos.x - this.x,pos.y - this.y,pos.z - this.z);
    }

    public double distanceTo(Vec3 vec){
        Vec3 vector_to = getVectorTo(vec);
        return Math.sqrt(Math.pow(vector_to.x, 2) + Math.pow(vector_to.y, 2) + Math.pow(vector_to.z, 2));
    }

    public double getXZDistanceTo(Vec3 vec){
        Vec3 vector_to = getVectorTo(vec);
        return Math.sqrt(Math.pow(vector_to.x, 2) + Math.pow(vector_to.z, 2));
    }

    public double getLength(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public float getAngle(){
        return (float)(Math.atan2(x, z));
    }
    public Vec3 getNormalized(){
        double length = this.getLength();
        return new Vec3((float) (this.x / length),(float)(this.y / length),(float)(this.z / length));
    }

    public Vec3 addVec3(Vec3 vec){
        return new Vec3(
                this.x + vec.x,
                this.y + vec.y,
                this.z + vec.z);
    }

    public Vec3 scaleBy(float scale){
        return new Vec3(
                this.x * scale,
                this.y * scale,
                this.z * scale);
    }

    public Vector3f toVector3f(){
        return new Vector3f(this.x, this.y, this.z);
    }

    public int[] toPointXZ() {
        return new int[] {(int)(this.x), (int)(this.z),};
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Float.floatToIntBits(this.x);
        hash = 31 * hash + Float.floatToIntBits(this.y);
        hash = 31 * hash + Float.floatToIntBits(this.z);
        return hash;
    }

    @Override
    public String toString() {
        return ("Vec3 : (" + x + ", " + y + ", " + z + ")");
    }
}

