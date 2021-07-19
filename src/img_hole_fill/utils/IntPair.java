package img_hole_fill.utils;

import java.util.Objects;

/**
 * Represents a point in the Cartesian plane
 */
public record IntPair(int x, int y) implements Comparable<IntPair>{

    /**
     * Getter for the y value
     * @return The y value of this point
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for the x value
     * @return The x value of this point
     */
    public int getX() {
        return x;
    }

    /**
     * Defining equality as equality of both x and y coordinates
     * @param other Another object to compare with
     * @return True iff they are equal
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        IntPair tuple = (IntPair) other;
        return x == tuple.x && y == tuple.y;
    }

    /**
     * Defining the hash code to depend on both x and y coordinates
     * @return The hash code of this point
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Comparing two points lexicographically
     * @param other The point to compare with
     * @return positive int if this is bigger than other, negative if smaller and 0 if equal
     */
    @Override
    public int compareTo(IntPair other) {
        if (this.getX() > other.getX()) return 1;
        else if (this.getX() < other.getX()) return -1;
        else{
            return this.getY() - other.getY();
        }
    }
}
