// index.ts
import Shape from './model/Shape';
import Vector from './model/Vector';
import Circle from './model/Circle';

class App {
    canvas: HTMLCanvasElement;
    context: CanvasRenderingContext2D;
    delta: number = 0;
    startTime: number;
    frameRequestHandle: number;
    shapes: Array<Shape> = [];

    constructor() {
        this.canvas = document.createElement('canvas');
        this.canvas.width = window.innerWidth;
        this.canvas.height = window.outerHeight;
        this.canvas.classList.add('can')
        this.context = this.canvas.getContext('2d')!;
        this.startTime = Date.now();
        this.frameRequestHandle = window.requestAnimationFrame(this.frameRequest);
        document.body.appendChild(this.canvas);

        for (let i = 0; i < 1000; i++) {
            this.shapes.push(
                new Circle(new Vector(this.canvas.width * 0.5, this.canvas.height * 0.5))
            )
        }
    }

    frameRequest = () => {
        this.frameRequestHandle = window.requestAnimationFrame(this.frameRequest);

        const currentTime = Date.now();
        this.delta = (currentTime - this.startTime) * 0.001;
        this.startTime = currentTime;

        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);

        for (let i = 0; i < this.shapes.length; i++) {
            this.shapes[i].update(this.delta);
            this.shapes[i].render(this.context);
        }
    }
}

window.addEventListener('load', () => {
    new App();
});