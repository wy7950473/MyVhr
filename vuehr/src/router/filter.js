import router from './index';
import store from '../store/index';
import { initMenu } from '../utils/menus';

router.beforeEach((to,from,next) => {
    if (to.path == '/') {
        next();
    } else {
        if (window.sessionStorage.getItem('user')){
            initMenu(router,store);
            next();
        } else{
            next("/?redirect="+to.path);
        }
    }
});