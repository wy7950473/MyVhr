import router from './index';
import store from '../store/index';
import { initMenu } from '../utils/menus';

router.beforeEach((to,from,next) => {
    if (to.path == '/') {
        next();
    } else {
        initMenu(router,store);
        next();
    }
});