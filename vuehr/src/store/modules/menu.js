
const state = {
    routes:[]
}

const getters = {

}

const mutations = {
    initRoutes(state,data){
        state.routes = data;
    }
}

const actions = {

}

export default {
    namespaced:true,
    state,
    getters,
    mutations,
    actions
}