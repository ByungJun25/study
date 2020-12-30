// index.ts
import Time from './model/Time'
import EntityManager from './model/EntityManager'
import Firework from './model/Firework';
import Vector from './model/Vector';

export default class App {
    ref: HTMLElement;
    canvas: HTMLCanvasElement;
    context: CanvasRenderingContext2D;
    handleRequestFrame: number | null = null;
    entityManager: EntityManager;

    constructor(ref: HTMLElement) {
        this.ref = ref;
        this.canvas = document.createElement('canvas');
        this.canvas.width = 700;
        this.canvas.height = 700;
        this.context = this.canvas.getContext('2d')!;
        this.entityManager = new EntityManager();
        this.ref.appendChild(this.canvas);
    }

    play = () => {
        Time.start();
        this.handleRequestFrame = window.requestAnimationFrame(this.onEnterFrame);
    }

    pause = () => {
        if(this.handleRequestFrame === null) {
            return;
        }

        window.cancelAnimationFrame(this.handleRequestFrame);
    }

    onEnterFrame = () => {
        Time.update();
        this.entityManager.update();
        this.entityManager.render(this.context);
        this.handleRequestFrame = window.requestAnimationFrame(this.onEnterFrame);
    }
}

window.addEventListener('load', () => {
    const app = new App(document.body);

    function createFirework() {
        const firework = new Firework(new Vector(app.canvas.width * Math.random(), app.canvas.height));
        app.entityManager.addEntity(firework);
    }
    
    createFirework();
    setInterval(() => {
        createFirework();
    }, 600);

    app.play();
});