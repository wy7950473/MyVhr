<template>
    <div>
        <el-form :rules="rules" :model="loginForm" class="loginContainer" ref="loginForm">
            <h3 class="loginTtile">系统登录</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginForm.username" auto-complete="off" placholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginForm.password" auto-complete="off" placholder="请输入密码"></el-input>
            </el-form-item>
            <el-checkbox v-model="checked" class="loginRemember">记住密码</el-checkbox>
            <el-button  type="primary" style="width:100%" @click="login">登录</el-button>
        </el-form>
    </div>
</template>

<script>
export default {
    name:"login",
    data() {
        return {
            loginForm:{
                username:'admin',
                password:123
            },
            checked:false,
            rules:{
                username:[{required:true,message:"请输入用户名",trigger:"blur"}],
                password:[{required:true,message:"请输入密码",trigger:"blur"}]
            }
        }
    },
    methods:{
        login(){
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    this.postKeyValueRequest('/doLogin',this.loginForm).then(response => {
                        if(response){
                            window.sessionStorage.setItem('user',JSON.stringify(response.object));
                            this.$message({
                                message:response.msg,
                                type:'success'
                            });
                            this.$router.replace({path:"/home"});
                        }
                    });
                } else {
                    this.$message({
                        message: '请输入所有字段',
                        type: 'error'
                    });
                    return false;
                }
            });
        }
    }
}
</script>

<style lang="css" scoped>
.loginContainer{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 35px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}
.loginTtile{
    margin: 0px auto 20px auto;
    text-align: center;
    color: #505458;
}
.loginRemember{
    text-align: center;
    margin: 0px 0px 15px 0px;
}
</style>