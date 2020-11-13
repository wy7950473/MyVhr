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
            <el-table :data="positions" stripe border style="width: 70%">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="id" label="编号" width="55"></el-table-column>
                <el-table-column  prop="name" label="职位名称" width="120"></el-table-column>
                <el-table-column prop="createDate" label="创建时间"></el-table-column>
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
            positions: []
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
            console.log(index);
            console.log(data);
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
</style>