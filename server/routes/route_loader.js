import { getAllUsers, getOnlineUsers, login, signup } from '../controllers/user_controller.js';
import { createRoom, enterRoom, leaveRoom, getAllRooms, getRoomsByUid } from '../controllers/room_controller.js';
import { uploadProfileImg, uploadRoomImg } from '../middlewares/multer_middleware.js';

const route_loader = { };

route_loader.init = (app, router) => {
    return initRoutes(app, router);
};

function initRoutes(app, router) {
    router.post('/user/login', login);
    router.post('/user/signup', uploadProfileImg.single('profile_img'), signup);
    router.get('/users', getAllUsers);
    router.get('/users/online', getOnlineUsers);
    router.post('/room', uploadRoomImg.single('room_img'), createRoom);
    router.put('/room/:id/enter/:uid', enterRoom);
    router.put('/room/:id/leave/:uid', leaveRoom);
    router.get('/rooms', getAllRooms);
    router.get('/rooms/:uid', getRoomsByUid);

    app.use('/', router);
}

export default route_loader;