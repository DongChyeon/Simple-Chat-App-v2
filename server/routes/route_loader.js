import { login, signup } from '../controllers/user_controller.js';
import { createRoom, enterRoom, leaveRoom, getRoomList } from '../controllers/room_controller.js';
import { uploadProfileImg, uploadRoomImg } from '../middlewares/multer_middleware.js';

const route_loader = { };

route_loader.init = (app, router) => {
    return initRoutes(app, router);
};

function initRoutes(app, router) {
    router.post('/user/login', login);
    router.post('/user/signup', uploadProfileImg.single('profile_img'), signup);
    router.post('/room', uploadRoomImg.single('room_img'), createRoom);
    router.put('/room/:room_id/enter/:user_id', enterRoom);
    router.put('/room/:room_id/leave/:user_id', leaveRoom);
    router.get('/rooms', getRoomList);

    app.use('/', router);
}

export default route_loader;