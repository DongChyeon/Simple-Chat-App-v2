import { getAllUsers, getOnlineUsers, getUserById, login, signup, updateProfile, updateProfileWithImg } from '../controllers/user_controller.js';
import { createRoom, enterRoom, leaveRoom, getAllRooms, getRoomsByUid } from '../controllers/room_controller.js';
import { uploadProfileImg, uploadRoomImg } from '../middlewares/multer_middleware.js';
import { verifyToken } from '../middlewares/jwt_middleware.js';

const route_loader = { };

route_loader.init = (app, router) => {
    return initRoutes(app, router);
};

function initRoutes(app, router) {
    router.post('/user/login', login);
    router.post('/user/signup', uploadProfileImg.single('profile_img'), signup);
    router.get('/user/:uid', verifyToken, getUserById);
    router.put('/user/', verifyToken, updateProfile);
    router.put('/user/img', verifyToken, uploadProfileImg.single('profile_img'), updateProfileWithImg);
    router.get('/users', verifyToken, getAllUsers);
    router.get('/users/online', verifyToken, getOnlineUsers);
    router.post('/room', verifyToken, uploadRoomImg.single('room_img'), createRoom);
    router.put('/room/:id/enter/:uid', verifyToken, enterRoom);
    router.put('/room/:id/leave/:uid', verifyToken, leaveRoom);
    router.get('/rooms/all', verifyToken, getAllRooms);
    router.get('/rooms/:uid', verifyToken, getRoomsByUid);

    app.use('/', router);
}

export default route_loader;