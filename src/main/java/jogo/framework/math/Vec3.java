package jogo.framework.math;

public class Vec3 {
    public float x, y, z;

    public Vec3() { this(0,0,0); }
    public Vec3(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }

    public Vec3 set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; }
    public Vec3 set(Vec3 other) { return set(other.x, other.y, other.z); }

    public Vec3 vector_to(Vec3 pos){
        return new Vec3(pos.x - this.x,pos.y - this.y,pos.z - this.z);
    }

    public double distance_to(Vec3 vec){
        Vec3 vector_to = vector_to(vec);
        return Math.sqrt(Math.pow(vector_to.x, 2) + Math.pow(vector_to.x, 2) + Math.pow(vector_to.x, 2));
    }

    public double length(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(x, 2) + Math.pow(x, 2));
    }

    public Vec3 normalized(){
        double length = this.length();
        return new Vec3((float) (this.x / length),(float)(this.y / length),(float)(this.z / length));
    }

    public void add_vec3(Vec3 vec){
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
    }

    public void scale_by(float scale){
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Float.floatToIntBits(this.x);
        hash = 31 * hash + Float.floatToIntBits(this.y);
        hash = 31 * hash + Float.floatToIntBits(this.z);
        return hash;
    }
}

