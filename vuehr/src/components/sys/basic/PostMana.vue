<template>
    <div>
        <div>
            <el-input    
                class="addInput"
                placeholder="添加职位"
                prefix-icon="el-icon-plus"
                @keydown.enter.native="addPosition"
                v-model="pos.name">
            </el-input>
            <el-button icon="el-icon-plus" type="primary" @click="addPosition">添加</el-button>
        </div>
        <div class="posManaMain">
            <el-table :data="positions" stripe border @selection-change="handleSelectionChange" style="width: 70%">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="id" label="编号" width="55"></el-table-column>
                <el-table-column  prop="name" label="职位名称" width="150"></el-table-column>
                <el-table-column prop="createDate" label="创建时间"></el-table-column>
                <el-table-column prop="enabled" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch
                            v-model="scope.row.enabled"
                            active-text="启用"
                            @change="updateStatus(scope.row)"
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
                        <td><el-tag>职位名称</el-tag></td>
                        <td><el-input size="small" class="updatePosInput" v-model="updatePos.name"></el-input></td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>是否启用</el-tag>
                        </td>
                        <td>
                            <el-switch
                                class="updatePosInput"
                                size="small"
                                v-model="updatePos.enabled"
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
    name:"PostMana",
    data() {
        return {
            pos:{
                name:''
            },
            positions: [],
            dialogVisible:false,
            updatePos:{
                name:'',
                enabled:''
            },
            multipleSelection: []
        }
    },
    methods:{
        initPosition(){
            this.getRequest("/system/basic/pos/").then(response => {
                if (response) {
                    this.positions = response;
                }
            });
        },
        addPosition(){
            if(this.pos.name){
                this.postRequest("/system/basic/pos/",this.pos).then(response => {
                    if(response){
                        this.initPosition();
                        this.pos.name = '';
                    }
                })
            } else {
                this.$message.error("职位名称不能为空");
            }
        },
        handleEdit(index,data){
            this.dialogVisible = true;
            Object.assign(this.updatePos,data);
        },
        doUpdate(){
            this.putRequest("/system/basic/pos/",this.updatePos).then(response => {
                if(response){
                    this.updatePos.name = '';
                    this.updatePos.enabled = '';
                    this.initPosition();
                    this.dialogVisible = false;
                }
            }).catch(error => {
                this.updatePos.name = '';
                this.dialogVisible = false;
            });
            
        },
        updateStatus(val){
            let requestData = {
                id:val.id,
                enabled:val.enabled
            }
            this.putRequest("/system/basic/pos/",requestData).then(response => {
                if (response){
                    this.initPosition();
                }
            })
        },
        handleDelete(index,data){
            let ids = [data.id];
            this.$confirm('此操作将永久删除【'+data.name+'】职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/",ids).then(response => {
                        if(response){
                            this.initPosition();
                        }
                    }); 
                }).catch(() => {
                    this.$message({
                        type: 'info',
                         message: '已取消删除'
                    });          
                });
        },
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        deleteMany(){
            if (this.multipleSelection.length == 0) {
                this.$message({
                    message:"请选择要删除的数据",
                    type:"warning"
                });
                return false;
            }
            let resquestData = [];
            this.multipleSelection.forEach(item => {
                resquestData.push(item.id);
            });
            this.$confirm('此操作将永久删除【'+resquestData.length+'】条数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/",resquestData).then(response => {
                        if(response){
                            this.initPosition();
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
        this.initPosition();
    }
}
</script>

<style lang="css" scoped>
.addInput{
    width:300px;
    margin-right:8px
}
.posManaMain{
    margin-top:15px
}
.updatePosInput{
    width: 230px;
    margin-left: 8px;
    margin-top: 8px;
}   
</style>