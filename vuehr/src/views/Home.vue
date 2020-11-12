<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">微人事</div>
                <el-dropdown class="userinfo" @command="commandHandler">
                    <span class="el-dropdown-link">
                        {{user.name}}<img :src="user.userface" />
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                        <el-dropdown-item command="setting">设置</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
            <el-container>
                <el-aside class="menuHandler" width="201px" style="solid 1px #e6e6e6">
                    <el-menu router>
                        <template v-for="(item,index) in routes">
                            <el-submenu :index="index+''" :key="index" v-if="!item.hidden">
                                <template slot="title">
                                    <i :class="item.iconCls" style="color:#409eff;margin-right:5px"></i>
                                    <span>{{item.name}}</span>
                                </template>
                                <el-menu-item v-for="(itemChildren,index) in item.children" 
                                    :key="index" :index="itemChildren.path">{{itemChildren.name}}</el-menu-item>
                            </el-submenu>
                        </template>
                    </el-menu>
                </el-aside>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div v-if="this.$router.currentRoute.path =='/home'" class="homeWelcome">
                        欢迎来到本系统
                    </div>
                    <router-view />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
export default {
    name:"Home",
    data() {
        return {
            user:JSON.parse(window.sessionStorage.getItem('user'))
        }
    },
    computed:{
        routes(){
            return this.$store.state.menu.routes;
        }
    },
    methods:{
        commandHandler(cmd){
            if (cmd == 'logout') {
                this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.getRequest('/logout');
                    window.sessionStorage.removeItem('user');
                    this.$store.commit('menu/initRoutes',[]);
                    this.$router.push({path:"/"});
                }).catch(() => {
                    this.$message({
                        type: 'info',
                         message: '已取消删除'
                    });          
                });
            }
        }
    }
}
</script>

<style lang="css" scoped>
.homeHeader{
    background-color: #409eff;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0px 15px;
    box-sizing: border-box;
}
.homeHeader .title {
    font-size: 30px;
    font-family: 华文行楷;
    color: #ffffff;
}
.el-dropdown-link {
    cursor: pointer;
    color: #ffffff;
    display: flex;
    align-items: center;
}
.el-icon-arrow-down {
    font-size: 12px;
}
.el-dropdown-link img{
    width: 48px;
    height: 48px;
    border-radius: 24px;
    margin-left: 8px;
}
.homeWelcome{
    text-align:center;
    font-size:30px;
    color:#409eff;
    padding-top:50px;
}
</style>