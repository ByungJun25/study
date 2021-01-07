import Vector from './Vector';

export default class Entity {
    position: Vector;

    constructor(position: Vector) {
        this.position = position;
    }

    update() {

    }

    render(context: CanvasRenderingContext2D) {

    }
}