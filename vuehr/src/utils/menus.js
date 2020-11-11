import { getRequest } from './api';

export const initMenu = (router,store) => {
    if(store.state.menu.routes.length > 0){
        return false;
    }
    getRequest("/system/config/menu").then(response => {
        console.log(response);
        if(response){
            let fmtRoutes = formatRoutes(response);
            router.addRoutes(fmtRoutes);
            store.commit('menu/initRoutes',fmtRoutes);
        }
    });
}

export const formatRoutes = (routers) => {
    let fmtRoutes = [];
    routers.forEach(router => {
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = router;
        if(children && children instanceof Array){
            children = formatRoutes(children); 
        }
        let fmRouter = {
            path:path,
            name:name,
            iconCls:iconCls,
            children:children,
            meta:meta,
            component(resolve){
                if (component.startsWith("Home")) {
                    require(['../views/'+component+'.vue'],resolve);
                } else if (component.startsWith("Emp")){
                    require(['../views/emp/'+component+'.vue'],resolve);
                } else if (component.startsWith("Per")){
                    require(['../views/per/'+component+'.vue'],resolve);
                } else if (component.startsWith("Sal")){
                    require(['../views/sal/'+component+'.vue'],resolve);
                } else if (component.startsWith("Sta")){
                    require(['../views/sta/'+component+'.vue'],resolve);
                } else if (component.startsWith("Sys")){
                    require(['../views/sys/'+component+'.vue'],resolve);
                }
                // require(['../views'+path+component+'.vue'],resolve);
            }
        }
        fmtRoutes.push(fmRouter);
    });
    return fmtRoutes;
}