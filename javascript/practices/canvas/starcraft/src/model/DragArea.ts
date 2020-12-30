import Entity from './Entity'
import Vector from './Vector'
import App from '../App'

export default class DragArea extends Entity {
    constructor(position: Vector) {
        super(position);
    }

    update() {
        this.position = App.instance.mouseDownPosition;
    }

    render(context: CanvasRenderingContext2D) {
        if (!App.instance.isPressed) {
            return;
        }

        context.beginPath();
        context.strokeStyle = '#00ff00';
        context.lineWidth = 2;
        const width = (App.instance.mousePosition.x - this.position.x);
        const height = (App.instance.mousePosition.y - this.position.y)
        context.rect(this.position.x, this.position.y, width, height);
        context.stroke();
    }
}