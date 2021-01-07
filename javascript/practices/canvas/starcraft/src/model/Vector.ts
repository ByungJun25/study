export default class Vector {
    x: number;
    y: number;

    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }

    public angleBetween(other: Vector): number {
        return Math.atan2(other.y - this.y, other.x - this.x);
        
    }

    public distance(other: Vector): number {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}