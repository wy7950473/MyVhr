<template>
    <div>
        <div>
            <el-input    
                class="addInput"
                placeholder="添加职称"
                prefix-icon="el-icon-plus"
                @keydown.enter.native="addJobLevel"
                v-model="jobLevel.name">
            </el-input>
            <el-select v-model="jobLevel.titleLevel" placeholder="请选择">
                <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
            <el-button style="margin-left:8px" icon="el-icon-plus" type="primary" @click="addJobLevel">添加</el-button>
        </div>
        <div style="margin-top:15px">
            <el-table :data="jobs" stripe border @selection-change="handleSelectionChange" style="width: 80%">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="id" label="编号" width="55"></el-table-column>
                <el-table-column  prop="name" label="职称名称" width="150"></el-table-column>
                <el-table-column  prop="titleLevel" label="职称级别" width="150"></el-table-column>
                <el-table-column prop="createDate" label="创建时间"></el-table-column>
                <el-table-column prop="enabled" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch
                            v-model="scope.row.enabled"
                            @change="updateStatus(scope.row)"
                            active-text="启用"
                            inactive-text="禁用"
                            active-color="#13ce66"
                            inactive-color="#ff4949">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                     <template slot-scope="scope">
                        <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
            title="修改职位"
            :visible.sync="dialogVisible"
            width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>职称名称</el-tag>
                        </td>
                        <td>
                            <el-input size="small" class="updateJobLevelInput" v-model="updateJob.name"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>职称级别</el-tag>
                        </td>
                        <td>
                            <el-select class="updateJobLevelInput" size="small"  v-model="updateJob.titleLevel" placeholder="请选择">
                                <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>是否启用</el-tag>
                        </td>
                        <td>
                            <el-switch
                                class="updateJobLevelInput"
                                size="small"
                                v-model="updateJob.enabled"
                                active-text="启用"
                                inactive-text="禁用"
                                active-color="#13ce66"
                                inactive-color="#ff4949">
                            </el-switch>
                        </td>
                    </tr>
                </table>    
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
            </span>
        </el-dialog>
        <el-button type="danger" size="small" style="margin-top:10px" 
                :disabled="multipleSelection.length == 0" @click="deleteMany">批量删除</el-button>
    </div>
</template>

<script>
export default {
    name:"JobLevelMana",
    data() {
        return {
            jobLevel:{
                name:"",
                titleLevel:""
            },
            options:[
                {label:"正高级",value:"正高级"},
                {label:"副高级",value:"副高级"},
                {label:"中级",value:"中级"},
                {label:"初级",value:"初级"},
                {label:"员级",value:"员级"}
            ],
            jobs:[],
            multipleSelection: [],
            dialogVisible:false,
            updateJob:{
                name:"",
                titleLevel:"",
                enabled:""
            }
        }
    },
    methods:{
        initJobLevel(){
            this.getRequest("/system/basic/joblevel/").then(response => {
                if(response){
                    this.jobs = response;
                }
            });
        },
        addJobLevel(){
            if (this.jobLevel.name.trim() == ''){
                this.$message.warning("请输入职称名称");
                return false;
            }
            if (this.jobLevel.titleLevel == ''){
                this.$message.warning("请选择职称级别");
                return false;
            }
            this.postRequest("/system/basic/joblevel/",this.jobLevel).then(response => {
                if (response){
                    this.initJobLevel();
                    this.jobLevel.name = '';
                    this.jobLevel.titleLevel = '';
                }
            })
        },
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        handleEdit(index,data){
            Object.assign(this.updateJob,data);
            this.dialogVisible = true;
        },
        updateStatus(val){
            let requestData = {
                id:val.id,
                enabled:val.enabled
            }
            this.putRequest("/system/basic/joblevel/",requestData).then(response => {
                if (response){
                    this.initJobLevel();
                }
            })
        },
        doUpdate(){
            this.putRequest("/system/basic/joblevel/",this.updateJob).then(response => {
                if (response){
                    this.updateJob.name = "";
                    this.updateJob.titleLevel = "";
                    this.updateJob.enabled = "";
                    this.dialogVisible = false;
                    this.initJobLevel();
                }
            })
        },
        handleDelete(index,data){
            let ids = [data.id];
            this.$confirm('此操作将永久删除【'+data.name+'】职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/joblevel/",ids).then(response => {
                        if(response){
                            this.initJobLevel();
                        }
                    }); 
                }).catch(() => {
                    this.$message({
                        type: 'info',
                         message: '已取消删除'
                    });          
                });
        },
        deleteMany(){
            if (this.multipleSelection.length == 0) {
                this.$message({
                    message:"请选择要删除的数据",
                    type:"warning"
                });
                return false;
            }
            let requestData = [];
            this.multipleSelection.forEach(item => {
                requestData.push(item.id);
            });
            this.$confirm('此操作将永久删除【'+requestData.length+'】条数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/joblevel/",requestData).then(response => {
                        if(response){
                            this.initJobLevel();
                            this.multipleSelection = [];
                        }
                    }).catch(error => {

                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });          
                });
        }
    },
    mounted(){
        this.initJobLevel();
    }
}
</script>

<style lang="css" scoped>
.addInput{
    width:300px;
    margin-right:8px
}
.updateJobLevelInput{
    width: 230px;
    margin-left: 8px;
    margin-top: 5px;
}  
</style>