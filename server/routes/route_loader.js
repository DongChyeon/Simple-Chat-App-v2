import { login, signup } from '../controllers/user_controller.js'

const route_loader = { };

route_loader.init = (app, router) => {
    return initRoutes(app, router);
};

function initRoutes(app, router) {
    router.route('/user/login').post(login);
    router.route('/user/signup').post(signup);

    app.use('/', router);
}

export default route_loader;