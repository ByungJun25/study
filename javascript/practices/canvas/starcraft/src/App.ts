import Time from './model/Time'
import EntityManager from './model/EntityManager'
import Vector from './model/Vector'
import Unit from './model/Unit'

export default class App {
    static instance: App;

    ref: HTMLElement;
    canvas: HTMLCanvasElement;
    context: CanvasRenderingContext2D;
    handleRequestFrame: number | null = null;
    entityManager: EntityManager;

    isPressed: boolean = false;
    mouseDownPosition: Vector = new Vector(0, 0);
    mousePosition: Vector = new Vector(0, 0);
    mouseUpPosition: Vector = new Vector(0, 0);

    selectedUnits: Array<Unit> = [];

    constructor(ref: HTMLElement) {
        App.instance = this;

        this.ref = ref;
        this.canvas = document.createElement('canvas');
        this.canvas.width = window.innerWidth;
        this.canvas.height = window.innerHeight;
        this.context = this.canvas.getContext('2d')!;
        this.entityManager = new EntityManager();
        this.ref.appendChild(this.canvas);
    }

    onMouseDown = (e: MouseEvent) => {
        e.preventDefault();
        e.stopPropagation();

        if (e.button === 0) {
            this.isPressed = true;
            this.mouseDownPosition = new Vector(e.clientX, e.clientY);
        } else if (e.button === 2) {
            for (let i = 0; i < this.selectedUnits.length; i++) {
                this.selectedUnits[i].movement = true;
                this.selectedUnits[i].targetPosition = new Vector(e.clientX, e.clientY);
            }
        }
    }

    onMouseMove = (e: MouseEvent) => {
        this.mousePosition = new Vector(e.clientX, e.clientY);
    }

    onMouseUp = (e: MouseEvent) => {
        if (e.button !== 0) {
            return;
        }

        this.isPressed = false;
        this.mouseUpPosition = new Vector(e.clientX, e.clientY);

        const startX = Math.min(this.mouseDownPosition.x, this.mouseUpPosition.x);
        const endX = Math.max(this.mouseDownPosition.x, this.mouseUpPosition.x);

        const startY = Math.min(this.mouseDownPosition.y, this.mouseUpPosition.y);
        const endY = Math.max(this.mouseDownPosition.y, this.mouseUpPosition.y);

        const units = this.entityManager.entities.filter((entity) => entity instanceof Unit) as Unit[];

        this.selectedUnits = units.filter((entity: Unit) => {
            return (
                entity.position.x >= startX &&
                entity.position.x <= endX &&
                entity.position.y >= startY &&
                entity.position.y <= endY
            );
        });

        for (let i = 0; i < units.length; i++) {
            units[i].isSelected = false;
        }

        for (let i = 0; i < this.selectedUnits.length; i++) {
            this.selectedUnits[i].isSelected = true;
        }
    }

    play = () => {
        Time.start();
        window.addEventListener('contextmenu', (e) => {
            e.preventDefault();
        }, false);
        window.addEventListener('mousedown', this.onMouseDown);
        window.addEventListener('mousemove', this.onMouseMove);
        window.addEventListener('mouseup', this.onMouseUp);
        this.handleRequestFrame = window.requestAnimationFrame(this.onEnterFrame);
    }

    pause = () => {
        if (this.handleRequestFrame === null) {
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