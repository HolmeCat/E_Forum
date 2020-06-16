<template>
<div class="home1">
<el-container>
    <!-- 左侧空白 -->
    <el-aside></el-aside>
    <el-container class="background">  
        <el-header>
            <el-container>
                 <el-page-header @back="Back" content="欢迎来到用户管理！"></el-page-header>
                 <el-button type="primary" @click="LogOut">退出登录</el-button>
            </el-container>
        </el-header>
        <el-main>
            <el-tabs v-model="tab" type="border-card" style="color: black;">
                <el-tab-pane label="用户信息查看与修改" name="first">
                    <!-- 用户名显示 -->
                    用户名：{{this.user.name}}
                    <!-- 修改密码表单 -->
                    <el-form :model="user" ref="user" class="demo-ruleForm">
                        <el-form-item prop="password" label="新密码" 
                            :rules="[{ required: true, message: '修改密码不可为空！'}]">
                            <el-input v-model="user.password" placeholder="请输入新密码" maxlength="20" show-word-limit></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="UpdatePassword">修改账户密码</el-button>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="评测帖子管理" name="second">
                    <el-table :data="comment.data" style="width: 100%">
                        <el-table-column>
                        <!-- 评论自定义显示方式 -->
                        <template slot-scope="commentData">
                            {{commentData.row.user.name}}{{commentData.row.date}}
                            <el-button @click="DeleteComment(commentData.row.id)">删除</el-button>
                            <br>电影名：<el-button type="text" @click="GotoProduct(commentData.row.product)">{{commentData.row.product.name}}</el-button>
                            <el-rate v-model="commentData.row.score" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
                            <br>{{commentData.row.context}}
                            <!-- 回复帖子自定义显示方式  -->
                            <el-collapse @change="SelectReply(commentData.row)">
                                <el-collapse-item title="查看回复" name="1">
                                    <el-table :data="commentData.row.reply" style="width: 100%">
                                        <el-table-column>
                                            <template slot-scope="replyData">
                                                {{replyData.row.user.name}}{{replyData.row.date}}
                                                {{replyData.row.context}}       
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-collapse-item>
                            </el-collapse>
                        </template>
                        </el-table-column>
                    </el-table>
                    <!-- 分页设置 -->
                    <el-pagination @current-change="SelectComment" :current-page.sync="comment.currentPage"
                            :page-size="10" :pager-count="5" layout="prev, pager, next" :total="comment.total"></el-pagination>
                </el-tab-pane>
                <el-tab-pane label="添加作品" name="third">
                    <el-form :model="product" ref="product" class="demo-ruleForm">
                        <el-form-item prop="name" label="请填写作品名称" 
                            :rules="[{ required: true, message: '作品名称不可为空！'}]">
                            <el-input v-model="product.name" maxlength="50" show-word-limit></el-input>
                        </el-form-item>
                        <el-form-item prop="introduction" label="请填写作品简介">
                            <el-input type="textarea" v-model="product.introduction" maxlength="200" show-word-limit></el-input>
                        </el-form-item>
                        <el-form-item prop="author" label="请填写作者名称">
                            <el-input v-model="product.author" maxlength="50" show-word-limit></el-input>
                        </el-form-item>
                        <el-form-item prop="style" label="请填入风格字段，可以随意编写一小段来说明它的标签" 
                            :rules="[{ required: true, message: '风格不可为空！'}]">
                            <br><el-input v-model="product.style" maxlength="50" show-word-limit></el-input>
                        </el-form-item>
                        <!-- 作品分类 -->
                        <el-form-item>
                            <el-dropdown @command="productType">
                            <span class="el-dropdown-link">
                                {{this.product.point}}<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command=0>{{product.type[0].data}}</el-dropdown-item>
                                <el-dropdown-item command=1>{{product.type[1].data}}</el-dropdown-item>
                                <el-dropdown-item command=2>{{product.type[2].data}}</el-dropdown-item>
                                <el-dropdown-item command=3>{{product.type[3].data}}</el-dropdown-item>
                                <el-dropdown-item command=4>{{product.type[4].data}}</el-dropdown-item>
                            </el-dropdown-menu>
                            </el-dropdown>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="InsertProduct">创建一个新的作品论坛区</el-button>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
            </el-tabs>
        </el-main>
    </el-container>
    <!-- 右侧空白 -->
    <el-aside></el-aside>
</el-container>
</div>
</template>
<script>
export default {
    data() {
      return {
        tab: 'second', //默认标签
        product:{ //作品相关
            name:null,
            introduction:null,
            author:null,
            point:'游戏',  //分类选择指针
            type:[ //排行榜分类
                { data:'游戏' },
                { data:'音乐' },
                { data:'电影' },
                { data:'图书' },
                { data:'综艺视频' },
            ],
            style:null,
        },
        comment:{ //测评相关
            data:[ //数据
                {reply:null} //回复帖数据
            ], 
            currentPage:0, //当前页数
            length:0, //总页数
        },
        user:{ //用户相关
            name:'未登录', //用户登录名称记录
            password:null, //用户更改密码
        },
      }
    },
    mounted(){ //初始化自动执行工作
        //读入用户信息
        if(this.$session.has('userName')){
            //写入变量name
            this.user.name = this.$session.get('userName')
        }
        this.SelectComment()
    },
    methods: {
        //页面组件功能
        productType(command) { //排行榜下拉菜单的选择
            this.product.point = this.product.type[command].data
        },
        //具体数据交互
        SelectComment(){//评测帖信息查询
            this.axios({//根据关键字做查询
                method:'get',
                url:'http://localhost:8085/comment/selectAllByUserName',
                params: {
                    name:this.user.name,
                    page:this.comment.currentPage,
                    size:10
                }
            }).then(response => { //返回列表清单
                if (response.status == 200){
                    this.comment.data = response.data.content,
                    this.comment.total = response.data.totalPages
                }
            });
        },
        SelectReply(data){//回复帖信息查询
             this.axios({//根据关键字做查询
                method:'get',
                url:'http://localhost:8085/reply/selectAllByCommentId',
                params: {
                    id:data.id
                }
            }).then(response => { //返回列表清单
                if (response.status == 200){
                    data.reply = response.data
                }
            });
        },
        InsertProduct(){//添加一个作品
            this.$refs['product'].validate((valid) => {
                if (valid) {
                    this.axios({//根据内容添加
                        method:'get',
                        url:'http://localhost:8085/product/insertOne',
                        params: {
                            name:this.product.name,
                            introduction:this.product.introduction,
                            author:this.product.author,
                            type:this.product.point,
                            style:this.product.style,
                        }
                    }).then(response => { //返回列表清单
                        if (response.status == 200){
                            //添加成功
                            this.$alert('添加成功！');
                        }
                    });    
                } else {
                    return false;
                }
            });
        },
        DeleteComment(data){//删除一个评论
            //确认删除弹窗
            this.$confirm('此操作将删除该评论 是否继续?', '系统消息', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //根据id删除评论
                this.axios({//根据关键字做查询
                    method:'get',
                    url:'http://localhost:8085/comment/deleteOne',
                    params: {
                        id:data
                    }
                });
                this.$router.go(0) //刷新页面
            });
        },
        UpdatePassword(){//修改用户密码
            this.$refs['user'].validate((valid) => {
                if (valid) {
                    this.axios({
                        method:'get',
                        url:'http://localhost:8085/user/updatePassword',
                        params: {
                            name:this.user.name,
                            password:this.user.password
                        }
                    }).then(response => { //返回列表清单
                        if (response.status == 200){
                            this.$alert('修改成功！');
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        LogOut(){ //退出登录
            //发出确认弹出框
            this.$confirm('此操作将退出用户登录状态 是否继续?', '系统消息', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$alert('账号已经退出！');    
                //确认之后在session中删除，即注销
                this.$session.remove('userName')
                //然后回到主页
                this.Back()
            });
        },
        //跳转
        GotoProduct(data){//作品评测页跳转
            this.$router.push({
                name: 'Product',
                query: data//将作品信息传到显示页面去
            })
        },
        Back(){ //返回主页
            this.$router.go(-1)  //刷新页面
        }
    }
}
</script>
<style>
    .home1{
        background: url('../img/6.jpg');
        position:relative;
        margin-left: -8px;
        margin-top: -6px;
        padding: 7px;
        background-size: cover;
        width: 100%;
        height: 600px;
    }
    .title{
        font-size: 45px;
    }
    .background{
        background-color: white;
    }
</style>