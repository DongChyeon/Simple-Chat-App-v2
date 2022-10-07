import { login, signup } from '../controllers/user_controller.js';
import { uploadProfileImg } from '../middlewares/multer_middleware.js';

const route_loader = { };

route_loader.init = (app, router) => {
    return initRoutes(app, router);
};

function initRoutes(app, router) {
    router.post('/user/login', login);
    router.post('/user/signup', uploadProfileImg.single('profile_img'), signup);

    app.use('/', router);
}

export default route_loader;