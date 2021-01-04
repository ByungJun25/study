import App from './App'
import DragArea from './model/DragArea'
import Vector from './model/Vector'
import Unit from './model/Unit'

window.addEventListener('load', () => {
    const app = new App(document.body);
    const dragArea = new DragArea(new Vector(0, 0));
    
    app.entityManager.addEntity(dragArea);

    for(let i = 0; i < 20; i++) {
        const unit = new Unit(new Vector(app.canvas.width * Math.random() * 0.5, app.canvas.height * Math.random() * 0.5));
        app.entityManager.addEntity(unit);
    }
    app.play();
})